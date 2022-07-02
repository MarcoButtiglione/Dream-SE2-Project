package com.se2project.dream.repository;

import com.se2project.dream.entity.Production;
import com.se2project.dream.entity.SoilData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SoilDataRepository  extends CrudRepository<SoilData, Long> {
    @Query(value = "SELECT * FROM soil_data WHERE farm_fk= ?1 ORDER BY date desc", nativeQuery = true)
    Iterable<SoilData> findByFarm(Long farm);
}
