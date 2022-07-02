package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Location;
import com.se2project.dream.service.LocationService;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api")
@CrossOrigin
@RestController
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * Get all location where a Farmer may have his Farm
     * the function @see getAllLocation in LocationService return all the Location
     * @return set<Location>
     * @exception 404 No Location Found
     * */
    @GetMapping("/farmer/GetAllLocation/")
    Response getAllLocation(){ return locationService.getAllLocation(); }

    /**
     * Get a location by its id
     * @param id of a Location
     * the function @see getLocation in LocationService return the Location with the same id
     * @return Location
     * @exception 404 Location Not Found
     * */
    @GetMapping("/farmer/GetLocation/{id}")
    Response getLocation(@PathVariable Long id){
        return locationService.getLocation(id);
    }

    /**
     * DEVELOPER FUNCTION
     * Function that allow to create a new location that is used from the developer to test the correct functionality of the system
     * @param newLocation the location data to insert
     * the data are sent to the function @see createLocation in LocationService that crete the location
     * @return newLocation
     * @exception 400 Location already existing
     */
    @PostMapping("/dev/PostLocation/")
    Response createLocation(@RequestBody Location newLocation){
        return locationService.createLocation(newLocation);
    }


}
