package com.se2project.dream.service;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.*;
import com.se2project.dream.repository.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class FarmService {

    private final FarmRepository farmRepository;
    private  final FarmerRepository farmerRepository;
    private final LocationRepository locationRepository;
    private final AgronomistRepository agronomistRepository;
    private final NotificationAgronomistRepository notificationAgronomistRepository;
    public FarmService(FarmRepository farmRepository, FarmerRepository farmerRepository, LocationRepository locationRepository, AgronomistRepository agronomistRepository, NotificationAgronomistRepository notificationAgronomistRepository) {
        this.farmRepository = farmRepository;
        this.farmerRepository = farmerRepository;
        this.locationRepository = locationRepository;
        this.agronomistRepository = agronomistRepository;
        this.notificationAgronomistRepository = notificationAgronomistRepository;
    }

    /**
     * Get id of the Farm given his farmerid
     * @param farmerId
     * first is called findById on farmerRepository
     * then with funtion findFarm @see farmRepository
     * @return farmId
     * */
    public Long getFarmId(Long farmerId){
        Farmer farmer= farmerRepository.findById(farmerId).orElse(null);
        Farm farm = farmRepository.findFarm(farmerId);
        return farm.getFarmId();
    }

    /**
     * Get the Farm given his farmerid
     * @param farmerId
     * first is called findById on farmerRepository @exception 404 Farmer Not Found
     * then with funtion findFarm @see farmRepository @exception 404 No Farm Found
     * @return farm
     * */
    public Response getFarm(Long farmerId){
        Response response=new Response();
        Farmer farmer= farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else {
            Farm farm = farmRepository.findFarm(farmerId);
            System.err.println(farm);
            if (farm==null){
                response.setCode(404);
                response.setMessage("No Farm Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(farm));
            }
        }
        return response;
    }

    /**
     * Get the Farm given his locationId
     * @param locationId
     * first is called findById on locationRepository @exception 404 Location Not Found
     * then with function findAllFarmByLocationFarm @see farmRepository
     * @return farms with that location
     * @exception 404 No Farm Found
     * */
    public Response getAllFarmByLocation(Long locationId){
        Response response=new Response();
        Location location = locationRepository.findById(locationId).orElse(null);
        if(location==null){
            response.setCode(404);
            response.setMessage("Location Not Found");
        }
        else{
            Iterable<Farm> farms = farmRepository.findAllFarmByLocation(locationId);
            if(farms.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Farm Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(farms));
            }
        }

        return response;
    }

    /**
     * create a new Farm
     * @param farmerId of the owner
     * @param locationId of the location of the farm
     * @param newFarm the new farmer information
     * first is checked if the farmer exist findById @exception 404 Farmer Not Found
     * then is checked if the location exist findById @exception 404 Location Not Found
     * then is checked if the farm is already beem registred with findById 400 You've already inserted your farm
     * then the new farm is saved in the db and a notification is saved in the db for the local agronomist
     * @return newFarmer
     */
    public Response createFarm(Long farmerId, Long locationId, Farm newFarm){
        Response response=new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(400);
            response.setMessage("Farmer Not Found");
        }
        else{
            Location location = locationRepository.findById(locationId).orElse(null);
            if(location==null){
                response.setCode(404);
                response.setMessage("Location Not Found");
            }
            else {
                Farm checkFarm= farmRepository.findFarm(farmerId);
               if(checkFarm!=null){
                   response.setCode(404);
                   response.setMessage("You've already inserted your farm");
               }
               else {
                   newFarm.setFarmer(farmer);
                   newFarm.setLocation(location);
                   farmRepository.save(newFarm);
                   Agronomist agronomist= agronomistRepository.findByLocation(locationId);
                   if(agronomist!=null) {
                       String desc= "A new Farmer sign up in your location";
                       NotificationAgronomist notification = new NotificationAgronomist("NEW_FARMER", desc,agronomist,null,farmer);
                       notificationAgronomistRepository.save(notification);
                   }
                   response.setCode(200);
                   response.setMessage("success");
                   response.setResults(Collections.singleton(newFarm));
               }
            }

        }
        return response;
    }

    /**
     * update a Farm
     * @param farmId farm to update
     * @param farmerId of the owner
     * @param locationId of the location of the farm
     * @param farmTdo the farmer information to update
     * first is checked if the farm exist findById @exception 404 Farm Not Found
     * first is checked if the farmer exist findById @exception 404 Farmer Not Found
     * then is checked if the location exist findById @exception 404 Location Not Found
     * then the farm is updated and saved in the db
     * @return newFarmer
     */
    public Response updateFarm(Long farmId, Long farmerId, Long locationId ,Farm farmTdo){
        Response response=new Response();
        Farm farmToUpdate=farmRepository.findById(farmId).orElse(null);
        if(farmToUpdate==null){
            response.setCode(400);
            response.setMessage("Farm Not Found");
        }
        else{
            Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(400);
                response.setMessage("Farmer Not Found");
            }
            else{
                Location location = locationRepository.findById(locationId).orElse(null);
                if(location==null){
                    response.setCode(404);
                    response.setMessage("Location Not Found");
                }
                else {
                    farmToUpdate.setFarmer(farmer);
                    farmToUpdate.setLocation(location);
                    farmToUpdate.setSquareKm(farmTdo.getSquareKm());
                    farmToUpdate.setAddress(farmTdo.getAddress());
                    farmRepository.save(farmToUpdate);
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(farmToUpdate));
                }

            }
        }

        return response;
    }
}
