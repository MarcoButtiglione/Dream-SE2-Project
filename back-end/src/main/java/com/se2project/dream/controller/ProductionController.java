package com.se2project.dream.controller;

import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Production;
import com.se2project.dream.service.FarmService;
import com.se2project.dream.service.LoginService;
import com.se2project.dream.service.ProductionService;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api")
@CrossOrigin
@RestController
public class ProductionController {
    private final ProductionService productionService;
    private final LoginService loginService;
    private final FarmService farmService;

    public ProductionController(ProductionService productionService, LoginService loginService, FarmService farmService) {
        this.productionService = productionService;
        this.loginService = loginService;
        this.farmService = farmService;
    }

    /**
     * Get all production produced in the farm of a farmer defined by id, used by the agronomist
     * @param farmerId of the farmer the agronomist want to see the production of
     * the id is send to the function getFarmId @see farmService that return the farmId of the farmer
     * the farmId is sent to the function getProductionsByFarm @see productionService that return the set of production
     * @return set<production>
     * @exception 400 No Production Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/agronomist/GetProductionsByFarm/")
    Response getProductionsByFarmA(Long farmerId){
        Long farmId = farmService.getFarmId(farmerId);
        return productionService.getProductionsByFarm(farmId);
    }

    /**
     * Get all Production produced in the farm of the logged farmer
     * @param tokenF the personal token generated from the login of the farmer
     * from the token is extrapolated the id with the function @see getIdFromTokenF() in LoginService
     * then the id is send to the function getFarmId @see farmService that return the farmId of the farmer
     * the farmId is sent to the function getProductionsByFarm @see productionService that return the set of production
     * @return set<production>
     * @exception 400 No Production Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/farmer/GetProductionsByFarm/")
    Response getProductionsByFarmF(@RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        Long farmId = farmService.getFarmId(farmerId);
        return productionService.getProductionsByFarm(farmId);
    }

    /**
     * Get all Production produced in the farm of the logged farmer filtred by product
     * @param tokenF the personal token generated from the login of the farmer
     * @param productId of the product the farmer want to see the production of
     * from the token is extrapolated the id with the function @see getIdFromTokenF() in LoginService
     * then the id is send to the function getFarmId @see farmService that return the farmId of the farmer
     * the farmId is sent to the function GetProductionsByFarmAndProduct @see productionService that return the set of production
     * @return set<production>
     * @exception 400 No Production Found
     * @exception 400 No Product Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/farmer/GetProductionsByFarmAndProduct/{productId}")
    Response GetProductionsByFarmAndProductF(@RequestHeader("Authorization") String tokenF,@PathVariable Long productId){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        Long farmId = farmService.getFarmId(farmerId);
        return productionService.GetProductionsByFarmAndProduct(farmId,productId);
    }

    /**
     * Get all Production produced in the farm of a farmer filtred by product, used by the agronomist
     * @param farmerId of the farmer the agronomist want to see the production
     * @param productId of the product the agronomist want to see the production of
     * the farmerId is send to the function getFarmId @see farmService that return the farmId of the farmer
     * the farmId is sent to the function GetProductionsByFarmAndProduct @see productionService that return the set of production
     * @return set<production>
     * @exception 400 No Production Found
     * @exception 400 No Product Found
     * @exception 404 Farm Not Found
     * */
    @GetMapping("/agronomist/GetProductionsByFarmAndProduct/{farmerId}")
    Response GetProductionsByFarmAndProductA(@PathVariable Long farmerId,@RequestParam Long productId){
        Long farmId = farmService.getFarmId(farmerId);
        return productionService.GetProductionsByFarmAndProduct(farmId,productId);
    }

    /**
     * Function that allow the logged farmer to create a production
     * @param tokenF farmer authentication token generated on login
     * @param newProduction production to insert
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then the id is send to the function getFarmId @see farmService that return the farmId of the farmer
     * then the function createProduction @see productionService create the production
     * @return newProduction
     * @exception 404 Farmer Not Found
     * */
    @PostMapping("/farmer/PostProduction/{productId}")
    Response createProduction(@RequestHeader("Authorization") String tokenF, @PathVariable Long productId, @RequestBody Production newProduction){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        Long farmId = farmService.getFarmId(farmerId);
        return productionService.createProduction(farmId,productId,newProduction);
    }

    /**
     * Function that allow the logged farmer to update a production
     * @param tokenF farmer authentication token generated on login
     * @param productId of the product you want to update the production
     * @param productionId of the production you want to update
     * @param productionDto production new value
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then the id is send to the function getFarmId @see farmService that return the farmId of the farmer
     * then the function updateProduction @see productionService update the production
     * @return updateProduction
     * @exception 404 Farmer Not Found
     * @exception 404 Product Not Found
     * @exception 404 Production Not Found
     * */
    @PostMapping("/farmer/PutProduction/{productId}")
    Response updateProduction(@RequestHeader("Authorization") String tokenF, @PathVariable Long productId, @RequestParam Long productionId, @RequestBody Production productionDto){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return productionService.updateProduction(farmerId,productId,productionId,productionDto);
    }

    /**
     * Function that allow the logged farmer to delete a production
     * @param tokenF farmer authentication token generated on login
     * @param id of production to delete
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then the function deleteProduction @see productionService create the production
     * @return productionDeleted
     * @exception 404 Farmer Not Found
     * @exception 404 Production Not Found
     * @exception 400 You cannot delete this production
     * */    @GetMapping("/farmer/DeleteProduction/{id}")
    Response deleteProduction(@PathVariable Long id, @RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return productionService.deleteProduct(id, farmerId);
    }
}