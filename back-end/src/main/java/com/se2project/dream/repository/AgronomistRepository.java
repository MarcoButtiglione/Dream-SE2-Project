package com.se2project.dream.repository;

import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AgronomistRepository extends JpaRepository<Agronomist, Long> {
    @Query(value = "SELECT * FROM agronomist WHERE aadhaar=?1", nativeQuery = true)
    Agronomist findByAadhaar(String aadhaar);

    @Query(value = "SELECT * FROM agronomist WHERE email=?1", nativeQuery = true)
    Agronomist findByEmail(String email);

    @Query(value = "SELECT * FROM agronomist WHERE location_fk=?1", nativeQuery = true)
    Agronomist findByLocation(Long locationId);
}
