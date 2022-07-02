package com.se2project.dream.service;

import com.se2project.dream.entity.Farmer;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Farm;
import com.se2project.dream.entity.Product;
import com.se2project.dream.entity.Production;
import com.se2project.dream.repository.FarmRepository;
import com.se2project.dream.repository.FarmerRepository;
import com.se2project.dream.repository.ProductRepository;
import com.se2project.dream.repository.ProductionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProductionService {

    private final ProductRepository productRepository;
    private final ProductionRepository productionRepository;
    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;

    public ProductionService(ProductRepository productRepository, ProductionRepository productionRepository, FarmRepository farmRepository, FarmerRepository farmerRepository) {
        this.productRepository = productRepository;
        this.productionRepository = productionRepository;
        this.farmRepository = farmRepository;
        this.farmerRepository = farmerRepository;
    }

    /**
     * Method to get a Production given the farmId
      * @param farmId
     * @exception 404 Farm Not Found
     * @exception 404 No Production Found
     * @return Production
     */
    public Response getProductionsByFarm(Long farmId){
        Response response=new Response();
        Farm farm=farmRepository.findById(farmId).orElse(null);
        if(farm==null){
            response.setCode(404);
            response.setMessage("farm Not Found");
        }
        else {
            Iterable<Production> productions = productionRepository.findByFarm(farmId);
            if (productions.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No Production Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(productions));
            }
        }
        return response;
    }


    /**
     * Method to get a Production given the farmId and the productId
      * @param farmId
     * @param prductId
     * @exception  404 Farm Not Found
     * @exception 404 Product Not Found
     * @exception  404 No Production Found
     * @return Production
     */
    public Response GetProductionsByFarmAndProduct(Long farmId, Long prductId){
        Response response=new Response();
        Farm farm=farmRepository.findById(farmId).orElse(null);
        if(farm==null){
            response.setCode(404);
            response.setMessage("farm Not Found");
        }
        else {
            Product product=productRepository.findById(prductId).orElse(null);
            if(product==null){
                response.setCode(404);
                response.setMessage("product Not Found");
            }
            else {
                Iterable<Production> productions = productionRepository.findByFarmAndProduct(farmId,prductId);
                if (productions.toString() == "[]") {
                    response.setCode(404);
                    response.setMessage("No Production Found");
                } else {
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(productions));
                }
            }
        }
        return response;
    }

    /**
     * Method to create a new Production
      * @param farmId
     * @param productId
     * @param newProduction
     * @exception 404 Farm Not Found
     * @exception 404 Product Not Found
     * @return Production
     */
    public Response createProduction(Long farmId, Long productId, Production newProduction){
        Response response=new Response();
        Farm farm=farmRepository.findById(farmId).orElse(null);
        if(farm==null){
            response.setCode(404);
            response.setMessage("farm Not Found");
        }
        else {
            Product product=productRepository.findById(productId).orElse(null);
            if(product==null){
                response.setCode(404);
                response.setMessage("product Not Found");
            }
            else {
                newProduction.setProduct(product);
                newProduction.setFarm(farm);
                newProduction.setDate();
                productionRepository.save(newProduction);
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(newProduction));
            }
        }
        return response;
    }

    /**
     * Method to update a production
     * @param farmerId
     * @param productId
     * @param productionId
     * @param productionDto
     * @exception  404 Farm Not Found
     * @exception 404 Product Not Found
     * @exception 400 Product Not Found
     * @exception 400 You cannot update this Production
     * @return Production
     */
    public Response updateProduction(Long farmerId, Long productId, Long productionId, Production productionDto){
        Response response=new Response();
        Farm farm=farmRepository.findFarm(farmerId);
        if(farm==null){
            response.setCode(404);
            response.setMessage("farm Not Found");
        }
        else {
            Product product=productRepository.findById(productId).orElse(null);
            if(product==null){
                response.setCode(404);
                response.setMessage("product Not Found");
            }
            else {
                Production productionToUpdate = productionRepository.findById(productionId).orElse(null);
                if (productionToUpdate == null) {
                    response.setCode(400);
                    response.setMessage("Product Not Found");
                } else {
                    Farm farmCheck =farmRepository.findById(productionToUpdate.getFarm()).orElse(null);
                    if(farmerId==farmCheck.getFarmer()) {
                        productionToUpdate.setFarm(farm);
                        productionToUpdate.setProduct(product);
                        productionToUpdate.setNote(productionDto.getNote());
                        productionToUpdate.setQta(productionDto.getQta());
                        productionRepository.save(productionToUpdate);
                        response.setCode(200);
                        response.setMessage("success");
                        response.setResults(Collections.singleton(productionToUpdate));
                    }
                    else{
                        response.setCode(400);
                        response.setMessage("You cannot update this production");
                    }
                }
            }
        }
        return response;
    }

    /**
     * Method to delete a Product
      * @param productionId
     * @param farmerId
     * @exception 400 Product Not Found
     * @exception 404 Farmer Not Found
     * @exception 400 You cannot update this product
     * @return Production
     */
    public Response deleteProduct(Long productionId, Long farmerId){
        Response response=new Response();
        Production productionToDelete=productionRepository.findById(productionId).orElse(null);
        if(productionToDelete==null){
            response.setCode(400);
            response.setMessage("Product Not Found");
        }
        else{
            Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(404);
                response.setMessage("Farmer Not Found");
            }
            else {
                Farm farm = farmRepository.findById(productionToDelete.getFarm()).orElse(null);
                if (farmerId == farm.getFarmer()) {
                    productionRepository.delete(productionToDelete);
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(productionToDelete));
                }
                else{
                    response.setCode(400);
                    response.setMessage("You cannot update this product");
                }
            }
        }
        return response;
    }
}
