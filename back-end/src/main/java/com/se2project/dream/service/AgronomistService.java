package com.se2project.dream.service;
import com.se2project.dream.entity.Farmer;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.Location;
import com.se2project.dream.repository.AgronomistRepository;
import com.se2project.dream.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service  @Transactional @Slf4j
public class AgronomistService {

    private final AgronomistRepository agronomistRepository;
    private final LocationRepository locationRepository;
    private final PasswordEncoder passwordEncoder;


    public AgronomistService(AgronomistRepository agronomistRepository, LocationRepository locationRepository, PasswordEncoder passwordEncoder) {
        this.agronomistRepository = agronomistRepository;
        this.locationRepository = locationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get information about the Agronomist given his id
     * @param agronomistId
     * first is checked if the agronomist exist in the database using he function findById
     * if exist @return Agronomist
     * if not @exception 404 Agronomist Not Found
     * */
    public Response getAgronomist(Long agronomistId)  {
        Optional<Agronomist> agronomist = agronomistRepository.findById(agronomistId);
        Response response = new Response();
        if (agronomist.isPresent()){
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(agronomist));
        }
        else{
            response.setCode(404);
            response.setMessage("No Agronomist Found");
        }
        return response;
    }

    /**
     * Get information about the Agronomist given his aadhaar
     * @param aadhaar
     * first is checked if the agronomist exist in the database using he function findByAadhaar @see agronomistRepository
     * if exist @return Agronomist
     * else @exception 404 Agronomist Not Found
     * */
        public Response getAgronomistByAadhaar(String aadhaar)  {
        Agronomist agronomist = agronomistRepository.findByAadhaar(aadhaar);
        Response response = new Response();
        if (agronomist==null){
            response.setCode(404);
            response.setMessage("No Agronomist Found");
        }
        else{
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(agronomist));
        }
        return response;
    }

    /**
     * Get information about the Agronomist given his location
     * @param locationId
     * first is checked if the given location exist:
     * if exists: then is checked if the agronomist exist in the database using he function findByLocation @see agronomistRepository
     *              * if exist @return Agronomist
     *              * else @exception 404 Agronomist Not Found
     * else  @exception 404 location not found
     * */
    public Response getAgronomistByLocation(Long locationId)  {
        Response response = new Response();
        Optional<Location> location = locationRepository.findById(locationId);
        if(location.isPresent()) {
            Agronomist agronomist = agronomistRepository.findByLocation(locationId);
            if (agronomist == null) {
                response.setCode(404);
                response.setMessage("No Agronomist Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(agronomist));
            }
        }
        else{
            response.setCode(404);
            response.setMessage("Location Not Found");
        }
        return response;
    }

    /**
     * create a new Agronomist
     * @param locationId the location the new agronomist will be responsible for
     * @param newAgronomist the agronomist data to insert
     * first is checked if the agronomist exist by function findByAadhaar @exception 404 Agronomist Not Found
     * then the location with the function findById @exception Location Not Found
     * then is check if exist an agronomist with the same email @exception 404 Email already used
     * then is checked if the location already have an agronomist @exception 400 Agronomist for that location already present
     * else password is crypted and agronomist is save
     * @return Agronomist
     */
    public Response createAgronomist(Long locationId, Agronomist newAgronomist){
        Response response = new Response();
        Agronomist agronomist= agronomistRepository.findByAadhaar(newAgronomist.getAadhaar());
        if(agronomist!=null){
            response.setCode(400);
            response.setMessage("The agronomist is already present");
        }
        else{
            Location location=locationRepository.findById(locationId).orElse(null);
            if(location==null){
                response.setCode(404);
                response.setMessage("Location Not Found");
            }
            else{
                Agronomist checkEmail = agronomistRepository.findByEmail(newAgronomist.getEmail());
                if(checkEmail!=null){
                    response.setCode(400);
                    response.setMessage("Email already used");
                }
                else {
                    Agronomist checkLocation = agronomistRepository.findByLocation(locationId);
                    if(checkLocation!=null){
                        response.setCode(400);
                        response.setMessage("Agronomist for that location already present");
                    }
                    else {
                        newAgronomist.setLocation(location);
                        newAgronomist.setPassword(passwordEncoder.encode(newAgronomist.getPassword()));
                        agronomistRepository.save(newAgronomist);
                        response.setCode(200);
                        response.setMessage("Success");
                        response.setResults(Collections.singleton(newAgronomist));
                    }
                }
            }
        }
        return response;
    }

    /**
     * update an Agronomist
     * @param locationId the location the new agronomist will be responsible for
     * @param agronomistId the agronomist to update
     * @param agronomistDto data to update
     * first is get the agronomist to update with function findById @exception 404 Agronomist Not Found
     * first is checked if the agronomist exist by function findByAadhaar @exception 404 Aadhaar already signed up
     * then the location with the function findById @exception Location Not Found
     * then is check if exist an agronomist with the same email @exception 404 Email already used
     * then is checked if the location already have an agronomist @exception 400 Agronomist for that location already present
     * else password is crypted and agronomist is updated and saved
     * @return Agronomist
     */
    public Response updateAgronomist( Long agronomistId, Long locationId, Agronomist agronomistDto){
        Response response = new Response();
        Agronomist agronomistToUpdate = agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomistToUpdate==null){
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Location location=locationRepository.findById(locationId).orElse(null);
            if(location==null){
                response.setCode(404);
                response.setMessage("Location Not Found");
            }
            else{
                Agronomist checkEmail = agronomistRepository.findByEmail(agronomistDto.getEmail());
                if(checkEmail!=null && checkEmail.getId()!=agronomistId){
                    response.setCode(400);
                    response.setMessage("Email already used");
                }
                else{
                    Agronomist checkAadhaar = agronomistRepository.findByAadhaar(agronomistDto.getAadhaar());
                    if(checkAadhaar!=null && checkAadhaar.getId()!=agronomistId){
                        response.setCode(400);
                        response.setMessage("Aadhaar already signed up");
                    }
                    else
                    {
                        Agronomist checkLocation = agronomistRepository.findByLocation(locationId);
                        if(checkLocation!=null && checkLocation.getId()!=agronomistId){
                            response.setCode(400);
                            response.setMessage("Agronomist for that location already present");
                        }
                        else {
                            agronomistToUpdate.setFirstName(agronomistDto.getFirstName());
                            agronomistToUpdate.setLastName(agronomistDto.getLastName());
                            agronomistToUpdate.setAadhaar(agronomistDto.getAadhaar());
                            agronomistToUpdate.setEmail(agronomistDto.getEmail());
                            //agronomistToUpdate.setPassword(agronomistDto.getPassword());
                            agronomistToUpdate.setPassword(passwordEncoder.encode(agronomistDto.getPassword()));
                            agronomistToUpdate.setTelephone(agronomistDto.getTelephone());
                            agronomistToUpdate.setLocation(location);
                            agronomistRepository.save(agronomistToUpdate);
                            response.setCode(200);
                            response.setMessage("Success");
                            response.setResults(Collections.singleton(agronomistToUpdate));
                        }
                    }
                }
            }
        }
        return response;
    }

    /**
     * Get information about the Agronomist given his email
     * @param email
     * first  is checked if the agronomist exist in the database using the function findByEmail @see agronomistRepository
     * if exist @return Agronomist
     * @exception 404 Agronomist Not Found
     * */
    public Agronomist getAgronomistByEmail(String email)  {
        Agronomist agronomist = agronomistRepository.findByEmail(email);
        if (agronomist != null){
            log.info("Agronomist found by email");
        }
        else{
            log.error("Agronomist not found by email");
        }
        return agronomist;
    }

}