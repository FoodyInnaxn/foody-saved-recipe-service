package com.foody.savedrecipeservice.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "saved_recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "recipe_id")
    private Long recipeId;

    private String title;
    private String time;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "saved_recipe_urls", joinColumns = @JoinColumn(name = "saved_recipe_id"))
    @Column(name = "img_url")
    private List<String> urls;
}
