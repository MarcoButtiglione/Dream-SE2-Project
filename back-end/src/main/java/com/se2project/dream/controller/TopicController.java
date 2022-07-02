package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Topic;
import com.se2project.dream.service.LoginService;
import com.se2project.dream.service.TopicService;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api")

@CrossOrigin
@RestController
public class TopicController {
   private final TopicService topicService;
   private final LoginService loginService;

    public TopicController(TopicService topicService, LoginService loginService) {
        this.topicService = topicService;
        this.loginService = loginService;
    }

    /**
     * Gets a Topic instance identified by a given topicId
      * @param topicId
     * @return Topic
     * @Exception 404 No topic found
     */
    @GetMapping("/farmer/getTopicById/{topicId}")
    Response getTopicById(@PathVariable Long topicId){
        return topicService.getTopicById(topicId);
    }

    /**
     * Get all Topic instances
      * @return Topic
     * @Exception 404 No topic found
     */
    @GetMapping("/farmer/getAllTopic/")
    Response getAllTopic(){
        return topicService.getAllTopic();
    }

    /**
     * Get all topics related to a given farmer
      * @param tokenF which is decodified by the function @see GetIdFromTokenF
     * @return Response
     * @Exception 400 Farmer not found
     * @Exception 404 No topics found
     */
    @GetMapping("/farmer/getAllTopicByFarmer/")
    Response getAllTopicByFarmer(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return topicService.getAllTopicByFarmer(farmerId);
    }

    /**
     * Get all topics related to a given tag
      * @param tag
     * @param tokenF which is decodified by the function @see GetIdFromTokenF
     * @return Topic
     * @Exception 400 Farmer not found
     * @Exception 404 No topics found
     */
    @GetMapping("/farmer/getAllTopicByTag/")
    Response getAllTopicByTag(@RequestParam String tag, @RequestHeader("Authorization") String tokenF) {
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return topicService.getAllTopicByTag(farmerId,tag);
    }

    /**
     * Get all Topic related to a given word
      * @param word
     * @return Topic
     * @Exception 404 No Topics Found
     */
    @GetMapping("/farmer/getAllTopicByWord/")
    Response getAllTopicByWord(@RequestParam String word){
        return topicService.getAllTopicByWord(word);
    }

    /**
     * Get all Topic related to a given word and a farmerId
      * @param word
     * @param tokenF which is decodified by the function @see GetIdFromTokenF
     * @return Topic
     * @Exception 400 Farmer not found
     * @Exception 404 No topic found
     */
    @GetMapping("/farmer/getAllTopicByWordF/")
    Response getAllTopicByWordF(@RequestParam String word, @RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return topicService.getAllTopicByWordF(word, farmerId);
    }

    /**
     * Update a Topic identified by a given Id
      * @param topicId
     * @param tokenF which is decodified by the function @see GetIdFromTokenF
     * @param topicTdo Topic to update
     * @return Topic
     * @Exception 400 Topic not found
     * @Exception 400 Farmer not found
     */
    @PostMapping("/farmer/PutTopic/{topicId}")
    Response updateTopic(@PathVariable Long topicId, @RequestHeader("Authorization") String tokenF, @RequestBody Topic topicTdo){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return topicService.updateTopic(topicId,farmerId,topicTdo);
    }

    /**
     * Create new Topic
      * @param tokenF which is decodified by the function @see GetIdFromTokenF
     * @param newTopic
     * @return Topic
     * @Exception 400 Agronomist Not Found
     */
    @PostMapping("/farmer/PostTopic/")
    Response createTopic(@RequestHeader("Authorization") String tokenF,@RequestBody Topic newTopic){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return topicService.createTopic(farmerId,newTopic);
    }

    /**
     * Delete a Topic identified by a give Id
      * @param topicId
     * @return Topic
     * @Exception 400 Topic not found
     */
    @GetMapping("/farmer/DeleteTopic/{topicId}")
    Response deleteTopic(@PathVariable Long topicId){
        return topicService.deleteTopic(topicId);
    }

}