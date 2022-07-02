package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Knowledge;
import com.se2project.dream.service.KnowledgeService;
import com.se2project.dream.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")

public class KnowledgeController {
    private final KnowledgeService knowledgeService;
    private final LoginService loginService;

    public KnowledgeController(KnowledgeService knowledgeService, LoginService loginService) {
        this.knowledgeService = knowledgeService;
        this.loginService = loginService;
    }

    /**
     * Get all knowledge and a boolean value isLiked that is true if the farmer logged in already liked the knowledge
     * @param tokenF farmer authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function getAllKnowledge @see KnowledgeService it get all the knowledges
     * @return set of knowledges + isLiked
     * @exception 404 Farmer Not Found
     * @exception 404 No Knwoledge Found
     * */
    @GetMapping("/farmer/getAllKnowledge")
    Response getAllKnowledge(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return knowledgeService.getAllKnowledge(farmerId);
    }

    /**
     * Get all knowledge created by the logged Agronomist
     * @param tokenA agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then with the function getAllKnowledgeByAgronomist @see KnowledgeService it get all the knowledges
     * @return set of knowledges
     * @exception 404 Agronomist Not Found
     * @exception 404 No Knwoledge Found
     * */
    @GetMapping("/agronomist/getAllKnowledgeByAgronomist")
    Response getAllKnowledgeByAgronomist(@RequestHeader("Authorization") String tokenA){
        Long agronomist =loginService.getIdFromTokenA(tokenA);
        return knowledgeService.getAllKnowledgeByAgronomist(agronomist);
    }

    /**
     * Get all knowledge created by the logged Agronomist filtred by category
     * @param tokenA agronomist authentication token generated on login
     * @param category string typed from agronomist
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then with the function getAllKnowledgeByCategory @see KnowledgeService it get all the knowledges
     * @return set of knowledges
     * @exception 404 Agronomist Not Found
     * @exception 404 No Knwoledge Found
     * */
    @GetMapping("/agronomist/getAllKnowledgeByCategory")
    Response getAllKnowledgeByCategory(@RequestParam String category, @RequestHeader("Authorization") String  tokenA) {
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return knowledgeService.getAllKnowledgeByCategory(category,agronomistId);
    }

    /**
     * Get all knowledge created by the logged Agronomist filtred by title
     * @param tokenA agronomist authentication token generated on login
     * @param title string typed from agronomist
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then with the function getAllKnowledgeByTitle @see KnowledgeService it get all the knowledges
     * @return set of knowledges
     * @exception 404 Agronomist Not Found
     * @exception 404 No Knwoledge Found
     * */
    @GetMapping("/agronomist/getAllKnowledgeByTitle")
    Response getAllKnowledgeByTitle(@RequestParam String title, @RequestHeader("Authorization") String tokenA){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return knowledgeService.getAllKnowledgeByTitle(title, agronomistId);
    }

    /**
     * Get all knowledge and a boolean value isLiked that is true if the farmer logged in already liked the knowledge
     * filtred by word, the search is made between title, article and category.
     * @param tokenF farmer authentication token generated on login
     * @param word string typed from farmer
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function getAllKnowledgeByWordF @see KnowledgeService it get all the knowledges + isLiked
     * @return set of knowledges + isLiked
     * @exception 404 Farmer Not Found
     * @exception 404 No Knwoledge Found
     * */
    @GetMapping("/farmer/getAllKnowledgeByWordF")
    Response getAllKnowledgeByWordF(@RequestHeader("Authorization") String tokenF, @RequestParam String word){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return knowledgeService.getAllKnowledgeByWordF(word,farmerId);
    }

    /**
     * Get all knowledge created by the logged Agronomist filtred by word, the search is made between title, article and category.
     * @param tokenA farmer authentication token generated on login
     * @param word string typed from agronomist
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function getAllKnowledgeByWordA @see KnowledgeService it get all the knowledges
     * @return set of knowledges
     * @exception 404 Agronomist Not Found
     * @exception 404 No Knwoledge Found
     * */
    @GetMapping("/agronomist/getAllKnowledgeByWordA")
    Response getAllKnowledgeByWordA(@RequestParam String word, @RequestHeader("Authorization") String tokenA){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return knowledgeService.getAllKnowledgeByWordA(word, agronomistId);
    }

    /**
     * Function that allow the logged farmer to like the knowledge if it not already liked
     * @param tokenF farmer authentication token generated on login
     * @param knowledgeId of the knowledge the farmer want to put like
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function putLikeKnowledge @see KnowledgeService it put a like on the knowledge
     * return likedKnowledge
     * @exception 404 Knowledge Not Found
     * @exception 404 Farmer Not Found
     * */
    @GetMapping("/farmer/LikeKnowledge/{knowledgeId}")
    Response LikeKnowledge(@RequestHeader("Authorization") String tokenF, @PathVariable Long knowledgeId){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return knowledgeService.putLikeKnowledge(knowledgeId,farmerId);
    }

    /**
     * Function that allow the logged farmer to unlike the knowledge if it already liked
     * @param tokenF farmer authentication token generated on login
     * @param knowledgeId of the knowledge the farmer want to put like
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function putUnLikeKnowledge @see KnowledgeService it deletes the like on the knowledge
     * return unLikedKnowledge
     * @exception 404 Knowledge Not Found
     * @exception 404 Farmer Not Found
     * */
    @GetMapping("/farmer/UnLikeKnowledge/{knowledgeId}")
    Response UnLikeKnowledge(@RequestHeader("Authorization") String tokenF, @PathVariable Long knowledgeId){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return knowledgeService.putUnLikeKnowledge(knowledgeId,farmerId);
    }

    /**
     * Function that allow the logged agronomist to change his knowledge
     * @param tokenA agronomist authentication token generated on login
     * @param knowledgeId of the knowledge the agronomist want to update
     * @param knowledgeTdo knowledge new value
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then  with the function updateKnowledge @see KnowledgeService it update the knowledge
     * return updatedKnowledge
     * @exception 404 Knowledge Not Found
     * @exception 404 Agronomist Not Found
     */
    @PostMapping("/agronomist/PutKnowledge/{knowledgeId}")
    Response updateKnowledge(@PathVariable Long knowledgeId, @RequestHeader("Authorization") String tokenA,@RequestBody Knowledge knowledgeTdo){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return knowledgeService.updateKnowledge(knowledgeId,agronomistId,knowledgeTdo);
    }

    /**
     * Function that allow the logged agronomist to create a knowledge
     * @param tokenA agronomist authentication token generated on login
     * @param newKnowledge knowledge to insert
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then  with the function createKnowledge @see KnowledgeService create the knowledge
     * @return newKnowledge
     * @exception 404 Agronomist Not Found
     * */
    @PostMapping("/agronomist/PostKnowledge")
    Response createKnowledge(@RequestHeader("Authorization") String tokenA,@RequestBody Knowledge newKnowledge){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return knowledgeService.createKnowledge(agronomistId,newKnowledge);
    }

    /**
     * Function that allow the logged agronomist to create a knowledge
     * @param tokenA agronomist authentication token generated on login
     * @param knowledgeId knowledge to delete
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then  with the function deleteKnowledge @see KnowledgeService delete the knowledge
     * @return knowledgeDeleted
     * @exception 404 Agronomist Not Found
     * @exception 404 Knowledge Not Found

     * */
    @GetMapping("/agronomist/DeleteKnowledge/{knowledgeId}")
    Response deleteKnowledge(@PathVariable Long knowledgeId,@RequestHeader("Authorization") String tokenA){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return knowledgeService.deleteKnowledge(knowledgeId, agronomistId);
    }
}
