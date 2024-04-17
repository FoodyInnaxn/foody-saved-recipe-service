package com.foody.savedrecipeservice.persistence;

import com.foody.savedrecipeservice.persistence.entity.SavedRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedRecipeRepository extends JpaRepository<SavedRecipe, Long> {
    Page<SavedRecipe> findByUserId(Long userId, Pageable pageable);
    Optional<SavedRecipe> findByUserIdAndAndRecipeId(Long userId, Long recipeId);
    List<SavedRecipe> findByRecipeId(Long recipeId);
    void deleteByRecipeId(Long recipeId);
}
