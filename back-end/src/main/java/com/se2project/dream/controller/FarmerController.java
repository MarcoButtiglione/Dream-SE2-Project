package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Farmer;
import com.se2project.dream.service.FarmerService;

import com.se2project.dream.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static java.util.Arrays.stream;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")

public class FarmerController {


    private final FarmerService farmerService;
    private final LoginService loginService;

    public FarmerController(FarmerService farmerService, LoginService loginService) {
        this.farmerService = farmerService;
        this.loginService = loginService;
    }

    /**
     * Get information about the logged Farmer
     * @param tokenF the personal token generated from the login of the farmer
     *  from the token is extrapolated the id with the function @see getIdFromTokenA() in LoginService
     * the id is sent to the function @see getFarmer in farmerService that return the Farmer information
     * @return Farmer
     * @exception 404 Farmer Not Found
     * */
    @GetMapping("/farmer/GetFarmerF")
    Response getFarmerF(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return farmerService.getFarmer(farmerId);
    }

    /**
     * Get information about the Farmer given his id used by the Agronomist
     * @param id of the farmer
     * the id is sent to the function @see getFarmer in farmerService that return the Farmer information
     * @return Farmer
     * @exception 404 Farmer Not Found
     * */
    @GetMapping("/agronomist/GetFarmerA/{id}")
    Response getFarmerA(@PathVariable Long id){
        return farmerService.getFarmer(id);
    }

    /**
     * Get information about the Farmer given his aadhaar used by the Agronomist
     * @param aadhaar of the farmer
     * the aadhaar is sent to the function @see getFarmerByAadhaar in farmerService that return the Farmer information
     * @return Farmer
     * @exception 404 Farmer Not Found
     * */
    @GetMapping("/agronomist/GetFarmerByAadhaar/{aadhaar}")
    Response getFarmerByAadhaar(@PathVariable String aadhaar){
        return farmerService.getFarmerByAadhaar(aadhaar);
    }

    /**
     * Get the set of farmer that the logged Agronomist is responsible for and a boolean value isAllert that define if
     * the agronomist already made at least two meetings, function used by the Agronomist
     * @param tokenA the personal token generated from the login of the agronomist
     * from the token is extrapolated the id with the function @see getIdFromTokenA() in LoginService
     * the id is sent to the function @see getFarmerByAgronomist in FarmService that return the set of Farmer + isAllert
     * @return Farmer + isAllert
     * @exception 404 Farmers Not Found
     * */
    @GetMapping("/agronomist/GetFarmerByAgronomist")
    Response getFarmerByAgronomist(@RequestHeader("Authorization") String tokenA){
        Long agronomistId=loginService.getIdFromTokenA(tokenA);
        return farmerService.getFarmerByAgronomist(agronomistId);
    }


    /**
     * Function that allow the logged farmer to change his information
     * @param tokenF farmer authentication token generated on login
     * @param farmerDto farmer new value
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function updatefarmer @see FarmerService it update the farmer
     * @return updatedFarm
     * @exception 404 Farmer Not Found
     * @exception 400 Email already used
     * @exception 404 Aadhaar already signed up
     * */
    @PostMapping("/farmer/PutFarmer")
    Response updateFarmer(@RequestHeader("Authorization") String tokenF,@RequestBody Farmer farmerDto){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return farmerService.updatefarmer(farmerId, farmerDto);
    }

    /**
     * Function that allow a new farmer to Sign Up
     * @param newFarmer farmer information
     * the function signUpFarmer @see FarmerService it create the farmer
     * @return newFarmer
     * @exception 400 Fill all fields
     * @exception 400 The farmer is already present
     * @exception 404 Email already used
     * */
    @PostMapping("/signUpFarmer")
    Response signUpFarmer(@RequestBody Farmer newFarmer){
        return farmerService.createFarmer(newFarmer);
    }

}
