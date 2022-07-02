package com.se2project.dream.repository;

import com.se2project.dream.entity.Comment;
import com.se2project.dream.entity.Farm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FarmRepository  extends CrudRepository<Farm, Long> {
    @Query(value = "SELECT * FROM farm WHERE farmer_fk = ?1 ", nativeQuery = true)
    Farm findFarm(Long farmerId);
    @Query(value = "SELECT * FROM farm WHERE location_fk = ?1 ", nativeQuery = true)
    Iterable<Farm> findAllFarmByLocation(Long locationId);
    @Query(value = "SELECT * FROM farm WHERE location_fk = ?2  and farmer_fk= ?1", nativeQuery = true)
    Iterable<Farm> findAllFarmByLocationAndFarmer(Long farmId, Long locationId);
}
