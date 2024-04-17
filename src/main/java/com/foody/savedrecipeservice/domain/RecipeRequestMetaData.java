package com.foody.savedrecipeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequestMetaData {
    private Long id;
    private String title;
    private String description;
    private String time;
    private List<String> urlImages;
}
