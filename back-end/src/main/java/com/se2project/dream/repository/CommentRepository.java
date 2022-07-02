package com.se2project.dream.repository;
import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.Comment;
import com.se2project.dream.entity.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository  extends CrudRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE topic_fk = ?1 ORDER BY date ", nativeQuery = true)
    Iterable<Comment> findAllComment(Long topicId);
}
