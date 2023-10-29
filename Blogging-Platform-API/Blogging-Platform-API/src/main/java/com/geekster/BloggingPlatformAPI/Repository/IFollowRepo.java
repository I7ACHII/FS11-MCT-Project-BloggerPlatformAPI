package com.geekster.BloggingPlatformAPI.Repository;

import com.geekster.BloggingPlatformAPI.Model.Follow;
import com.geekster.BloggingPlatformAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFollowRepo extends JpaRepository<Follow, Long> {
    List<Follow> findByUserAndUsersFollower(User user, User usersFollower);
}
