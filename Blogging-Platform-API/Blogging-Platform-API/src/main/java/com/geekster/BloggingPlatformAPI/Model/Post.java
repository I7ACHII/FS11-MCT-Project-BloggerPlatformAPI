package com.geekster.BloggingPlatformAPI.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    private String postContent;
    private String postLocation;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    User postOwner;


}
