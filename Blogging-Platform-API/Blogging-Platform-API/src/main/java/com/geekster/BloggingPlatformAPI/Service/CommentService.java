package com.geekster.BloggingPlatformAPI.Service;


import com.geekster.BloggingPlatformAPI.Model.Comment;
import com.geekster.BloggingPlatformAPI.Model.Post;
import com.geekster.BloggingPlatformAPI.Repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    ICommentRepo iCommentRepo;

    public String addPostComment(Comment comment) {
        iCommentRepo.save(comment);
        return "comment has been added!!";
    }

    public Comment getCommentById(Long commentId) {
        return iCommentRepo.findById(commentId).orElse(null);
    }

    public String removeComment(Long commentId) {
        iCommentRepo.deleteById(commentId);
        return "comment has been deleted!!";
    }

    public List<Comment> getAllCommentsOfPost(Post post) {
        List<Comment> commentsOfPost = iCommentRepo.findByPost(post);
        return commentsOfPost;
    }
}
