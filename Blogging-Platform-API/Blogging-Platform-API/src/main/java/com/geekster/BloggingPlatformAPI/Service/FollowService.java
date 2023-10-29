package com.geekster.BloggingPlatformAPI.Service;

import com.geekster.BloggingPlatformAPI.Model.Follow;
import com.geekster.BloggingPlatformAPI.Model.User;
import com.geekster.BloggingPlatformAPI.Repository.IFollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    IFollowRepo iFollowRepo;
    public boolean followAllowedOrNot(User user, User usersFollower) {
        List<Follow> SameRecordOfFollow = iFollowRepo.findByUserAndUsersFollower(user, usersFollower);
        return SameRecordOfFollow.isEmpty();
    }

    public void followSomeone(Follow follow) {
        iFollowRepo.save(follow);
    }

    public Follow findById(Long followId) {
        return iFollowRepo.findById(followId).orElse(null);
    }

    public String unfollowSomeone(Follow followRecord) {
        iFollowRepo.delete(followRecord);
        return "unfollowed successfully";
    }
}
