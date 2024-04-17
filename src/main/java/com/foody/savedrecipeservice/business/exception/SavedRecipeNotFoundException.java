package com.foody.savedrecipeservice.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SavedRecipeNotFoundException extends ResponseStatusException {
    public SavedRecipeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "INVALID_SAVED_RECIPE_ID");
    }
}