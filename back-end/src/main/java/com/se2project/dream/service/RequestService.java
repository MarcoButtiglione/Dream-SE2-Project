package com.se2project.dream.service;

import com.se2project.dream.entity.*;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.repository.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final AgronomistRepository agronomistRepository;
    private final FarmerRepository farmerRepository;
    private final FarmRepository farmRepository;
    private final NotificationAgronomistRepository notificationAgronomistRepository;
    private final NotificationFarmerRepository notificationFarmerRepository;
    public RequestService(RequestRepository requestRepository, AgronomistRepository agronomistRepository, FarmerRepository farmerRepository, FarmRepository farmRepository, NotificationAgronomistRepository notificationAgronomistRepository, NotificationFarmerRepository notificationFarmerRepository) {
        this.requestRepository = requestRepository;
        this.agronomistRepository = agronomistRepository;
        this.farmerRepository = farmerRepository;
        this.farmRepository = farmRepository;
        this.notificationAgronomistRepository = notificationAgronomistRepository;
        this.notificationFarmerRepository = notificationFarmerRepository;
    }

    public Response getRequestById(Long requestId){
        Response response=new Response();
        Request request=requestRepository.findById(requestId).orElse(null);
        if(request==null){
            response.setCode(404);
            response.setMessage("Request Not Found");
        }
        else{
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(request));
        }
        return response;
    }

    public Response getByAgronomistPendant(Long agronomistId){
        Response response=new Response();
        Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Iterable<Request> requests=requestRepository.findAllByAgronomistP(agronomistId);
            if(requests.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Request Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(requests));
            }
        }
        return response;
    }

    public Response getByAgronomistNoFeed(Long agronomistId) {
        Response response = new Response();
        Agronomist agronomist = agronomistRepository.findById(agronomistId).orElse(null);
        if (agronomist == null) {
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        } else {
            Iterable<Request> requests = requestRepository.findAllByAgronomistNF(agronomistId);
            if (requests.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No Request Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(requests));
            }

        }
        return response;
    }

    public Response getByAgronomistClosed(Long agronomistId) {
        Response response = new Response();
        Agronomist agronomist = agronomistRepository.findById(agronomistId).orElse(null);
        if (agronomist == null) {
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        } else {
            Iterable<Request> requests = requestRepository.findAllByAgronomistC(agronomistId);
            if (requests.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No Request Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(requests));
            }

        }
        return response;
    }

    public Response getByFarmerPendant(Long farmerId){
        Response response=new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else{
            Iterable<Request> requests=requestRepository.findAllByFarmerP(farmerId);
            if(requests.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Request Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(requests));
            }
        }
        return response;
    }

    public Response getByFarmerNoFeed(Long farmerId) {
        Response response = new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if (farmer == null) {
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        } else {
            Iterable<Request> requests = requestRepository.findAllByFarmerNF(farmerId);
            if (requests.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No Request Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(requests));
            }

        }
        return response;
    }

    public Response getByFarmerClosed(Long farmerId) {
        Response response = new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if (farmer == null) {
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        } else {
            Iterable<Request> requests = requestRepository.findAllByFarmerC(farmerId);
            if (requests.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No Request Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(requests));
            }

        }
        return response;
    }

    public Response createRequest(Long farmerId, Request newRequest){
        Response response=new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else{
            Farm farm= farmRepository.findFarm(farmerId);
            Agronomist agronomist=agronomistRepository.findByLocation(farm.getLocation());
            if(agronomist==null){
                response.setCode(404);
                response.setMessage("We're sorry but at the moment we don't have an agronomist in your location.");
            }
            else{
                newRequest.setStatus("pendant");
                newRequest.setAgronomist(agronomist);
                newRequest.setFarmer(farmer);
                newRequest.setDate();
                requestRepository.save(newRequest);
                String desc= "The farmer Mr/Ms. "+farmer.getLastName()+" just sent you a new request of help: "+newRequest.getSubject();
                NotificationAgronomist notificationAgronomist =new NotificationAgronomist("NEW_REQUEST",desc,agronomist,null,null);
                notificationAgronomistRepository.save(notificationAgronomist);
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(newRequest));
            }
        }
        return response;
    }

    public Response respondRequest(Long requestId, Long agronomistId, Request Response){
        Response response=new Response();
        Request request=requestRepository.findById(requestId).orElse(null);
        if(request==null){
            response.setCode(404);
            response.setMessage("Request Not Found");
        }
        else {
            if(agronomistId==request.getAgronomist()){
                Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
                if(agronomist==null){
                    response.setCode(404);
                    response.setMessage("Agronomist Not Found");
                }
                else {
                    if(request.getResponse()==null){
                        request.setResponse(Response.getResponse());
                        request.setStatus("noFeed");
                        requestRepository.save(request);
                        Farmer farmer=farmerRepository.findById(request.getFarmer()).orElse(null);
                        String desc= "The local agronomist Mr/Ms. "+agronomist.getLastName()+" just sent you answered yor request for help";
                        NotificationFarmer notification= new NotificationFarmer("NEW_RESPONSE",desc,farmer,null);
                        notificationFarmerRepository.save(notification);
                        response.setCode(200);
                        response.setMessage("success");
                        response.setResults(Collections.singleton(request));
                    }
                    else{
                        response.setCode(400);
                        response.setMessage("You've already responded to this request");
                    }
                }
            }
            else{
                response.setCode(400);
                response.setMessage("You cannot responde to this request is not your job");
            }
        }
        return response;
    }

    public Response feedRequest(Long requestId, Long farmerId, Request feed){
        Response response=new Response();
        Request request=requestRepository.findById(requestId).orElse(null);
        if(request==null){
            response.setCode(404);
            response.setMessage("Request Not Found");
        }
        else {
            if(farmerId==request.getFarmer()){
                Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
                if(farmer==null){
                    response.setCode(404);
                    response.setMessage("Farmer Not Found");
                }
                else {
                    if(request.getFeedback()==null){
                        request.setFeedback(feed.getFeedback());
                        request.setStatus("closed");
                        requestRepository.save(request);
                        Agronomist agronomist=agronomistRepository.findById(request.getAgronomist()).orElse(null);
                        String desc= "The farmer Mr/Ms. "+farmer.getLastName()+" just  gave you a feedback about your response";
                        NotificationAgronomist notificationAgronomist =new NotificationAgronomist("FEED_REQUEST",desc,agronomist,null,null);
                        notificationAgronomistRepository.save(notificationAgronomist);
                        response.setCode(200);
                        response.setMessage("success");
                        response.setResults(Collections.singleton(request));
                    }
                    else{
                        response.setCode(400);
                        response.setMessage("You've already give a feedback to this response");
                    }
                }
            }
            else{
                response.setCode(400);
                response.setMessage("You cannot give a feedback to this response is not your job");
            }
        }
        return response;
    }

    public Response deleteRequest(Long requestId, Long farmerId){
        Response response=new Response();
        Request request= requestRepository.findById(requestId).orElse(null);
        if(request==null){
            response.setCode(404);
            response.setMessage("Request Not Found");
        }
        else{
            if(request.getFarmer()==farmerId) {
                requestRepository.delete(request);
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(request));
            }
            else{
                response.setCode(400);
                response.setMessage("you cannot delete this request");
            }
        }
        return response;
    }


}
