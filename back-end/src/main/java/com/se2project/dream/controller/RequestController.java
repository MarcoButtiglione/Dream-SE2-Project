package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Request;
import com.se2project.dream.service.LoginService;
import com.se2project.dream.service.RequestService;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/api")

public class RequestController {
    private final RequestService requestService;
    private final LoginService loginService;

    public RequestController(RequestService requestService, LoginService loginService) {
        this.requestService = requestService;
        this.loginService = loginService;
    }

    /**
     * Get information about the Request given his id, used by the Agronomist
     * @param requestId of the request
     * the id is sent to the function getRequestById @see requestService that return the Request information
     * @return Request
     * @exception 404 Request Not Found
     * */
    @GetMapping("/agronomist/GetRequestById/{requestId}")
    Response getRequestByIdA(@PathVariable Long requestId){
        return requestService.getRequestById(requestId);
    }

    /**
     * Get information about the Request given his id, used by the Farmer
     * @param requestId of the request
     * the id is sent to the function getRequestById @see requestService that return the Request information
     * @return Request
     * @exception 404 Request Not Found
     * */
    @GetMapping("/farmer/GetRequestById/{requestId}")
    Response getRequestByIdF(@PathVariable Long requestId){
        return requestService.getRequestById(requestId);
    }

    /**
     * Get all the Request of an Agronomist with a pendant status
     * @param tokenA agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * the id is sent to the function getByAgronomistPendant @see requestService that return the Requests
     * @return set Requests
     * @exception 404 Agronomist Not Found
     * @exception 404 No Request Found
     * */
    @GetMapping("/agronomist/GetRequestByAgronomistPendant/")
    Response getByAgronomistPendant(@RequestHeader("Authorization") String tokenA){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return requestService.getByAgronomistPendant(agronomistId);
    }

    /**
     * Get all the Request of an Agronomist that have a response but don't have yet a feedback by the farmer
     * @param tokenA agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * the id is sent to the function getByAgronomistNoFeed @see requestService that return the Requests
     * @return set Requests
     * @exception 404 Agronomist Not Found
     * @exception 404 No Request Found
     * */
    @GetMapping("/agronomist/GetRequestByAgronomistNoFeed/")
    Response getByAgronomistNoFeed(@RequestHeader("Authorization") String tokenA){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return requestService.getByAgronomistNoFeed(agronomistId);
    }

    /**
     * Get all the Request of an Agronomist that have a response and a feedback by the farmer
     * @param tokenA agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * the id is sent to the function getByAgronomistClosed @see requestService that return the Requests
     * @return set Requests
     * @exception 404 Agronomist Not Found
     * @exception 404 No Request Found
     * */
    @GetMapping("/agronomist/GetRequestByAgronomistClosed/")
    Response getByAgronomistClosed(@RequestHeader("Authorization") String tokenA){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return requestService.getByAgronomistClosed(agronomistId);
    }

    /**
     * Get all the Request of a Farmer with a pendant status
     * @param tokenF farmer authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * the id is sent to the function getByFarmerPendant @see requestService that return the Requests
     * @return set Requests
     * @exception 404 Farmer Not Found
     * @exception 404 No Request Found
     * */
    @GetMapping("/farmer/GetRequestByFarmerPendant/")
    Response getByFarmerPendant(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return requestService.getByFarmerPendant(farmerId);
    }

    /**
     * Get all the Request of a Farmer that have a response but don't have yet a feedback by the farmer
     * @param tokenF farmer authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * the id is sent to the function getByFarmerNoFeed @see requestService that return the Requests
     * @return set Requests
     * @exception 404 Farmer Not Found
     * @exception 404 No Request Found
     * */
    @GetMapping("/farmer/GetRequestByFarmerNoFeed/")
    Response getByFarmerNoFeed(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return requestService.getByFarmerNoFeed(farmerId);
    }

    /**
     * Get all the Request of a Farmer that have a response and a feedback by the farmer
     * @param tokenF agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * the id is sent to the function getByFarmerClosed @see requestService that return the Requests
     * @return set Requests
     * @exception 404 Farmer Not Found
     * @exception 404 No Request Found
     * */
    @GetMapping("/farmer/GetRequestByFarmerClosed/")
    Response getByFarmerClosed(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return requestService.getByFarmerClosed(farmerId);
    }

    /**
     * function that allow the logged farmer to create a new request
     * @param tokenF agronomist authentication token generated on login
     * @param newRequest request to insert
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * the function createRequest @see requestService create the new Request
     * @return  newRequests
     * @exception 404 Farmer Not Found
     * @exception 400 We're sorry but at the moment we don't have an agronomist in your location.
     * */
    @PostMapping("/farmer/PostRequest/")
    Response createRequest(@RequestHeader("Authorization") String tokenF,@RequestBody Request newRequest){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return requestService.createRequest(farmerId,newRequest);
    }

    /**
            * function that allow the logged agronomist to response a request
     * @param tokenA agronomist authentication token generated on login
     * @param requestId request to response
     * @param response response to put
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * the function RespondRequest @see requestService update the Request inserting the response
     * @return  Request
     * @exception 404 Request Not Found
     * @exception 404 Agronomist Not Found
     * @exception 400 You've already responded to this request
     * @exception 400 You cannot responde to this request is not your job
     * */
    @PostMapping("/agronomist/PutResponse/{requestId}")
    Response RespondRequest(@RequestHeader("Authorization") String tokenA,@PathVariable Long requestId, @RequestBody Request response){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return requestService.respondRequest(requestId,agronomistId,response);
    }

    /**
     * function that allow the logged farmer to give a feedback to a response
     * @param tokenF farmer authentication token generated on login
     * @param requestId request to feed
     * @param feed feedback to put
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * the function feedRequest @see requestService update the Request inserting the feedback
     * @return  Request
     * @exception 404 Request Not Found
     * @exception 404 Farmer Not Found
     * @exception 400 You've already give a feedback to this response
     * @exception 400 You cannot give a feedback to this response is not your job
     * */
    @PostMapping("/farmer/PutFeedBack/{requestId}")
    Response FeedRequest(@RequestHeader("Authorization") String tokenF,@PathVariable Long requestId, @RequestBody Request feed){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return requestService.feedRequest(requestId,farmerId,feed);
    }

    /**
     * function that allow the logged agronomist to give a feedback to a response
     * @param tokenF agronomist authentication token generated on login
     * @param requestId request to feed
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * the function deleteRequest @see requestService delete the Request
     * @return  Request
     * @exception 404 Request Not Found
     * @exception 400 You cannot delete this request
     * */
    @GetMapping("/farmer/DeleteRequest/{requestId}")
    Response deleteRequest(@PathVariable Long requestId,@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return requestService.deleteRequest(requestId, farmerId);
    }
}