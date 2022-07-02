package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Product;
import com.se2project.dream.service.FarmService;
import com.se2project.dream.service.LoginService;
import com.se2project.dream.service.ProductService;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api")
@CrossOrigin
@RestController
public class ProductController {
    private final ProductService productService;
    private final LoginService loginService;
    private final FarmService farmService;

    public ProductController(ProductService productService, LoginService loginService, FarmService farmService) {
        this.productService = productService;
        this.loginService = loginService;
        this.farmService = farmService;
    }

    /**
     * Get information about the Product given his id used by the Agronomist
     * @param id of the product
     * the id is sent to the function getProduct @see productService that return the product information
     * @return Product
     * @exception 404 Product Not Found
     * */
    @GetMapping("/agronomist/GetProductA/{id}")
    Response getProductA(@PathVariable Long id){
        return productService.getProduct(id);
    }

    /**
     * * Get information about the Product given his id used by the Farmer
     * @param id of the product
     * the id is sent to the function getProduct @see productService that return the product information
     * @return Product
     * @exception 404 Product Not Found
     * */
    @GetMapping("/farmer/GetProductF/{id}")
    Response getProductF(@PathVariable Long id){
        return productService.getProduct(id);
    }

    /**
     * Get all Products produced in the farm of the logged farmer filtered by defined type
     * @param tokenF the personal token generated from the login of the farmer
     * @param type to filter the products
     * from the token is extrapolated the id with the function @see getIdFromTokenA() in LoginService
     * then the id is send to the function getFarmId @see farmService that return the farmId of the farmer
     * the farmId is sent to the function getProductsByType @see productService that return the set of products
     * @return set<product>
     * @exception 400 No Product Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/farmer/GetProductsByType/")
    Response getProductsByType(@RequestHeader("Authorization") String tokenF, @RequestParam String type){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        Long farmId = farmService.getFarmId(farmerId);
        return productService.getProductsByType(farmId, type);
    }

    /**
     * Get all Products produced in the farm of the logged farmer filtered by defined product
     * @param tokenF the personal token generated from the login of the farmer
     * @param product to filter the products
     * from the token is extrapolated the id with the function @see getIdFromTokenA() in LoginService
     * then the id is send to the function getFarmId @see farmService that return the farmId of the farmer
     * the farmId is sent to the function getProductsByProduct @see productService that return the set of products
     * @return set<product>
     * @exception 400 No Product Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/farmer/GetProductsByProduct/")
    Response getProductsByProduct(@RequestHeader("Authorization") String tokenF, @RequestParam String product){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        Long farmId = farmService.getFarmId(farmerId);
        return productService.getProductsByProduct(farmId, product);
    }

    /**
     * Get all Products produced in the farm of the logged farmer
     * @param tokenF the personal token generated from the login of the farmer
     * from the token is extrapolated the id with the function @see getIdFromTokenF() in LoginService
     * then the id is send to the function getFarmId @see farmService that return the farmId of the farmer
     * the farmId is sent to the function getProductsByFarm @see productService that return the set of products
     * @return set<product>
     * @exception 400 No Product Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/farmer/GetProductsByFarm/")
    Response getProductsByFarmF(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        Long farmId = farmService.getFarmId(farmerId);
        return productService.getProductsByFarm(farmId);
    }

    /**
     * Get all Products produced in the farm of a farmer defined by id, used by the agronomist
     * @param farmerId of the farmer the agronomist want to see the products of
     * the id is send to the function getFarmId @see farmService that return the farmId of the farmer
     * the farmId is sent to the function getProductsByFarm @see productService that return the set of products
     * @return set<product>
     * @exception 400 No Product Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/agronomist/GetProductsByFarm/{farmerId}")
    Response getProductsByFarmA(@PathVariable Long farmerId){
        Long farmId = farmService.getFarmId(farmerId);
        return productService.getProductsByFarm(farmId);
    }

    /**
     * Function that allow the logged farmer to create a product
     * @param tokenF farmer authentication token generated on login
     * @param newProduct product to insert
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then the function createProduct @see productService create the product
     * @return newProduct
     * @exception 404 Farmer Not Found
     * */
    @PostMapping("/farmer/PostProduct/")
    Response createProduct(@RequestHeader("Authorization") String tokenF, @RequestBody Product newProduct){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        Long farmId = farmService.getFarmId(farmerId);
        return productService.createProduct(farmId, newProduct);
    }

    /**
     * Function that allow the logged farmer to update a product
     *@param tokenF farmer authentication token generated on login
     * @param productId of the product the farmer want to update
     * @param productDto product new value
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then the function updateProduct @see productService update the product
     * @return productUpdated
     * @exception 404 Product Not Found
     * @exception 404 Farmer Not Found
     * @exception 400 You cannot update this product
     * */
    @PostMapping("/farmer/PutProduct/{productId}")
    Response updateProduct(@PathVariable Long productId, @RequestBody Product productDto, @RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return productService.updateProduct(productId, productDto, farmerId);
    }

    /**
     * Function that allow the logged farmer to delete a product
     *@param tokenF farmer authentication token generated on login
     * @param id of the product the farmer want to delete
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then the function deleteProduct @see productService delete the product
     * @return productDeleted
     * @exception 404 Product Not Found
     * @exception 404 Farmer Not Found
     * @exception 400 You cannot update this product
     * @exception 400 The product that you are trying to delete is associated with an production, so it cannot be deleted!
     * */
    @GetMapping("/farmer/DeleteProduct/{id}")
    Response deleteProduct(@PathVariable Long id,@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return productService.deleteProduct(id, farmerId);
    }
}