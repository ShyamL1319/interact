package com.interact.interact.dto;

import com.interact.interact.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long postId;

    @NotEmpty
    @Size(min = 10, max = 50, message = "minimum 10 and maximum 50 character should be there in the title")
    private String title;

    @NotEmpty
    @Size(min = 50, message = "At least 50 Character should be there")
    private String content;

    private Instant addedDate;

    private Instant updatedDate;

    private String imageUrl;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();
}
