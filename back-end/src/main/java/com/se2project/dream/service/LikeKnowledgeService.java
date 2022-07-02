package com.se2project.dream.service;

import com.se2project.dream.entity.*;
import com.se2project.dream.repository.LikeKnowledgeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeKnowledgeService {
    private final LikeKnowledgeRepository likeKnowledgeRepository;

    public LikeKnowledgeService(LikeKnowledgeRepository likeKnowledgeRepository) {
        this.likeKnowledgeRepository = likeKnowledgeRepository;
    }

    /**
     * Method to return if a Knowledge is liked or not
     * @param knowledgeId
     * @param farmerId
     * @return boolean
     */
    public boolean isLiked(Long knowledgeId, Long farmerId){
        LikeKnwoledge lk = likeKnowledgeRepository.likesKnowledge(knowledgeId, farmerId);
        if(lk==null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Method to like a given Knowledge
     * @param knowledge
     * @param farmer
     */
    public void likeKnowledge(Knowledge knowledge, Farmer farmer){
        LikeKnwoledge check=likeKnowledgeRepository.likesKnowledge(knowledge.getKnowledgeId(),farmer.getId());
        if(check==null){
            knowledge.like();
            LikeKnwoledge like = new LikeKnwoledge(knowledge,farmer);
            likeKnowledgeRepository.save(like);
            return;
        }
        else
        {
            return;
        }

    }

    /**
     * Method to unlike a given Knowledge
     * @param knowledge
     * @param farmer
     */
    public void unLikeKnowledge(Knowledge knowledge, Farmer farmer) {
        LikeKnwoledge unlike = likeKnowledgeRepository.likesKnowledge(knowledge.getKnowledgeId(),farmer.getId());
        if(unlike==null){
            return;
        }
        else
        {
            knowledge.unlike();
            likeKnowledgeRepository.delete(unlike);
            return;
        }
    }
}
