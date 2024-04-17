package com.foody.savedrecipeservice.business.rabbitmq.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavedRecipeEvent {

    private Long recipeId;
    private Integer numberSaved;

}
