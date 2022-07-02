package com.se2project.dream.service;

import com.se2project.dream.entity.*;
import com.se2project.dream.extraClasses.Liked;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.repository.CommentRepository;
import com.se2project.dream.repository.FarmerRepository;
import com.se2project.dream.repository.NotificationFarmerRepository;
import com.se2project.dream.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentService {

    private final TopicRepository topicRepository;
    private final FarmerRepository farmerRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentService likeCommentService;
    private final NotificationFarmerRepository notificationFarmerRepository;

    public CommentService(TopicRepository topicRepository, FarmerRepository farmerRepository, CommentRepository commentRepository, LikeCommentService likeCommentService, NotificationFarmerRepository notificationFarmerRepository) {
        this.topicRepository = topicRepository;
        this.farmerRepository = farmerRepository;
        this.commentRepository = commentRepository;
        this.likeCommentService = likeCommentService;
        this.notificationFarmerRepository = notificationFarmerRepository;
    }

    /**
     * Get all comment of a topic and a boolean value isLiked that is true if the farmer logged in already liked the comment
     * @param farmerId of the logged in farmer
     * @param topicId of the topic the farmer want to see the comments
     * first is checked if the topic exist by function findById @see TopicRepository else @exception 404 Topic Not Found
     * then is checked if the farmer exist by function findById @see FarmerRepository else @exception 404 Farmer Not Found
     * Lastly with the function findAllComment @see CommentRepository it return all the comments
     * if no comments was found @exception 404 No Comment Found
     * else for each found using function isLiked @see likeCommentService it return if the farmer logged in liked the comment
     * @return set of comments + isLiked
     */
    public Response getAllComment(Long topicId, Long farmerId){
        Response response=new Response();
        Topic topic=topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            response.setCode(400);
            response.setMessage("Topic Not Found");
        }
        else {
            Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
            if (farmer == null) {
                response.setCode(404);
                response.setMessage("Farmer Not Found");
            }
            else {
                Iterable<Comment> comments = commentRepository.findAllComment(topicId);
                if (comments.toString() == "[]") {
                    response.setCode(404);
                    response.setMessage("No Comment Found");
                } else {
                    List<Liked> liked=new ArrayList<Liked>();
                    for (Comment c : comments) {
                        if (likeCommentService.isLiked(c.getCommentId(), farmerId) == true) {
                            liked.add(new Liked(true,c));
                        }
                        else {
                            liked.add(new Liked(false,c));
                        }
                    }
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(liked));
                }
            }
        }
        return response;

    }

    /**
     * create a new Comment
     * @param farmerId the farmer that create the comment
     * @param topicId the topic the comment is under
     * @param newComment comment data to insert
     * first is checked if the farmer exist by function findById @ see farmerRepository @exception 404 Farmer Not Found
     * then the topic with the function findById @see TopicRepository @exception Topic Not Found
     * then the new comment is saved in the db and a notification is inserted in the db for the creator of the topic
     * @return newComment
     */
    public Response createComment(Long farmerId, Long topicId ,Comment newComment){
        Response response=new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(400);
            response.setMessage("Farmer Not Found");
        }
        else{
            Topic topic= topicRepository.findById(topicId).orElse(null);
            if(topic==null){
                response.setCode(400);
                response.setMessage("Topic Not Found");
            }
            else{
                newComment.setFarmer(farmer);
                newComment.setTopic(topic);
                newComment.setDate();
                commentRepository.save(newComment);
                Farmer topicCreator= farmerRepository.findById(topic.getFarmer()).orElse(null);
                if(topicCreator!=null & topicCreator!=farmer) {
                    String desc= farmer.getFirstName()+" commented your topic: "+ topic.getTopic();
                    NotificationFarmer notification = new NotificationFarmer("NEW_COMMENT", desc,topicCreator,null);
                    notificationFarmerRepository.save(notification);
                }
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(newComment));
            }
        }
        return response;
    }

    /**
     * update a Comment
     * @param farmerId the farmer that create the comment
     * @param topicId the topic the comment is under
     * @param commentId comment to update
     * @param commentTdo data to update
     * first is checked if the comment exist by function findById @ see commentRepository @exception 404 Comment Not Found
     * then is checked if the farmer exist by function findById @ see farmerRepository @exception 404 Farmer Not Found
     * then the topic with the function findById @see TopicRepository @exception Topic Not Found
     * then the comment is updated and saved in the db
     * @return updatedComment
     */
    public Response updateComment(Long commentId, Long farmerId, Long topicId, Comment commentTdo){
        Response response=new Response();
        Comment commentToUpdate=commentRepository.findById(commentId).orElse(null);
        if(commentToUpdate==null){
            response.setCode(400);
            response.setMessage("Comment Not Found");
        }
        else{
            Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(400);
                response.setMessage("Farmer Not Found");
            }
            else{
                Topic topic= topicRepository.findById(topicId).orElse(null);
                if(topic==null){
                    response.setCode(400);
                    response.setMessage("Topic Not Found");
                }
                else {
                    commentToUpdate.setTopic(topic);
                    commentToUpdate.setFarmer(farmer);
                    commentToUpdate.setComment(commentTdo.getComment());
                    commentRepository.save(commentToUpdate);
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(commentToUpdate));
                }

            }
        }

        return response;
    }

    /**
     * like a Comment
     * @param farmerId the farmer that liked the comment
     * @param commentId comment to like
     * first is checked if the comment exist by function findById @ see commentRepository @exception 404 Comment Not Found
     * then is checked if the farmer exist by function findById @ see farmerRepository @exception 404 Farmer Not Found
     * then the like to the comment is saved in the db and a notification is saved for the creator of the topic
     * @return likedComment
     */
    public Response putLikeComment(Long commentId, Long farmerId){
        Response response=new Response();
        Comment commentLiked=commentRepository.findById(commentId).orElse(null);
        if(commentLiked==null){
            response.setCode(400);
            response.setMessage("Comment Not Found");
        }
        else{
            Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(400);
                response.setMessage("Farmer Not Found");
            }
            else {
                likeCommentService.likeComment(commentLiked,farmer);
                Farmer commentCreator= farmerRepository.findById(commentLiked.getFarmer()).orElse(null);
                if(commentCreator!=null && commentCreator != farmer ) {
                    String desc= farmer.getFirstName()+" liked your comment: "+ commentLiked.getComment();
                    NotificationFarmer notification = new NotificationFarmer("LIKE_COMMENT", desc,commentCreator,null);
                    notificationFarmerRepository.save(notification);
                }
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(commentLiked));
            }
        }
        return response;
    }

    /**
     * unlike a Comment
     * @param farmerId the farmer that unliked the comment
     * @param commentId comment to like
     * first is checked if the comment exist by function findById @ see commentRepository @exception 404 Comment Not Found
     * then is checked if the farmer exist by function findById @ see farmerRepository @exception 404 Farmer Not Found
     * then the like to the comment is deleted from the db
     * @return unlikedComment
     */
    public Response putUnlikeComment(Long commentId, Long farmerId){
        Response response=new Response();
        Comment commentUnLiked=commentRepository.findById(commentId).orElse(null);
        if(commentUnLiked==null){
            response.setCode(400);
            response.setMessage("Comment Not Found");
        }
        else{
            Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(400);
                response.setMessage("Farmer Not Found");
            }
            else {
                likeCommentService.unLikeComment(commentUnLiked,farmer);
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(commentUnLiked));
            }
        }
        return response;
    }

    /**
     * delete a Comment
     * @param farmerId the farmer that delete the comment
     * @param commentId comment to delete
     * first is checked if the comment exist by function findById @see commentRepository @exception 404 Comment Not Found
     * then is checked if the farmer exist by function findById @see farmerRepository @exception 404 Farmer Not Found
     * if the farmerId is diffrent from the creator of the comment @exception 404 You cannot delete this comment
     * else the comment is deleted from the db
     * @return commentToDelete
     */
    public Response deleteComment(Long commentId, Long farmerId){
        Response response=new Response();
        Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else {
            Comment commentToDelete = commentRepository.findById(commentId).orElse(null);
            if (commentToDelete == null) {
                response.setCode(404);
                response.setMessage("Comment Not Found");
            } else {
                if (commentToDelete.getFarmer() == farmerId) {
                    commentRepository.delete(commentToDelete);
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(commentToDelete));
                } else {
                    response.setCode(400);
                    response.setMessage("You cannot delete this comment");
                }
            }
        }
            return response;
    }
}
