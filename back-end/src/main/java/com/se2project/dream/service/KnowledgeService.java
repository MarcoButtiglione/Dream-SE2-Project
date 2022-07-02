package com.se2project.dream.service;

import com.se2project.dream.entity.*;
import com.se2project.dream.extraClasses.Liked;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KnowledgeService {
    private final KnowledgeRepository knowledgeRepository;
    private final AgronomistRepository agronomistRepository;
    private  final FarmerRepository farmerRepository;
    private final LikeKnowledgeService likeKnowledgeService;
    private final NotificationAgronomistRepository notificationAgronomistRepository;

    public KnowledgeService(KnowledgeRepository knowledgeRepository, AgronomistRepository agronomistRepository, FarmerRepository farmerRepository, LikeKnowledgeService likeKnowledgeService, NotificationAgronomistRepository notificationAgronomistRepository) {
        this.knowledgeRepository = knowledgeRepository;
        this.agronomistRepository = agronomistRepository;
        this.farmerRepository = farmerRepository;
        this.likeKnowledgeService = likeKnowledgeService;
        this.notificationAgronomistRepository = notificationAgronomistRepository;
    }

    /**
     * Get all knowledge and a boolean value isLiked that is true if the farmer logged in already liked the knowledge
     * @param farmerId of the logged in farmer
     * first is checked if the farmer exist by function findById @see FarmerRepository else @exception 404 Farmer Not Found
     * then with the function findAllKnowledge @see KnowledgeRepository it return all the knowledges
     * if no knowledges was found @exception 404 No Knowledge Found
     * else for each found using function isLiked @see likeKnowledgeService it return if the farmer logged in liked the knowledge
     * @return set of knowledge + isLiked
     */
    public Response getAllKnowledge(Long farmerId){
        Response response=new Response();
        Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
        if (farmer == null) {
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else {
            Iterable<Knowledge> knowledges = knowledgeRepository.findAllKnowledge();
            if (knowledges.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No Knowledge Found");
            } else {
                List<Liked> liked=new ArrayList<Liked>();
                for (Knowledge k : knowledges) {
                    if (likeKnowledgeService.isLiked(k.getKnowledgeId(), farmerId) == true) {
                        liked.add(new Liked(true,k));
                    }
                    else {
                        liked.add(new Liked(false,k));
                    }
                }
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(liked));
            }
        }
        return response;
    }

    /**
     * Get all knowledge of an agronomist
     * @param agronomistId of the logged in agronomist
     * first is checked if the agronomist exist by function findById @see agronomistRepository else @exception 404 Agronomist Not Found
     * then with the function findByAgronomist @see KnowledgeRepository it return all the knowledges
     * if no knowledges was found @exception 404 No Knowledge Found
     * else @return set of knowledge
     */
    public Response getAllKnowledgeByAgronomist(Long agronomistId){
        Response response=new Response();
        Agronomist agronomist = agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(400);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Iterable<Knowledge> knowledges = knowledgeRepository.findByAgronomist(agronomistId);
            if(knowledges.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Knowledge Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(knowledges));
            }
        }

        return response;
    }

    /**
     * Get all knowledge of an agronomist filtered by category
     * @param agronomistId of the logged in agronomist
     * @param category to filter knowledges
     * first is checked if the agronomist exist by function findById @see agronomistRepository else @exception 404 Agronomist Not Found
     * then with the function findByCategory @see KnowledgeRepository it return all the knowledges
     * if no knowledges was found @exception 404 No Knowledge Found
     * else @return set of knowledge
     */
    public Response getAllKnowledgeByCategory(String category, Long agronomistId){
        Response response=new Response();
        Agronomist agronomist = agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(400);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Iterable<Knowledge> knowledges = knowledgeRepository.findByCategory(category,agronomistId);
            if(knowledges.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Knowledge Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(knowledges));
            }
        }

        return response;
    }

    /**
     * Get all knowledge of an agronomist filtered by title
     * @param agronomistId of the logged in agronomist
     * @param title to filter knowledges
     * first is checked if the agronomist exist by function findById @see agronomistRepository else @exception 404 Agronomist Not Found
     * then with the function findByTitle @see KnowledgeRepository it return all the knowledges
     * if no knowledges was found @exception 404 No Knowledge Found
     * else @return set of knowledge
     */
    public Response getAllKnowledgeByTitle(String title, Long agronomistId){
        Response response=new Response();
        Agronomist agronomist = agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(400);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Iterable<Knowledge> knowledges = knowledgeRepository.findByTitle(title,agronomistId);
            if(knowledges.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Knowledge Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(knowledges));
            }
        }

        return response;
    }

    /**
     * Get all knowledge filtered by word and a boolean value isLiked that is true if the farmer logged in already liked the knowledge
     * @param farmerId of the logged in farmer
     * @param word to filter knowledges
     * first is checked if the farmer exist by function findById @see farmerRepository else @exception 404 Agronomist Not Found
     * then with the function findByWordF @see KnowledgeRepository it return all the knowledges
     * if no knowledges was found @exception 404 No Knowledge Found
     * else for each found using function isLiked @see likeKnowledgeService it return if the farmer logged in liked the knowledge
     * @return set of knowledge
     */
    public Response getAllKnowledgeByWordF(String word, Long farmerId){
        Response response=new Response();
        Iterable<Knowledge> knowledges = knowledgeRepository.findByWordF(word);
        if(knowledges.toString()=="[]"){
            response.setCode(404);
            response.setMessage("No Knowledge Found");
        }
        else{
            Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
            if (farmer == null) {
                response.setCode(404);
                response.setMessage("Farmer Not Found");
            }
            else {
                List<Liked> liked=new ArrayList<Liked>();
                for (Knowledge k : knowledges) {
                    if (likeKnowledgeService.isLiked(k.getKnowledgeId(), farmerId) == true) {
                        liked.add(new Liked(true,k));
                    }
                    else {
                        liked.add(new Liked(false,k));
                    }
                }
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(liked));
            }
        }
        return response;
    }

    /**
     * Get all knowledge of an agronomist filtered by word
     * @param agronomistId of the logged in agronomist
     * @param word to filter knowledges
     * first is checked if the agronomist exist by function findById @see agronomistRepository else @exception 404 Agronomist Not Found
     * then with the function findByWordA @see KnowledgeRepository it return all the knowledges
     * if no knowledges was found @exception 404 No Knowledge Found
     * else @return set of knowledge
     */
    public Response getAllKnowledgeByWordA(String word, Long agronomistId){
        Response response=new Response();
        Agronomist agronomist = agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(400);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Iterable<Knowledge> knowledges = knowledgeRepository.findByWordA(word,agronomistId);
            if(knowledges.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Knowledge Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(knowledges));
            }
        }

        return response;
    }

    /**
     * create a new Knowledge
     * @param agronomistId the agronmist that create the knowledge
     * @param newKnowledge knowledge data to insert
     * first is checked if the agronomist exist by function findById @ see agronomistRepository @exception 404 Agronomist Not Found
     * then the new knowledge is saved in the db
     * @return newKnowledge
     */
    public Response createKnowledge(Long agronomistId, Knowledge newKnowledge){
        Response response=new Response();
        Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(400);
            response.setMessage("Agronomist Not Found");
        }
        else{
            newKnowledge.setAgronomist(agronomist);
            newKnowledge.setDate();
            knowledgeRepository.save(newKnowledge);
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(newKnowledge));
        }
        return response;
    }

    /**
     * update a knowledge
     * @param agronomistId the agronomist that create the knowledge
     * @param knowledgeId the knowledge the comment is under to update
     * @param knowledgeTdo data to update
     * first is checked if the knowledge exist by function findById @ see knowledgeRepository @exception 404 Knowledge Not Found
     * then is checked if the agronomist exist by function findById @ see agronomistRepository @exception 404 Agronomist Not Found
     * then the knowledge is updated and saved in the db
     * @return updatedKnowledge
     */
    public Response updateKnowledge(Long knowledgeId, Long agronomistId, Knowledge knowledgeTdo){
        Response response=new Response();
        Knowledge knowledgeToUpdate=knowledgeRepository.findById(knowledgeId).orElse(null);
        if(knowledgeToUpdate==null){
            response.setCode(400);
            response.setMessage("Knowledge Not Found");
        }
        else{
            Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
            if(agronomist==null){
                response.setCode(400);
                response.setMessage("Agronomist Not Found");
            }
            else{
                knowledgeToUpdate.setTitle(knowledgeTdo.getTitle());
                knowledgeToUpdate.setDescription(knowledgeTdo.getDescription());
                knowledgeToUpdate.setArticle(knowledgeTdo.getArticle());
                knowledgeToUpdate.setCategory(knowledgeTdo.getCategory());
                knowledgeToUpdate.setAgronomist(agronomist);
                knowledgeToUpdate.setDate();
                knowledgeRepository.save(knowledgeToUpdate);
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(knowledgeToUpdate));

            }
        }

        return response;
    }

    /**
     * like a Knowledge
     * @param farmerId the farmer that liked the comment
     * @param knowledgeId knowledge to like
     * first is checked if the knowledge exist by function findById @ see knowledgeRepository @exception 404 Comment Not Found
     * then is checked if the farmer exist by function findById @ see farmerRepository @exception 404 Farmer Not Found
     * then the like to the knwoledge is saved in the db and a notification is saved for the creator of the knowledge
     * @return likedKnowledge
     */
    public Response putLikeKnowledge(Long knowledgeId,Long farmerId){
        Response response=new Response();
        Knowledge knowledgeLiked=knowledgeRepository.findById(knowledgeId).orElse(null);
        if(knowledgeLiked==null){
            response.setCode(400);
            response.setMessage("Knowledge Not Found");
        }
        else{
            Farmer farmer =farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(404);
                response.setMessage("Farmer Not Found");
            }
            else {
                likeKnowledgeService.likeKnowledge(knowledgeLiked,farmer);
                Agronomist knowledgeCreator= agronomistRepository.findById(knowledgeLiked.getAgronomist()).orElse(null);
                if(knowledgeCreator!=null) {
                    String desc= farmer.getLastName()+" liked your knowledge: "+ knowledgeLiked.getTitle();
                    NotificationAgronomist notification = new NotificationAgronomist("LIKE_KNOWLEDGE", desc,knowledgeCreator,null,null);
                    notificationAgronomistRepository.save(notification);
                }
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(knowledgeLiked));
            }
        }
        return response;
    }

    /**
     * inlike a Knowledge
     * @param farmerId the farmer that unliked the comment
     * @param knowledgeId knowledge to unlike
     * first is checked if the knowledge exist by function findById @ see knowledgeRepository @exception 404 Comment Not Found
     * then is checked if the farmer exist by function findById @ see farmerRepository @exception 404 Farmer Not Found
     * then the like to the knwoledge is deleed from the db
     * @return unlikedKnowledge
     */
    public Response putUnLikeKnowledge(Long knowledgeId,Long farmerId){
        Response response=new Response();
        Knowledge knowledgeUnLiked=knowledgeRepository.findById(knowledgeId).orElse(null);
        if(knowledgeUnLiked==null){
            response.setCode(400);
            response.setMessage("Knowledge Not Found");
        }
        else{
            Farmer farmer =farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(404);
                response.setMessage("Farmer Not Found");
            }
            else {

                likeKnowledgeService.unLikeKnowledge(knowledgeUnLiked,farmer);
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(knowledgeUnLiked));
            }
        }
        return response;
    }

    /**
     * delete a Knowledge
     * @param agronomistId the agronomist that delete the knowledge
     * @param knowledgeId knowledge to like
     * first is checked if the knowledge exist by function findById @ see knowledgeRepository @exception 404 Comment Not Found
     * then is checked if the agronomist exist by function findById @ see agronomistRepository @exception 404 Agronomist Not Found
     * then is checked that the agronomist who is deleting the knowledge is the one who creates it @exception 400 You cannot delete this message
     * then the  knwoledge is deleted from db
     * @return deletedKnowledge
     */
    public Response deleteKnowledge(Long knowledgeId, Long agronomistId){
        Response response=new Response();
        Knowledge knowledgeToDelete=knowledgeRepository.findById(knowledgeId).orElse(null);
        if(knowledgeToDelete==null){
            response.setCode(400);
            response.setMessage("Knowledge Not Found");
        }
        else{
            Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
            if(agronomist==null){
                response.setCode(404);
                response.setMessage("Agronomist Not Found");
            }
            else {
                if(knowledgeToDelete.getAgronomist()==agronomistId) {
                    knowledgeRepository.delete(knowledgeToDelete);
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(knowledgeToDelete));
                }
                else{
                    response.setCode(400);
                    response.setMessage("You cannot delete this message");
                }
            }
        }
        return response;
    }

}
