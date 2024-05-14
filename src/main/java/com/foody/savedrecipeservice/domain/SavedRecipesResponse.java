package com.foody.savedrecipeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedRecipesResponse {

    private List<SavedRecipeResponse> recipes;
    private int totalPages;

}