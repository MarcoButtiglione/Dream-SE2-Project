package com.se2project.dream.service;

import com.se2project.dream.extraClasses.Response;

import com.se2project.dream.entity.Farmer;
import com.se2project.dream.entity.Topic;
import com.se2project.dream.repository.FarmerRepository;
import com.se2project.dream.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class TopicService {
    private  final TopicRepository topicRepository;
    private final FarmerRepository farmerRepository;

    public TopicService(TopicRepository topicRepository, FarmerRepository farmerRepository) {
        this.topicRepository = topicRepository;
        this.farmerRepository = farmerRepository;
    }

    /**
     * Get information about the Topic given its Id
     * @param id
     * first is checked if the Topic exists in the database using he function findById @see topicRepository
     * if exist @return Topic
     * @exception 404 Topic Not Found
     * */
    public Response getTopicById(Long id){
        Response response=new Response();
        Topic topic=topicRepository.findById(id).orElse(null);
        if(topic==null){
            response.setCode(404);
            response.setMessage("No Topic Found");
        }
        else{
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(topic));
        }
        return response;
    }

    /**
     * Get information about all Topic
     * first is checked if there are Topics in the database using the function findAllTopic
     * if exist @return Topic
     * if not @return Message Error
     * @exception 404 Topics Not Found
     * */
    public Response getAllTopic(){
        Response response=new Response();
        Iterable<Topic> topics=topicRepository.findAllTopic();
        if(topics.toString()=="[]"){
            response.setCode(404);
            response.setMessage("No Topic Found");
        }
        else{
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(topics));
        }
        return response;
    }

    /**
     * Get information about the Topic given the Farmer's Id
     * @param farmerId
     * first is checked if the Topic exists in the database using the function findById
     * if exist @return Topic
     * if not @return Message Error
     * @exception 404 Topic Not Found
     * @exception 400 Farmer Not Found
     * */    public Response getAllTopicByFarmer(Long farmerId){
        Response response=new Response();
        Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(400);
            response.setMessage("Farmer Not Found");
        }
        else{
            Iterable<Topic> topics = topicRepository.findByFarmer(farmerId);
            if(topics.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Topics Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(topics));
            }
        }

        return response;
    }

    /**
     * Get information about the Topics given their tag
     * @param farmerId
     * @param tag
     * first is checked if the farmer exists in the database using the function findById
     * Then the topics with the given tag are searched with the function findByTag
     * if exist @return Topic
     * if not @return Message Error
     * @exception 400 Farmer Not Found
     * @exception 404 No topics found
     * */
    public Response getAllTopicByTag(Long farmerId,String tag){
        Response response=new Response();
        Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(400);
            response.setMessage("Farmer Not Found");
        }
        else{
            Iterable<Topic> topics = topicRepository.findByTag(tag,farmerId);
            if(topics.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Topics Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(topics));
            }
        }

        return response;
    }

    /**
     * Get information about the Topics given the word
     * @param word
     * the topics with the given word are searched with the function findByWord
     * if exist @return Topic
     * if not @return Message Error
     * @exception 404 No topics found
     * */
    public Response getAllTopicByWord(String word){
        Response response=new Response();
        Iterable<Topic> topics = topicRepository.findByWord(word);
        if(topics.toString()=="[]"){
            response.setCode(404);
            response.setMessage("No Topics Found");
        }
        else{
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(topics));
        }
        return response;
    }

    /**
     * Get information about the Topics given the word and the farmerId
     * @param word
     * @param farmerId
     * First the Farmer is searched in the database with the function findById
     * if the farmer exists,
     * the topics with the given word are searched with the function findByWord
     * if exist @return Topic
     * if not @return Message Error
     * @exception 400 Farmer Not Found
     * @exception 404 No topics found
     * */
    public Response getAllTopicByWordF(String word, Long farmerId){
        Response response=new Response();
        Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(400);
            response.setMessage("Farmer Not Found");
        }
        else{
            Iterable<Topic> topics = topicRepository.findByWordF(word,farmerId);
            if(topics.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Topic Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(topics));
            }
        }

        return response;
    }

    /**
     * Get information about the Topics given the date
     * @param date
     * the topics with the given date are searched with the function findByDate
     * if exist @return Topic
     * if not @return Message Error
     * @exception 404 No topics found
     * */
    public Response getAllTopicByDate(String date){
        Response response=new Response();
        Iterable<Topic> topics = topicRepository.findByDate(date);
        if(topics.toString()=="[]"){
            response.setCode(404);
            response.setMessage("No Topics Found");
        }
        else{
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(topics));
        }
        return response;
    }

    /**
     * create a new Topic
     * @param farmerId the id of the Farmer that creates the Topic
     * @param newTopic the Topic data to insert
     * first is checked if the farmer exists by function findById @exception 400 Farmer Not Found
     * the new topic is then created
     * @return Topic
     */
    public Response createTopic(Long farmerId, Topic newTopic){
        Response response=new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(400);
            response.setMessage("Agronomist Not Found");
        }
        else{
            newTopic.setFarmer(farmer);
            newTopic.setDate();
            topicRepository.save(newTopic);
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(newTopic));
        }
        return response;
    }

    /**
     * update an Topic
     * @param topicId the topic to update
     * @param farmerId the farmer owning the topic to update
     * @param topicTdo data to update
     * first is get the topic to update with function findById @exception 400 Topic Not Found
     * then is checked if the farmer exist by function findById @exception 400 Farmer Not Found
     * then the Topic is updated
     * @return Topic
     */     public Response updateTopic(Long topicId, Long farmerId, Topic topicTdo){
        Response response=new Response();
        Topic topicToUpdate=topicRepository.findById(topicId).orElse(null);
        if(topicToUpdate==null){
            response.setCode(400);
            response.setMessage("Topic Not Found");
        }
        else{
            Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(400);
                response.setMessage("Farmer Not Found");
            }
            else{
                topicToUpdate.setTopic(topicTdo.getTopic());
                topicToUpdate.setDescription(topicTdo.getDescription());
                topicToUpdate.setTag(topicTdo.getTag());
                topicToUpdate.setFarmer(farmer);
                topicToUpdate.setDate();
                topicRepository.save(topicToUpdate);
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(topicToUpdate));

            }
        }

        return response;
    }

    /**
     * delete a Topic
      * @param topicId Id of the Topic to delete
     *  first is checked if the Topic to delete exists with the function findById
     *  if the Topic is not found @exception 400 Topic Not Found
     *  Otherwise, the Topic is deleted
     * @return Topic
     */
    public Response deleteTopic(Long topicId){
        Response response=new Response();
        Topic topicToDelete=topicRepository.findById(topicId).orElse(null);
        if(topicToDelete==null){
            response.setCode(400);
            response.setMessage("Topic Not Found");
        }
        else{
            topicRepository.delete(topicToDelete);
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(topicToDelete));
        }
        return response;
    }
}
