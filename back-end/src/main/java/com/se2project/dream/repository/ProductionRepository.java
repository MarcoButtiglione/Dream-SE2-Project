package com.se2project.dream.repository;

import com.se2project.dream.entity.Product;
import com.se2project.dream.entity.Production;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductionRepository  extends CrudRepository<Production, Long> {
    @Query(value = "SELECT * FROM production WHERE farm_fk= ?1 ORDER BY date desc", nativeQuery = true)
    Iterable<Production> findByFarm(Long farm);

    @Query(value = "SELECT * FROM production WHERE farm_fk= ?1 and product_fk= ?2 ORDER BY date desc", nativeQuery = true)
    Iterable<Production> findByFarmAndProduct(Long farm, Long product);
}