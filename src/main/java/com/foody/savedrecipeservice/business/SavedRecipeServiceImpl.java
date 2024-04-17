package com.foody.savedrecipeservice.business;

import com.foody.savedrecipeservice.business.exception.AlreadyAddedException;
import com.foody.savedrecipeservice.business.exception.SavedRecipeNotFoundException;
import com.foody.savedrecipeservice.business.rabbitmq.SavedRecipeEventPublisher;
import com.foody.savedrecipeservice.business.rabbitmq.event.SavedRecipeEvent;
import com.foody.savedrecipeservice.configuration.RabbitMQConfig;
import com.foody.savedrecipeservice.domain.RecipeRequestMetaData;
import com.foody.savedrecipeservice.domain.SavedRecipeResponse;
import com.foody.savedrecipeservice.persistence.SavedRecipeRepository;
import com.foody.savedrecipeservice.persistence.entity.SavedRecipe;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedRecipeServiceImpl implements SavedRecipeService {
    private final SavedRecipeRepository savedRecipeRepository;
    private final SavedRecipeEventPublisher savedRecipeEventPublisher;


    public boolean checkIfCanAdd(Long userId, Long recipeId){
        Optional<SavedRecipe> optionalSavedRecipe = savedRecipeRepository.findByUserIdAndAndRecipeId(userId, recipeId);
        return optionalSavedRecipe.isEmpty();
    }
    @Override
    public void addFavoriteRecipe(Long id, RecipeRequestMetaData requestMetaData){
        if(!this.checkIfCanAdd(id, requestMetaData.getId())){
            throw new AlreadyAddedException();
        }

        SavedRecipe savedRecipe = new SavedRecipe();
        savedRecipe.setUserId(id);
        savedRecipe.setRecipeId(requestMetaData.getId());
        savedRecipe.setTitle(requestMetaData.getTitle());
        savedRecipe.setTime(requestMetaData.getTime());
        savedRecipe.setDescription(requestMetaData.getDescription());
        savedRecipe.setUrls(requestMetaData.getUrlImages());
        SavedRecipe newSavedRecipe = savedRecipeRepository.save(savedRecipe);

        // sending message to recipe service
        SavedRecipeEvent savedRecipeEvent= new SavedRecipeEvent();
        savedRecipeEvent.setRecipeId(newSavedRecipe.getRecipeId());
        savedRecipeEvent.setNumberSaved(1);
        savedRecipeEventPublisher.publishSavedRecipeEvent(savedRecipeEvent);
        System.out.println("sending to recipe the event for adding: " + savedRecipeEvent);
    }

    @Override
    public void removeFavoriteRecipe(Long id){

        Optional<SavedRecipe> optionalSavedRecipe = savedRecipeRepository.findById(id);
        if(optionalSavedRecipe.isEmpty()){
            throw new SavedRecipeNotFoundException();
        }
        SavedRecipe recipe = optionalSavedRecipe.get();

        // sending message to recipe service
        SavedRecipeEvent savedRecipeEvent= new SavedRecipeEvent();
        savedRecipeEvent.setRecipeId(recipe.getRecipeId());
        savedRecipeEvent.setNumberSaved(-1);
        savedRecipeEventPublisher.publishSavedRecipeEvent(savedRecipeEvent);
        System.out.println("sending to recipe the event for removing: " + savedRecipeEvent);

        this.savedRecipeRepository.deleteById(id);
    }

    @Override
    public List<SavedRecipeResponse> getSavedRecipesByUserId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SavedRecipe> recipePage = savedRecipeRepository.findByUserId(id, pageable);

        if (recipePage.isEmpty()) {
            throw new SavedRecipeNotFoundException();
        }

        return recipePage.getContent().stream()
                .map(this::mapToRecipeResponse)
                .collect(Collectors.toList());
    }


    private SavedRecipeResponse mapToRecipeResponse(SavedRecipe recipeEntity) {
        SavedRecipeResponse response = new SavedRecipeResponse();
        response.setId(recipeEntity.getId());
        response.setUserId(recipeEntity.getUserId());
        response.setRecipeId(recipeEntity.getRecipeId());
        response.setTitle(recipeEntity.getTitle());
        response.setDescription(recipeEntity.getDescription());
        response.setTime(recipeEntity.getTime());
        response.setUrlImages(recipeEntity.getUrls());
        return response;
    }

    @RabbitListener(queues = RabbitMQConfig.FANOUT_SAVED_QUEUE)
    @Transactional
    public void handleDelete(Long recipeId) {
        List<SavedRecipe> savedRecipes = savedRecipeRepository.findByRecipeId(recipeId);
        if(!savedRecipes.isEmpty()){
            savedRecipeRepository.deleteByRecipeId(recipeId);
        }
    }
}
