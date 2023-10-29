package com.geekster.BloggingPlatformAPI.Repository;

import com.geekster.BloggingPlatformAPI.Model.AuthenticationToken;
import com.geekster.BloggingPlatformAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken, Long> {

    AuthenticationToken findFirstByTokenValue(String token);

    AuthenticationToken findFirstByUser(User user);
}
