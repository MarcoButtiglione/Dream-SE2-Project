package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.SoilData;
import com.se2project.dream.service.FarmService;
import com.se2project.dream.service.LoginService;
import com.se2project.dream.service.SoilDataService;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
@RequestMapping("/api")
@CrossOrigin
@RestController
public class SoilDataController {
    private final SoilDataService soilDataService;
    private final LoginService loginService;
    private final FarmService farmService;

    public SoilDataController(SoilDataService soilDataService, LoginService loginService, FarmService farmService) {
        this.soilDataService = soilDataService;
        this.loginService = loginService;
        this.farmService = farmService;
    }

    /**
     * Get information about the SoilData of a Farm.
     * @param tokenF the personal token generated from the login of the farmer-
     * From the token is extrapolated the id with the function @see getIdFromTokenA() in LoginService
     * the id is sent to the function @see getFarmId in farmService that returns the Farm information
     * @return SoilData
     */
    @GetMapping("/farmer/GetSoilDataOfFarm/")
    Response getSoilDataOfFarm(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        Long farmId = farmService.getFarmId(farmerId);
        return soilDataService.getSoilDataOfFarm(farmId);
    }

    /**
     * Get information about the humidity.
      * @return int (humidity)
     */
    @GetMapping("/farmer/GetHumidity/")
    int getHumidity(){
        Random rand = new Random();
        int humidity = rand.nextInt((100 - 1) + 1) + 1;
        return humidity;
    }

    /**
     * Get information about the temperature.
     * @return int (temperature)
     */
    @GetMapping("/farmer/GetTemperature/")
    int getTemperature(){
        Random rand = new Random();
        int temperature = rand.nextInt((25 - 10) + 1) + 10;
        return temperature;
    }

    /**
     * Get information about the waterConsumption.
     * @return float (waterConsumption)
     */    @GetMapping("/farmer/GetWaterConsumption/")
    float getWaterConsumption(){
        Random rand = new Random();
        int waterConsuption = rand.nextInt((10000 - 500) + 1) + 500;
        return waterConsuption;
    }

    /**
     * Get information about the SoilData.
     * @param id of the Farm
     * @return soilData
     */    @GetMapping("/agronomist/GetSoilData/{id}")
    Response getSoilData(@PathVariable Long id){
        return soilDataService.getSoilData(id);
    }

    /**
     * Creates a new instance of SoilData
      * @param farmerId
     * @param newSoilData
     * @return Response (SoilData)
     */
    @PostMapping("/agronomist/PostSoilData/{farmerId}")
    Response createSoilData(@PathVariable Long farmerId, @RequestBody SoilData newSoilData){
        Long farmId = farmService.getFarmId(farmerId);
        return soilDataService.createSoilData(farmId,newSoilData);
    }

    /**
     * Updates an instance of SoilData
      * @param farmerId
     * @param soilDataId
     * @param soilDataDto
     * @return Response (SoilData)
     */
    @PostMapping("/agronomist/PutSoilData/{farmerId}")
    Response updateSoilData(@PathVariable Long farmerId, @RequestParam Long soilDataId, @RequestBody SoilData soilDataDto){
        Long farmId = farmService.getFarmId(farmerId);
       return soilDataService.updateSoilData(farmId,soilDataId,soilDataDto);
    }

    /**
     * Delete an instance of SoilData.
     * @return Response (SoilData))
     */    @GetMapping("/agronomist/DeleteSoilData/{id}")
    Response deleteSoilData(@PathVariable Long id){
        return soilDataService.deleteSoilData(id);
    }

}