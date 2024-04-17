package com.foody.savedrecipeservice.business;

import com.foody.savedrecipeservice.domain.RecipeRequestMetaData;
import com.foody.savedrecipeservice.domain.SavedRecipeResponse;

import java.util.List;

public interface SavedRecipeService {
    void addFavoriteRecipe(Long id, RecipeRequestMetaData requestMetaData);
    void removeFavoriteRecipe(Long id);
    List<SavedRecipeResponse> getSavedRecipesByUserId(Long id, int page, int size);
}
