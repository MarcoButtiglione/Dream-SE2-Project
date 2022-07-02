package com.se2project.dream.service;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.*;
import com.se2project.dream.repository.FarmRepository;
import com.se2project.dream.repository.FarmerRepository;
import com.se2project.dream.repository.ProductRepository;
import com.se2project.dream.repository.ProductionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductionRepository productionRepository;
    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;

    public ProductService(ProductRepository productRepository, ProductionRepository productionRepository, FarmRepository farmRepository, FarmerRepository farmerRepository) {
        this.productRepository = productRepository;
        this.productionRepository = productionRepository;
        this.farmRepository = farmRepository;
        this.farmerRepository = farmerRepository;
    }

    /**
     * Method to get a Product
      * @param productId
     * @exception 404 Product Not Found
     * @return product
     */
    public Response getProduct(Long productId){
        Response response=new Response();
        Product product= productRepository.findById(productId).orElse(null);
        if(product==null){
            response.setCode(404);
            response.setMessage("Product Not Found");
        }
        else {
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(product));
        }
        return response;
    }

    /**
     * Method to get Products given the type
     * @param farmId
     * @param type
     * @exception 400 Farm Not Found
     * @exception 404 No Product Found
     * @return Product
     */

    public Response getProductsByType(Long farmId, String type){
        Response response=new Response();
        Farm farm= farmRepository.findById(farmId).orElse(null);
        if(farm==null){
            response.setCode(400);
            response.setMessage("Farm Not Found");
        }
        else {
            Iterable<Product> products = productRepository.findByType(farmId,type);
            if (products.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No Product Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(products));
            }
        }
        return response;
    }

    /**
     * Method to get Products given the name of it and the farmId
      * @param farmId
     * @param product
     * @exception 400 Farm Not Found
     * @exception 404 No Product Found
     * @return Product
     */
    public Response getProductsByProduct(Long farmId, String product){
        Response response=new Response();
        Farm farm= farmRepository.findById(farmId).orElse(null);
        if(farm==null){
            response.setCode(400);
            response.setMessage("Farm Not Found");
        }
        else {
            Iterable<Product> products = productRepository.findByProduct(farmId, product);
            if (products.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No Product Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(products));
            }
        }
        return response;
    }

    /**
     * Method to get Products given the farmId
      * @param farmId
     * @exception 404 No Farm Found
     * @exception 404 No Product Found
     * @return Product
     */
    public Response getProductsByFarm(Long farmId){
        Response response=new Response();

        Farm farm=farmRepository.findById(farmId).orElse(null);
        if (farm == null) {
            response.setCode(404);
            response.setMessage("No Farm Found");
        }
        else {
            Iterable<Product> products = productRepository.findByFarm(farmId);
            if (products.toString() == "[]") {
                response.setCode(404);
                response.setMessage("No Product Found");
            } else {
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(products));
            }
        }
        return response;
    }

    /**
     * Method to create a new Product
      * @param farmId
     * @param newProduct
     * @exception 400 Farm Not Found
     * @return Product
     */
    public Response createProduct(Long farmId, Product newProduct){
        Response response=new Response();
        Farm farm=farmRepository.findById(farmId).orElse(null);
        if(farm==null){
            response.setCode(400);
            response.setMessage("Farm Not Found");
        }
        else {
            newProduct.setFarm(farm);
            productRepository.save(newProduct);
            response.setCode(200);
            response.setMessage("success");
            response.setResults(Collections.singleton(newProduct));
        }
        return response;
    }

    /**
     * Method to update a Product
      * @param productId
     * @param productDto
     * @param farmerId
     * @exception 404 Product Not Found
     * @exception 404 Farmer Not Found
     * @exception 400 Tou cannot update this product
     * @return Product
     */
    public Response updateProduct(Long productId, Product productDto, Long farmerId){
        Response response=new Response();
        Product productToUpdate=productRepository.findById(productId).orElse(null);
        if(productToUpdate==null){
            response.setCode(404);
            response.setMessage("Product Not Found");
        }
        else{
            Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
            if(farmer==null){
                response.setCode(404);
                response.setMessage("Farmer Not Found");
            }
            else {
                Farm farm=farmRepository.findById(productToUpdate.getFarm()).orElse(null);
                if(farmerId==farm.getFarmer()) {
                    productToUpdate.setProduct(productDto.getProduct());
                    productToUpdate.setSpecifics(productDto.getSpecifics());
                    productToUpdate.setType(productDto.getType());
                    productRepository.save(productToUpdate);
                    response.setCode(200);
                    response.setMessage("success");
                    response.setResults(Collections.singleton(productToUpdate));
                }
                else{
                    response.setCode(400);
                    response.setMessage("You cannot update this product");
                }
            }
        }

        return response;
    }

    /**
     * Method to delete a Product
      * @param productId
     * @param farmerId
     * @exception 400 Product Not Found
     * @exception 404 Farmer Not Found
     * @exception 400 You cannot update this product
     * @exception 400 The product that you are trying to delete is associated with an production, so it cannot be deleted!
     * @return Product
     */
    public Response deleteProduct(Long productId, Long farmerId){
        Response response=new Response();
        Product productToDelete=productRepository.findById(productId).orElse(null);
        if(productToDelete==null){
            response.setCode(400);
            response.setMessage("Product Not Found");
        }
        else{
            Iterable<Production> productions=productionRepository.findByFarmAndProduct(productToDelete.getFarm(),productId);
            if(productions.toString().equals("[]")) {
                Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
                if(farmer==null){
                    response.setCode(404);
                    response.setMessage("Farmer Not Found");
                }
                else {
                    Farm farm = farmRepository.findById(productToDelete.getFarm()).orElse(null);
                    if (farmerId == farm.getFarmer()) {
                        productRepository.delete(productToDelete);
                        response.setCode(200);
                        response.setMessage("success");
                        response.setResults(Collections.singleton(productToDelete));
                    }
                    else{
                        response.setCode(400);
                        response.setMessage("You cannot update this product");
                    }
                }
            }
            else{
                response.setCode(400);
                response.setMessage("The product that you are trying to delete is associated with an production, so it cannot be deleted! ");
            }
        }
        return response;
    }
}
