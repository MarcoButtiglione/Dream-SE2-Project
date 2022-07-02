package com.se2project.dream.service;

import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.NotificationAgronomist;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.repository.AgronomistRepository;
import com.se2project.dream.repository.NotificationAgronomistRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class NotificationAgronomistService {
    private final NotificationAgronomistRepository notificationAgronomistRepository;
    private final AgronomistRepository agronomistRepository;
    public NotificationAgronomistService( NotificationAgronomistRepository notificationAgronomistRepository, AgronomistRepository agronomistRepository) {
        this.notificationAgronomistRepository = notificationAgronomistRepository;
        this.agronomistRepository = agronomistRepository;
    }

    /**
     * Method to retrieve all Notification given the agronomistId
      * @param agronomistId
     * @exception 404 Agronomist not found
     * @exception 404 No notification found
     * @return Notification
     */
    public Response getAllNotification(Long agronomistId){
        Response response=new Response();
        Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(404);
            response.setMessage("Agronomist not found");
        }
        else {
            Iterable<NotificationAgronomist> notifications = notificationAgronomistRepository.findAllNotification(agronomistId);
            if(notifications.toString().equals("[]")){
                response.setCode(404);
                response.setMessage(" No notification found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(notifications));
            }
        }
        return response;
    }

    /**
     * Method to delete a given Notification
      * @param id
     * @param agronomistId
     * @exception 404 Notification not found
     * @exception 404 Agronomist not found
     * @return
     */
    public Response deleteNotification(Long id, Long agronomistId){
        Response response=new Response();
        NotificationAgronomist not= notificationAgronomistRepository.findById(id).orElse(null);
        if(not==null){
            response.setCode(404);
            response.setMessage("notification not found");
        }
        else {
            Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
            if(agronomist==null){
                response.setCode(404);
                response.setMessage("Agronomist not found");
            }
            else {
                if(not.getAgronomist()==agronomistId){
                    notificationAgronomistRepository.delete(not);
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(not));
                }
                else {
                    response.setCode(404);
                    response.setMessage("You cannot delete this notification");
                }

            }
        }
        return response;
    }

}
