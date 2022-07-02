package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.service.AgronomistService;
import com.se2project.dream.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AgronomistController {

    private final AgronomistService agronomistService;
    private final LoginService loginService;

    public AgronomistController(AgronomistService agronomistService, LoginService loginService) {
        this.agronomistService = agronomistService;
        this.loginService = loginService;
    }

    /**
     * Get information about the logged Agronomist
     * @param tokenA the personal token generated from the login of the agronomist
     * from the token is extrapolated the id with the function @see getIdFromTokenA() in LoginService
     * the id is sent to the function @see getAgronomist in AgronomistService that return the Agronomist information
     * @return Agronomist
     * @exception 404 Agronomist Not Found
     * */
    @GetMapping("/agronomist/GetAgronomist")
    Response getAgronomist(@RequestHeader("Authorization") String tokenA){
        Long agronomistId=loginService.getIdFromTokenA(tokenA);
        return agronomistService.getAgronomist(agronomistId);
    }

    /**
     * Get information about the Agronomist with the same aadhaar as the
     * @param aadhaar the personal fiscal code
     * the aadhaar is sent to the function @see getAgronomistByAadhaar in AgronomistService that return the Agronomist information
     * @return Agronomist
     * @exception 404 Agronomist Not Found
     * */
    @GetMapping("/agronomist/GetAgronomistByAadhaar/{aadhaar}")
    Response getAgronomistByAadhaar(@PathVariable String aadhaar){
        return agronomistService.getAgronomistByAadhaar(aadhaar);
    }

    /**
     * Get information about the Agronomist responsible for the location defined in
     * @param locationId
     * the locationId is sent to the function @see getAgronomistByLocation in AgronomistService that return the Agronomist information
     * @return Agronomist
     * @exception 404 Location Not Found
     * @exception 404 Agronomist Not Found
     * */
    @GetMapping("/agronomist/GetAgronomistByLocation/{locationId}")
    Response getAgronomistByLocation(@PathVariable Long locationId){
        return agronomistService.getAgronomistByLocation(locationId);
    }

    /**
     * DEVELOPER FUNCTION
     * Function that allow to create a new agronomist that is used from the developer to test the correct functionality of the system
     * @param locationId the location the new agronomist will be responsible for
     * @param newAgronomist the agronomist data to insert
     * the locationId and the new Agronomist is sent to the function @see createAgronomist in AgronomistService that return the new Agronomist information
     * @return Agronomist
     * @exception 400 The agronomist is already present
     * @exception 404 Location Not Found
     * @exception 400 Email already used
     * @exception 400 Agronomist for that location already present
     */
    @PostMapping("/dev/signUpAgronomist/{locationId}")
    Response createAgronomist(@PathVariable Long locationId, @RequestBody Agronomist newAgronomist){
        return agronomistService.createAgronomist(locationId,newAgronomist);
    }

    /**
     * Function that allow to update the information of an agronomist
     * @param locationId the location the new agronomist will be responsible for
     * @param agronomistDto the agronomist data to update
     * @param tokenA the personal token generated from the login of the agronomist
     * the locationId and the new Agronomist is sent to the function @see updateAgronomist in AgronomistService that return the updated Agronomist information
     * @return Agronomist
     * @exception 404 Agronomist Not Found
     * @exception 400 Aadhaar already signed up
     * @exception 404 Location Not Found
     * @exception 400 Email already used
     * @exception 400 Agronomist for that location already present
     */
    @PostMapping("/agronomist/PutAgronomist/{locationId}")
    Response updateAgronomist(@RequestHeader("Authorization") String tokenA, @PathVariable Long locationId, @RequestBody Agronomist agronomistDto){
        Long agronomistId=loginService.getIdFromTokenA(tokenA);
        return agronomistService.updateAgronomist(agronomistId,locationId,agronomistDto);
    }
}

