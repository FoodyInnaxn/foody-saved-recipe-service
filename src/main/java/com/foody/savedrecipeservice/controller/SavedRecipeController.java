package com.foody.savedrecipeservice.controller;

import com.foody.savedrecipeservice.business.SavedRecipeService;
import com.foody.savedrecipeservice.business.exception.SavedRecipeNotFoundException;
import com.foody.savedrecipeservice.domain.RecipeRequestMetaData;
import com.foody.savedrecipeservice.domain.SavedRecipeResponse;
import com.foody.savedrecipeservice.domain.SavedRecipesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@Slf4j
public class SavedRecipeController {
    private final SavedRecipeService savedRecipeService;

    @PostMapping("/{id}")
    public ResponseEntity<String> addFavoriteRecipe(@PathVariable Long id, @RequestBody RecipeRequestMetaData recipe) {
        log.info("hey add ");
        savedRecipeService.addFavoriteRecipe(id, recipe);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/{recipeId}")
    public ResponseEntity<Void> removeSavedRecipe(@PathVariable("id") Long userId, @PathVariable Long recipeId) {
        savedRecipeService.removeFavoriteRecipe(userId, recipeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public SavedRecipesResponse getRecipes(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        log.info("hey get ");
        SavedRecipesResponse recipeResponses = savedRecipeService.getSavedRecipesByUserId(id, page, size);
        log.info("controller get recipes {}", recipeResponses);
        if (recipeResponses.getRecipes().isEmpty()) {
            throw new SavedRecipeNotFoundException();
        }
        return recipeResponses;

    }

    @GetMapping("/{userId}/recipes/{recipeId}/saved")
    public ResponseEntity<Boolean> isRecipeSaved(@PathVariable Long userId, @PathVariable Long recipeId) {
        boolean isSaved = savedRecipeService.checkIfCanAdd(recipeId, userId);
        return ResponseEntity.ok(isSaved);
    }

}
