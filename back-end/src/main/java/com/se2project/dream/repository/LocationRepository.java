package com.se2project.dream.repository;

import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long>{
}
