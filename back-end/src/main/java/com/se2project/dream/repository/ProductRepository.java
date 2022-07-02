package com.se2project.dream.repository;

import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
public interface ProductRepository  extends CrudRepository<Product, Long> {
    @Query(value = "SELECT * FROM product WHERE farm_fk= ?1 and type like '%' ?2 '%' order by product", nativeQuery = true)
    Iterable<Product> findByType(Long farmId, String type);

    @Query(value = "SELECT * FROM product WHERE farm_fk=  ?1 and product like '%' ?2 '%' order by product", nativeQuery = true)
    Iterable<Product> findByProduct(Long farmId, String product);

    @Query(value = "SELECT * FROM product WHERE farm_fk= ?1 order by product", nativeQuery = true)
    Iterable<Product> findByFarm(Long farmId);
}