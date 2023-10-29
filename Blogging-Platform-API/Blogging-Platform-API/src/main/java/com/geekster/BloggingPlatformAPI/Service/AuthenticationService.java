package com.geekster.BloggingPlatformAPI.Service;

import com.geekster.BloggingPlatformAPI.Model.AuthenticationToken;
import com.geekster.BloggingPlatformAPI.Model.User;
import com.geekster.BloggingPlatformAPI.Repository.IAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthenticationRepo iAuthenticationRepo;
    public void saveAuthToken(AuthenticationToken authenticationToken) {
        iAuthenticationRepo.save(authenticationToken);
    }

    public boolean authenticate(String email, String token) {
        AuthenticationToken authToken = iAuthenticationRepo.findFirstByTokenValue(token);
        if(authToken == null) {
            return false;
        }
        String tokenConnectedEmail = authToken.getUser().getUserEmail();
        if(!tokenConnectedEmail.equals(email)){
            return false;
        }
        return true;
    }

    public AuthenticationToken findFirstByUser(User user) {
        return iAuthenticationRepo.findFirstByUser(user);
    }

    public void removeToken(AuthenticationToken token) {
        iAuthenticationRepo.delete(token);
    }
}
