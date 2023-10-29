package com.geekster.BloggingPlatformAPI.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PostComment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    private String commentDescp;

    @ManyToOne
    @JoinColumn(name = "fk_post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name = "fk_commenter_id")
    User commenter;

}
