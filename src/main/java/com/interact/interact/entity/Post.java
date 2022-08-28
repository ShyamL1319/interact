package com.interact.interact.entity;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
@Getter()
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;

    @NotBlank(message = "Post Name cannot be empty or Null")
    private String title;

    private String imageUrl;

    @NotEmpty
    private String content;

    private Date addedDate = new Date();

    private Instant updatedDate;

    @ManyToOne()
    private Category category;

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
