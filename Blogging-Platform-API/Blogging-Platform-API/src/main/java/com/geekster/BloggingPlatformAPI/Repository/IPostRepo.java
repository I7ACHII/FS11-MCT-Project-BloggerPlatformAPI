package com.geekster.BloggingPlatformAPI.Repository;

import com.geekster.BloggingPlatformAPI.Model.Post;
import com.geekster.BloggingPlatformAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepo extends JpaRepository<Post, Long> {
    List<Post> findByPostOwner(User user);
}
