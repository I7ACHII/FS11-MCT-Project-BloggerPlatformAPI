package com.geekster.BloggingPlatformAPI.Controller;

import com.geekster.BloggingPlatformAPI.Model.Comment;
import com.geekster.BloggingPlatformAPI.Model.Follow;
import com.geekster.BloggingPlatformAPI.Model.Post;
import com.geekster.BloggingPlatformAPI.Model.User;
import com.geekster.BloggingPlatformAPI.Model.dto.SignInInput;
import com.geekster.BloggingPlatformAPI.Model.dto.SignUpOutput;
import com.geekster.BloggingPlatformAPI.Service.AuthenticationService;
import com.geekster.BloggingPlatformAPI.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    //sign up, sign in , sign out a particular Blogging user
    @PostMapping("user/signup")
    public SignUpOutput signUpUser(@Valid @RequestBody User user) {
        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInUser(@RequestBody @Valid SignInInput signInInput) {
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutUser(String email, String token) {
        if(authenticationService.authenticate(email,token)) {
            return userService.sigOutUser(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }
    }

    // API related to Post
    // create post
    @PostMapping("post")
    public String createPost (@RequestBody Post post, @RequestParam String postOwnerEmail, @RequestParam String token){
        if(authenticationService.authenticate(postOwnerEmail,token)) {
            return userService.createPost(post,postOwnerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @GetMapping("all/post")
    public List<Post> getAllPostOfUser(@RequestParam Long userId, @RequestParam String yourEmail, @RequestParam String yourToken){
        if(authenticationService.authenticate(yourEmail,yourToken)) {
            return userService.getAllPostOfUser(userId);
        }
        else {
            return null;
        }

    }

    // delete a post
    @DeleteMapping("post")
    public String removePost(@RequestParam Long postId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removePost(postId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    // comment functionalities
    // add comment
    @PostMapping("comment")
    public String addPostComment(@RequestBody Comment comment, @RequestParam String commenterEmail, @RequestParam String commenterAuthToken){
        if(authenticationService.authenticate(commenterEmail, commenterAuthToken)){
            return userService.addPostComment(comment, commenterEmail);
        }
        else{
            return "Non authenticated user!!";
        }
    }

    @GetMapping("all/comment")
    public List<Comment> getAllCommentsOfPost(@RequestParam Long postId, @RequestParam String yourEmail, @RequestParam String yourToken) {
        if (authenticationService.authenticate(yourEmail, yourToken)) {
            return userService.getAllCommentsOfPost(postId);
        } else {
            return null;
        }
    }


        //delete comment
    @DeleteMapping("comment")
    public String removeComment (@RequestParam Long commentId, @RequestParam String deletersEmail, @RequestParam String userAuthToken){
        if(authenticationService.authenticate(deletersEmail, userAuthToken)){
            return userService.removeComment(commentId, deletersEmail);
        }
        else{
            return "Not authenticated user!!";
        }
    }

    //   follow functionality
    @PostMapping("follow")
    public String followSomeone (@RequestBody Follow follow, @RequestParam String usersFollowerEmail, @RequestParam String usersFollowerAuthToken){
        if(authenticationService.authenticate(usersFollowerEmail, usersFollowerAuthToken)){
            return userService.followSomeone(follow, usersFollowerEmail);
        }
        else{
            return "Not an Authenticated user activity!!";
        }
    }

    @DeleteMapping("unfollow/target/{followId}")
    public String unfollowSomeone(@PathVariable Long followId, @RequestParam String unfollowerEmail, @RequestParam String unfollowerAuthToken) {
        if(authenticationService.authenticate(unfollowerEmail, unfollowerAuthToken)){
            return userService.unfollowSomeone(followId, unfollowerEmail);
        }
        else{
            return "Not a Authenticated User activity!!";
        }
    }
}
