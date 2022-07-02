package com.se2project.dream.repository;

import com.se2project.dream.entity.LikeKnwoledge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LikeKnowledgeRepository extends CrudRepository<LikeKnwoledge, Long> {

    @Query(value = "SELECT * FROM like_knwoledge WHERE knowledge_knowledge_id=  ?1  and farmer_farmer_id=  ?2 ", nativeQuery = true)
    LikeKnwoledge likesKnowledge(Long KnowledgeId, Long FarmerId);
}
