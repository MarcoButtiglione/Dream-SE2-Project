package com.se2project.dream.repository;

import com.se2project.dream.entity.Comment;
import com.se2project.dream.entity.LikeComment;
import com.se2project.dream.entity.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LikeCommentRepository  extends CrudRepository<LikeComment, Long> {

    @Query(value = "SELECT * FROM like_comment WHERE comment_comment_id =  ?1  and farmer_farmer_id =  ?2 ", nativeQuery = true)
    LikeComment likesComment(Long CommentId, Long FarmerId);
}
