package com.foody.savedrecipeservice.business;

import com.foody.savedrecipeservice.domain.RecipeRequestMetaData;
import com.foody.savedrecipeservice.domain.SavedRecipeResponse;
import com.foody.savedrecipeservice.domain.SavedRecipesResponse;

import java.util.List;

public interface SavedRecipeService {
    void addFavoriteRecipe(Long id, RecipeRequestMetaData requestMetaData);
    boolean checkIfCanAdd(Long userId, Long recipeId);
    void removeFavoriteRecipe(Long userId, Long recipeId);
    SavedRecipesResponse getSavedRecipesByUserId(Long id, int page, int size);
}
