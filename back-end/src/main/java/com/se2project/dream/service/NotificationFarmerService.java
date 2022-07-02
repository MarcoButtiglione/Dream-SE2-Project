package com.se2project.dream.service;

import com.se2project.dream.entity.Farmer;
import com.se2project.dream.entity.NotificationFarmer;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.repository.FarmerRepository;
import com.se2project.dream.repository.NotificationFarmerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class NotificationFarmerService {
    private final NotificationFarmerRepository notificationFarmerRepository;
    private final FarmerRepository farmerRepository;

    public NotificationFarmerService(NotificationFarmerRepository notificationFarmerRepository, FarmerRepository farmerRepository) {
        this.notificationFarmerRepository = notificationFarmerRepository;
        this.farmerRepository = farmerRepository;
    }

    /**
     * Method to retrieve all Notification given the farmerId
      * @param farmerId
     * @exception 404 Farmer not Found
     * @exception 404 No notification found
     * @return Notification
     */
    public Response getAllNotification(Long farmerId){
        Response response=new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(404);
            response.setMessage("Farmer not found");
        }
        else {
            Iterable<NotificationFarmer> notifications = notificationFarmerRepository.findAllNotification(farmerId);
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
     * @param farmerId
     * @exception 404 notification not found
     * @exception 404 Farmer not found
     * @exception 400 You cannot delete this Notification
     * @return
     */
    public Response deleteNotification(Long id, Long farmerId){
        Response response=new Response();
        NotificationFarmer not= notificationFarmerRepository.findById(id).orElse(null);
        if(not==null){
            response.setCode(404);
            response.setMessage("notification not found");
        }
        else {
            Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(404);
                response.setMessage("Farmer not found");
            }
            else {
                if(farmerId== not.getFarmer()) {
                    notificationFarmerRepository.delete(not);
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(not));
                }else{
                    response.setCode(400);
                    response.setMessage("You cannot delete this notification");
                }
            }
        }
        return response;
    }
}
