package com.se2project.dream.service;

import com.se2project.dream.entity.Farmer;
import com.se2project.dream.entity.NotificationFarmer;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Farm;
import com.se2project.dream.entity.SoilData;
import com.se2project.dream.repository.FarmRepository;
import com.se2project.dream.repository.FarmerRepository;
import com.se2project.dream.repository.NotificationFarmerRepository;
import com.se2project.dream.repository.SoilDataRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SoilDataService {

    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;
    private final SoilDataRepository soilDataRepository;
    private final NotificationFarmerRepository notificationFarmerRepository;
    public SoilDataService(FarmRepository farmRepository, FarmerRepository farmerRepository, SoilDataRepository soilDataRepository, NotificationFarmerRepository notificationFarmerRepository) {
        this.farmRepository = farmRepository;
        this.farmerRepository = farmerRepository;
        this.soilDataRepository = soilDataRepository;
        this.notificationFarmerRepository = notificationFarmerRepository;
    }

    /**
     * Get information about the SoilData of a Farm.
     * @param farmId of the farm
     * first it is checked if the Farm exists with the function findById @exception 404 Farmer not Found
     * then the SoilData are retrieved using the findByFarm function @exception 404 No SOilData Found
     * if SoilData are found, they are given back as response
     * @return SoilData
     */
    public Response getSoilDataOfFarm(Long farmId){
        Response response=new Response();
        Farm farm=farmRepository.findById(farmId).orElse(null);
        if(farm==null){
            response.setCode(404);
            response.setMessage("farm Not Found");
        }
        else {
            Iterable<SoilData> soilData = soilDataRepository.findByFarm(farmId);
            if (soilData.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No SoilData Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(soilData));
            }
        }
        return response;
    }

    /**
     * Get information about the SoilData.
     * @param soilDataId
     * first it is checked if the soilData exists with the function findById @exception 404 SoilData nNot Found
     * totherwise the SoilData are retrieved and given as response
     * @return SoilData
     */
    public Response getSoilData(Long soilDataId){
        Response response=new Response();
        SoilData soilData= soilDataRepository.findById(soilDataId).orElse(null);
        if(soilData==null){
            response.setCode(404);
            response.setMessage("SoilData Not Found");
        }
        else {
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(soilData));
        }
        return response;
    }

    /**
     * Creates a new instance of a SoilData.
     * @param farmId
     * @param newSoilData
     * first, the Farmer is searched in the database with the function findById @exception 404 Farm Not Found
     * otherwise the new soilData is created
     * @return SoilData
     */
    public Response createSoilData(Long farmId, SoilData newSoilData){
        Response response=new Response();
        Farm farm=farmRepository.findById(farmId).orElse(null);
        if(farm==null){
            response.setCode(404);
            response.setMessage("farm Not Found");
        }
        else {
            newSoilData.setFarm(farm);
            newSoilData.setDate();
            soilDataRepository.save(newSoilData);
            Farmer farmer= farmerRepository.findById(farm.getFarmer()).orElse(null);
            String desc= "The local agronomist just added the soil data about your farm, go take a look!";
            NotificationFarmer notificationFarmer=new NotificationFarmer("NEW_SOILDATA",desc,farmer,null);
            notificationFarmerRepository.save(notificationFarmer);
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(newSoilData));
        }
        return response;
    }

    /**
     * Updates an instance of an object SoilData
      * @param farmId
     * @param soilDataId
     * @param soilDataDto
     * first, the farm is searched with the function findById @exception 404 Farm Not Found
     * then, it is checked if the SoilData exists with the function findById @exception 404 SoilData Not Found
     * then the SoilData is updated.
     * @return SoilData
     */
    public Response updateSoilData(Long farmId, Long soilDataId,SoilData soilDataDto){
        Response response=new Response();
        Farm farm=farmRepository.findById(farmId).orElse(null);
        if(farm==null){
            response.setCode(404);
            response.setMessage("farm Not Found");
        }
        else {
            SoilData soilDataToUpdate=soilDataRepository.findById(soilDataId).orElse(null);
            if(soilDataToUpdate==null){
                response.setCode(404);
                response.setMessage("Soil Data Not Found");
            }
            else {
                soilDataToUpdate.setFarm(farm);
                soilDataToUpdate.setNitrogen(soilDataDto.getNitrogen());
                soilDataToUpdate.setOrganic_carbon(soilDataDto.getOrganic_carbon());
                soilDataToUpdate.setPH(soilDataDto.getPH());
                soilDataToUpdate.setPhosphorus(soilDataDto.getPhosphorus());
                soilDataToUpdate.setLimestone(soilDataDto.getLimestone());
                soilDataRepository.save(soilDataToUpdate);
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(soilDataToUpdate));
            }
        }
        return response;
    }

    /**
     * Deletes an instance of a SoilData instance identified
     * by a given Id
      * @param soilDataId
     * first it is checked if the SoilData exists with the function findById @exception 400 SoilData Not Found
     * then the SoilData is deleted
     * @return SoilData
     */
    public Response deleteSoilData(Long soilDataId){
        Response response=new Response();
        SoilData soilDataToDelete=soilDataRepository.findById(soilDataId).orElse(null);
        if(soilDataToDelete==null){
            response.setCode(400);
            response.setMessage("SoilData Not Found");
        }
        else{
            soilDataRepository.delete(soilDataToDelete);
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(soilDataToDelete));
        }
        return response;
    }
}
