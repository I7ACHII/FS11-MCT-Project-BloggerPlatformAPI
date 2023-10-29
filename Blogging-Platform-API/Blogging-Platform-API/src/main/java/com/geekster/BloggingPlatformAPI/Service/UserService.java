package com.geekster.BloggingPlatformAPI.Service;


import com.geekster.BloggingPlatformAPI.Model.*;
import com.geekster.BloggingPlatformAPI.Model.dto.SignInInput;
import com.geekster.BloggingPlatformAPI.Model.dto.SignUpOutput;
import com.geekster.BloggingPlatformAPI.Repository.IUserRepo;
import com.geekster.BloggingPlatformAPI.Service.hashingUtility.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepo iUserRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    FollowService followService;

    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        // checking if user has provided a null email ID
        String newEmail = user.getUserEmail();
        if(newEmail == null){
            signUpStatusMessage = "Invalid email!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        // check if this user email already exists??
        User existingUser = iUserRepo.findFirstByUserEmail(newEmail);
        if(existingUser != null){
            signUpStatusMessage = "Email already registered!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(user.getUserPassword());

            //save the user with the new encrypted password
            user.setUserPassword(encryptedPassword);
            iUserRepo.save(user);
            return new SignUpOutput(signUpStatus, "User registered successfully!!!");

        } catch (NoSuchAlgorithmException e) {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }


    }

    public String signInUser(SignInInput signInInput) {

        // If email provided by user for sign in is null, then it will return invalid email
        String signInEmail = signInInput.getEmail();
        if(signInEmail == null) {
            return "Invalid Email";
        }

        //check if this user email already exists ??
        User existingUser = iUserRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null) {
            return "Email not registered!!!";
        }

        //match passwords :
        //hash the password: encrypt the password

        try {
            String encryptPassword = PasswordEncryptor.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptPassword)){

                AuthenticationToken authenticationToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authenticationToken);
                return "Sign In successful!!";
            }
            else{
                return "Invalid credentials!!";
            }
        } catch (Exception e) {
            return "Internal error occurred during sign in";
        }

    }

    public String sigOutUser(String email) {
        User user = iUserRepo.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "User has signed out successfully";
    }

    public String createPost(Post post, String postOwnerEmail) {
        User postOwner = iUserRepo.findFirstByUserEmail(postOwnerEmail);
        post.setPostOwner(postOwner);
        return postService.createPost(post);
    }

    public String removePost(Long postId, String email) {
        return postService.removePost(postId, email);
    }

    public String addPostComment(Comment comment, String commenterEmail) {

        if(postService.validatePost(comment.getPost())){
            comment.setCommenter(iUserRepo.findFirstByUserEmail(commenterEmail));
            return commentService.addPostComment(comment);
        }
        else{
            return "Post not available!!";
        }
    }

    public String removeComment(Long commentId, String deletersEmail) {
        if(validateUser(commentId, deletersEmail)){
            return commentService.removeComment(commentId);
        }
        else{
            return "Comment cannot be deleted!!";
        }
    }

    private boolean validateUser(Long commentId, String deletersEmail) {
        Comment comment = commentService.getCommentById(commentId);
        if(comment == null){
            return false;
        }
        String commenterEmail = comment.getCommenter().getUserEmail();
        String comments_postOwnerEmail = comment.getPost().getPostOwner().getUserEmail();

        if(commenterEmail.equals(deletersEmail) || comments_postOwnerEmail.equals(deletersEmail)){
            return true;
        }
        return false;
    }

    public String followSomeone(Follow follow, String usersFollowerEmail) {
        User user = iUserRepo.findById(follow.getUser().getUserId()).orElse(null);
        if(user != null){

            User usersFollower = iUserRepo.findFirstByUserEmail(usersFollowerEmail);
            if(user.getUserId().equals(usersFollower.getUserId())){
                return "You cannot follow Yourself!!";
            }
            if( followService.followAllowedOrNot(user, usersFollower)){
                follow.setUsersFollower(usersFollower);
                followService.followSomeone(follow);
                return "follow successful";
            }
            else{
                return "already following this user";
            }
        }
        else{
            return "Not a valid user!!";
        }
    }

    public String unfollowSomeone(Long followId, String unfollowerEmail) {
        Follow followRecord = followService.findById(followId);
        if(followRecord != null){

            if( unfollowAllowedOrNot(followRecord, unfollowerEmail)){
                return followService.unfollowSomeone(followRecord);
            }
            else{
                return "Unauthorized unfollow detected...Not allowed!!!!";
            }
        }
        else{
            return "Invalid follow mapping!!";
        }
    }

    private boolean unfollowAllowedOrNot(Follow followRecord, String unfollowerEmail) {
        // taking out user from provided email ID
        User unfollowingUser = iUserRepo.findFirstByUserEmail(unfollowerEmail);

        // Taking out usersFollower from followRecord that we have get above
        User usersFollower = followRecord.getUsersFollower();

        return unfollowingUser.equals(usersFollower) || unfollowerEmail.equals(followRecord.getUser().getUserEmail());

    }

    public List<Post> getAllPostOfUser(Long userId) {
        User existingUser = iUserRepo.findById(userId).orElse(null);
        if(existingUser != null){
            return postService.getAllPostOfUser(existingUser);
        }
        else{
            return null;
        }
    }

    public List<Comment> getAllCommentsOfPost(Long postId) {
        Post existingPost = postService.findByPostId(postId);
        if(existingPost != null){
            return commentService.getAllCommentsOfPost(existingPost);
        }
        else{
            return null;
        }
    }
}
