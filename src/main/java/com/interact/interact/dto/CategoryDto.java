package com.interact.interact.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long categoryId;

    @NotEmpty(message = "Title can't be empty")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 25,message = "At least 25 chars should be added")
    private String categoryDescription;
}
