package com.geekster.BloggingPlatformAPI.Service;


import com.geekster.BloggingPlatformAPI.Model.Post;
import com.geekster.BloggingPlatformAPI.Model.User;
import com.geekster.BloggingPlatformAPI.Repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    IPostRepo iPostRepo;

    public String createPost(Post post) {
        iPostRepo.save(post);
        return "Post has been added!!!";
    }


    public String removePost(Long postId, String email) {
        Post post = iPostRepo.findById(postId).orElse(null);
        if(post == null){
            return "Post to be deleted does not exists!!";
        }
        else if (post.getPostOwner().getUserEmail().equals(email)){
            iPostRepo.deleteById(postId);
            return "Post has been deleted!!!";
        }
        else{
            return "You are not authorize to delete this post!! ";
        }
    }

    public boolean validatePost(Post post) {
        return iPostRepo.existsById(post.getPostId());
    }

    public List<Post> getAllPostOfUser(User user) {
        List<Post> postsOfUser = iPostRepo.findByPostOwner(user);
        return postsOfUser;
    }


    public Post findByPostId(Long postId) {
        return iPostRepo.findById(postId).orElse(null);

    }
}
