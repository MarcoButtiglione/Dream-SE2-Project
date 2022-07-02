package com.se2project.dream.service;

import com.se2project.dream.entity.Comment;
import com.se2project.dream.entity.Farmer;
import com.se2project.dream.entity.LikeComment;
import com.se2project.dream.repository.CommentRepository;
import com.se2project.dream.repository.FarmerRepository;
import com.se2project.dream.repository.LikeCommentRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeCommentService {
    private final LikeCommentRepository likeCommentRepository;

    public LikeCommentService(LikeCommentRepository likeCommentRepository) {
        this.likeCommentRepository = likeCommentRepository;
    }

    /**
     * Method to return if a Comment is liked or not
      * @param commentId
     * @param farmerId
     * @return boolean
     */
    public boolean isLiked(Long commentId, Long farmerId){
        LikeComment lk = likeCommentRepository.likesComment(commentId, farmerId);
        if(lk==null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Method to like a given Comment
      * @param comment
     * @param farmer
     */
    public void likeComment(Comment comment, Farmer farmer){
        LikeComment check = likeCommentRepository.likesComment(comment.getCommentId(),farmer.getId());
        if(check==null){
            comment.setLikes();
            LikeComment like=new LikeComment(comment,farmer);
            likeCommentRepository.save(like);
            return;
        }
        else
        {
            return;
        }

    }

    /**
     * Method to unlike a given Comment
     * @param comment
     * @param farmer
     */
    public void unLikeComment(Comment comment, Farmer farmer) {
        LikeComment unlike=likeCommentRepository.likesComment(comment.getCommentId(),farmer.getId());
        if(unlike==null){
            return;
        }
        else {
            comment.unlike();
            likeCommentRepository.delete(unlike);
            return;
        }
    }
}
