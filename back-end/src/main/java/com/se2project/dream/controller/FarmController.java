package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Farm;
import com.se2project.dream.service.FarmService;
import com.se2project.dream.service.LoginService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")

public class FarmController {
    private final FarmService farmService;
    private final LoginService loginService;

    public FarmController(FarmService farmService, LoginService loginService) {
        this.farmService = farmService;
        this.loginService = loginService;
    }

    /**
     * Get information about the Farm of a farmer given his id used by the Agronomist
     * @param farmerId id of the farmer
     * the id is sent to the function @see getFarm in farmService that return the Farm information
     * @return Farm
     * @exception 404 Farmer Not Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/agronomist/getFarmA/{farmerId}")
    Response getFarmA(@PathVariable Long farmerId){
        return farmService.getFarm(farmerId);
    }

    /**
     * Get information about the farm of the logged Farmer
     * @param tokenF the personal token generated from the login of the farmer
     * from the token is extrapolated the id with the function @see getIdFromTokenF() in LoginService
     * the id is sent to the function @see getFarm in FarmService that return the Farm information
     * @return Farm
     * @exception 404 Farmer Not Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/farmer/getFarmF")
    Response getFarmF(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return farmService.getFarm(farmerId);
    }

    /**
     * Get the set of farm of a location used by the Agronomist to see the farmer in his area
     * @param locationId of a location
     * the id is sent to the function @see getAllFarmByLocation in FarmService that return the set of Farms
     * @return set<Farm>
     * @exception 404 Location Not Found
     * @exception 404 No Farm Found
     * */
    @GetMapping("/agronomist/getAllFarmByLocation/{locationId}")
    Response getAllFarmByLocation(@PathVariable Long locationId){
        return farmService.getAllFarmByLocation(locationId);
    }

    /**
     * Function that allow the logged farmer to change his farm information
     * @param tokenF farmer authentication token generated on login
     * @param locationId of the location the farmer want to update the farm
     * @param farmTdo farm new value
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function updateFarm @see FarmService it update the farm
     * @return updatedFarm
     * @exception 404 Farm Not Found
     * @exception 404 Farmer Not Found
     * @exception 404 Location Not Found
     * */
    @PostMapping("/farmer/PutFarm/{locationId}")
    Response updateFarm(@PathVariable Long locationId, @RequestHeader("Authorization") String tokenF,@RequestBody Farm farmTdo){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        Long farmId =farmService.getFarmId(farmerId);
        return farmService.updateFarm(farmId,farmerId,locationId,farmTdo);
    }

    /**
     * Function that allow the logged farmer to insert a farm
     * @param tokenF farmer authentication token generated on login
     * @param locationId of the location that the farmer want to set his farm
     * @param newFarm farm to insert
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function createFarm @see FarmService create the farm of the farmer
     * @return newFarm
     * @exception 404 Location Not Found
     * @exception 404 Farmer Not Found
     * @exception 400 You've already inserted your farm
     * */
    @PostMapping("/farmer/PostFarm/{locationId}")
    Response createFarm(@RequestHeader("Authorization") String tokenF,@PathVariable Long locationId,@RequestBody Farm newFarm){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return farmService.createFarm(farmerId, locationId, newFarm);
    }
}