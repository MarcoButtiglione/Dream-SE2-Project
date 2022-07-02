package com.se2project.dream.service;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Location;
import com.se2project.dream.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Method to retrieve all locations
     * @exception 404 No Location Found
      * @return Location
     */
    public Response getAllLocation() {
        Response response = new Response();
        Iterable<Location> locations = locationRepository.findAll();
        if (locations.toString() == "[]") {
            response.setCode(404);
            response.setMessage("No Location Found");
        }
        else {
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(locations));
        }
        return response;
    }

    /**
     * Method to get a Location given the Id
      * @param id
     * @exception 404 Location Not Found
     * @return Location
     */
    public Response getLocation(Long id) {
        Response response = new Response();
        Location location = locationRepository.findById(id).orElse(null);
        if (location == null) {
            response.setCode(404);
            response.setMessage("Location Not Found");
        }
        else {
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(location));
        }
        return response;
    }


    /**
     * Method to create a new Location
      * @param newLocation
     * @exception 404 Location already exists
     * @return Location
     */
    public Response createLocation(Location newLocation) {
        Response response = new Response();
        Location location = locationRepository.findById(newLocation.getLocationId()).orElse(null);
        if (location != null) {
            response.setCode(404);
            response.setMessage("Location already existing");
        }
        else {
            locationRepository.save(newLocation);
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(newLocation));
        }
        return response;
    }

}
