package com.interact.interact.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long commentId;

    @NotEmpty
    private String content;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "post",referencedColumnName = "postId")
    private Post post;

//    @CreatedDate
//    private Instant createdDate;
//
//    @LastModifiedDate
//    private Instant modifiedDate;
//
//    @ManyToOne()
//    @JoinColumn(name = "user", referencedColumnName = "userId")
//    private User user;

}
