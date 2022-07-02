package com.se2project.dream.service;

import com.se2project.dream.extraClasses.Allert;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.Farm;
import com.se2project.dream.entity.Farmer;
import com.se2project.dream.repository.AgronomistRepository;
import com.se2project.dream.repository.FarmRepository;
import com.se2project.dream.repository.FarmerRepository;
import com.se2project.dream.repository.MeetingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service @Transactional @Slf4j
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final FarmRepository farmRepository;
    private final AgronomistRepository agronomistRepository;
    private final PasswordEncoder passwordEncoder;
    private final MeetingRepository meetingRepository;

    public FarmerService(FarmerRepository farmerRepository, FarmRepository farmRepository, AgronomistRepository agronomistRepository, PasswordEncoder passwordEncoder, MeetingRepository meetingRepository) {
        this.farmerRepository = farmerRepository;
        this.farmRepository = farmRepository;
        this.agronomistRepository = agronomistRepository;
        this.passwordEncoder = passwordEncoder;
        this.meetingRepository = meetingRepository;
    }

    /**
     * Get information about the Farmer given his id
     * @param farmerId
     * first is checked if the farmer exist in the database using he function findById
     * if exist @return Farmer
     * if not @exception 404 Farmer Not Found
     * */
    public Response getFarmer(Long farmerId)  {
        Optional<Farmer> farmer = farmerRepository.findById(farmerId);
        Response response = new Response();
        if (farmer.isPresent()){
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(farmer));
        }
        else{
            response.setCode(404);
            response.setMessage("No Farmer Found");
        }
        return response;
    }

    /**
     * Get information about the Farmer given his email
     * @param email
     * first is checked if the farmer exist in the database using he function findByEmail
     * if exist @return Farmer
     * if not @exception 404 Farmer Not Found
     * */
    public Farmer getFarmerByEmail(String email)  {
        Farmer farmer = farmerRepository.findByEmail(email);
        if (farmer != null){
            log.info("Farmer found by email");
        }
        else{
            log.error("Farmer not found by email");
        }
        return farmer;
    }

    /**
     * Get information about the Farmer given his aadhaar
     * @param aadhaar
     * first is checked if the farmer exist in the database using he function findByAadhaar
     * if exist @return Farmer
     * if not @exception 404 Farmer Not Found
     * */
    public Response getFarmerByAadhaar(String aadhaar)  {
        Farmer farmer = farmerRepository.findByAadhaar(aadhaar);
        Response response = new Response();
        if (farmer==null){
            response.setCode(404);
            response.setMessage("No farmer Found");
        }
        else{
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(farmer));
        }
        return response;
    }

    /**
     * Get Farmers in the same location of an agronomist witn a boolean allert that define if the agronomist made qith that farmer
     * at least two meeting
     * @param agronomistId
     * first is checked if the agronomist exist in the database using he function findById else @exception 404 Farmer Not Found
     * then using the function findAllFarmByLocation(agronomist location) @see farmRepository it return all farms of his location
     * if empty @exception No Farmers Found
     * else for each farmer is checked the ammount of meeting in current year using countMeeeting @see meetingRepository
     * if count < 2 allert true else false
     * @return farmers + allert
     */
    public Response getFarmerByAgronomist(Long agronomistId)  {
        Response response = new Response();
        Optional<Agronomist> agronomist = agronomistRepository.findById(agronomistId);
        if(agronomist.isPresent()) {
            Iterable<Farm> farms = farmRepository.findAllFarmByLocation(agronomist.get().getLocation());
            if(farms.toString()=="[]"){
                response.setCode(404);
                response.setMessage("No Farmers Found");
            }
            else {
                List<Allert> farmers = new ArrayList<Allert>();
                LocalDate start= LocalDate.of(LocalDate.now().getYear(), 01,01);
                LocalDate end= LocalDate.of(LocalDate.now().getYear(), 12,31);
                boolean allert=false;
                farms.forEach(farm -> {
                    int count = meetingRepository.countMeeeting(farm.getFarmer(),agronomistId,start,end);
                    if (count>=2){
                        farmers.add(new Allert(false,farmerRepository.findById(farm.getFarmer()).orElse(null)));
                    }
                    else{
                        farmers.add(new Allert(true,farmerRepository.findById(farm.getFarmer()).orElse(null)));
                    }
                });
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(farmers));
            }

        }
        else{
            response.setCode(404);
            response.setMessage("Location Not Found");
        }
        return response;
    }

    /**
     * create a new Farmer
     * @param newFarmer the new farmer information
     * first is checked if all fields has been filled else @exception 400 Fill all fields
     * then is checked if the farmer already in the db by findById @exception 400 The farmer is already present
     * then is checked if the farmer email is already in the db by findByEmail @exception 400 Email already used
     * then the password is crypded and new farmer is saved in the db
     * @return newFarmer
     */
    public Response createFarmer(Farmer newFarmer){
        Response response = new Response();
        Farmer farmer= farmerRepository.findByAadhaar(newFarmer.getAadhaar());
        if(
                newFarmer.getFirstName()==""||
                        newFarmer.getLastName()==""||
                        newFarmer.getEmail()==""||
                        newFarmer.getAadhaar()==""||
                        newFarmer.getPassword()==""||
                        newFarmer.getTelephone()==""
        ){
            response.setCode(400);
            response.setMessage("Fill all fields");
        }else {
            if(farmer!=null){
                response.setCode(400);
                response.setMessage("The farmer is already present");
            }
            else{
                Farmer checkEmail = farmerRepository.findByEmail(newFarmer.getEmail());
                if(checkEmail!=null){
                    response.setCode(400);
                    response.setMessage("Email already used");
                }
                else {
                    newFarmer.setPassword(passwordEncoder.encode(newFarmer.getPassword()));
                    farmerRepository.save(newFarmer);
                    response.setCode(200);
                    response.setMessage("Success");
                    //response.setResults(Collections.singleton(newFarmer));
                }
            }
        }

        return response;
    }

    /**
     * update Farmer
     * @param farmerId the farmer to update
     * @param farmerDto data to update
     * first is checked if farmer exist using findbyId @exception 404 farmer not found
     * then is checked if the farmer email is already in the db by findByEmail @exception 400 Email already used
     * then is checked if the farmer aadhaar is already in the db by findByAadhaar @exception 400 Aadhaar already signed up
     * then the password is crypded and farmer updated is saved in the db
     * @return updatedFarmer
     */
    public Response updatefarmer( Long farmerId, Farmer farmerDto){
        Response response = new Response();
        Farmer farmerToUpdate = farmerRepository.findById(farmerId).orElse(null);
        if(farmerToUpdate==null){
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else{
            Farmer checkEmail = farmerRepository.findByEmail(farmerDto.getEmail());
            if(checkEmail!=null){
                response.setCode(400);
                response.setMessage("Email already used");
            }
            else{
                Farmer checkAadhaar = farmerRepository.findByAadhaar(farmerDto.getAadhaar());
                if(checkEmail!=null){
                    response.setCode(400);
                    response.setMessage("Aadhaar already signed up");
                }
                else
                {
                    farmerToUpdate.setFirstName(farmerDto.getFirstName());
                    farmerToUpdate.setLastName(farmerDto.getLastName());
                    farmerToUpdate.setAadhaar(farmerDto.getAadhaar());
                    farmerToUpdate.setEmail(farmerDto.getEmail());
                    farmerToUpdate.setPassword(passwordEncoder.encode(farmerDto.getPassword()));
                    farmerToUpdate.setTelephone(farmerDto.getTelephone());
                    farmerRepository.save(farmerToUpdate);
                    response.setCode(200);
                    response.setMessage("Success");
                    response.setResults(Collections.singleton(farmerToUpdate));
                }
            }

        }
        return response;
    }

}
