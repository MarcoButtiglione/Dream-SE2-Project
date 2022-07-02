package com.se2project.dream.controller;

import com.se2project.dream.entity.*;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.service.CommentService;
import com.se2project.dream.service.LoginService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")

public class CommentController {

    private final CommentService commentService;
    private final LoginService loginService;
    public CommentController(CommentService commentService, LoginService loginService) {
        this.commentService = commentService;
        this.loginService = loginService;
    }

    /**
     * Get all comment of a topic and a boolean value isLiked that is true if the farmer logged in already liked the comment
     * @param tokenF farmer authentication token generated on login
     * @param topicId of the topic the farmer want to see the comments
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function getallComment @see CommentService it get all the comments
     * @return set of comments + isLiked
     * @exception 404 Topic Not Found
     * @exception 404 Farmer Not Found
     * @exception 404 No Comment Found
     * */
    @GetMapping("/farmer/getAllComment/{topicId}")
    Response getAllComment(@RequestHeader("Authorization") String tokenF, @PathVariable Long topicId){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return commentService.getAllComment(topicId,farmerId);
    }

    /**
     * Function that allow the logged farmer to like the comment if it not already liked
     * @param tokenF farmer authentication token generated on login
     * @param commentId of the comment the farmer want to put like
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function putLikeComment @see CommentService it put a like on the comment
     * return likedComment
     * @exception 404 Comment Not Found
     * @exception 404 Farmer Not Found
     * */
    @GetMapping("/farmer/LikeComment/{commentId}")
    Response LikeComment(@RequestHeader("Authorization") String tokenF, @PathVariable Long commentId){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
      return commentService.putLikeComment(commentId,farmerId);
    }

    /**
     * Function that allow the logged farmer to unlike the comment if it already liked
     * @param tokenF farmer authentication token generated on login
     * @param commentId of the comment the farmer want to put like
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function putUnlikeComment @see CommentService it delete the like on the comment
     * return unLikedComment
     * @exception 404 Comment Not Found
     * @exception 404 Farmer Not Found
     * */
    @GetMapping("/farmer/UnLikeComment/{commentId}")
    Response UnLikeComment(@RequestHeader("Authorization") String tokenF, @PathVariable Long commentId){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return commentService.putUnlikeComment(commentId, farmerId);
    }

    /**
     * Function that allow the logged farmer to change his comment under a topic
     * @param tokenF farmer authentication token generated on login
     * @param commentId of the comment the farmer want to update
     * @param topicId of the topic that the comment is about
     * @param commentTdo comment new value
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function updateComment @see CommentService it update the comment
     * return updatedComment
     * @exception 404 Comment Not Found
     * @exception 404 Farmer Not Found
     * @exception 404 Topic Not Found
     * */
    @PostMapping("/farmer/PutComment/{topicId}")
    Response updateComment(@RequestParam Long commentId, @RequestHeader("Authorization") String tokenF, @PathVariable Long topicId, @RequestBody Comment commentTdo){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return commentService.updateComment(commentId,farmerId,topicId,commentTdo);
    }

    /**
     * Function that allow the logged farmer to create a comment under a topic
     * @param tokenF farmer authentication token generated on login
     * @param topicId of the topic that the farmer want to comment
     * @param newComment comment to insert
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function createComment @see CommentService create the comment on the topic
     * return newComment
     * @exception 404 Topic Not Found
     * @exception 404 Farmer Not Found
     * */
    @PostMapping("/farmer/PostComment/{topicId}")
    Response createComment(@RequestHeader("Authorization") String tokenF,@PathVariable Long topicId,@RequestBody Comment newComment){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return commentService.createComment(farmerId,topicId,newComment);
    }

    /**
     * Function that allow the logged farmer to delete his comment under a topic
     * @param tokenF farmer authentication token generated on login
     * @param commentId of the comment that the farmer want to delete
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then the function deletComment @see CommentService delete the comment on the topic
     * return deletedComment
     * @exception 404 Comment Not Found
     * @exception 404 Farmer Not Found
     * @exception 400 You cannot delete this comment (when an user try to delete a comment he did not create)
     * */
    @GetMapping("/farmer/DeleteComment/{commentId}")
    Response deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return commentService.deleteComment(commentId,farmerId);
    }
}
