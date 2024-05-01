package com.foody.savedrecipeservice.controller;

import com.foody.savedrecipeservice.business.SavedRecipeService;
import com.foody.savedrecipeservice.business.exception.SavedRecipeNotFoundException;
import com.foody.savedrecipeservice.domain.RecipeRequestMetaData;
import com.foody.savedrecipeservice.domain.SavedRecipeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class SavedRecipeController {
    private final SavedRecipeService savedRecipeService;

    @PostMapping("/{id}")
    public ResponseEntity<String> addFavoriteRecipe(@PathVariable Long id, @RequestBody RecipeRequestMetaData recipe) {
        savedRecipeService.addFavoriteRecipe(id, recipe);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSavedRecipe(@PathVariable Long id) {
        savedRecipeService.removeFavoriteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SavedRecipeResponse>>getSavedRecipesByUserId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<SavedRecipeResponse> savedRecipeResponses = savedRecipeService.getSavedRecipesByUserId(id, page, size);
        if (savedRecipeResponses.isEmpty()) {
            throw new SavedRecipeNotFoundException();
        }
        return ResponseEntity.ok(savedRecipeResponses);
    }
}
