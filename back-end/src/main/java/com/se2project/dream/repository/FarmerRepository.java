package com.se2project.dream.repository;

import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FarmerRepository extends JpaRepository<Farmer, Long>{
    @Query(value = "SELECT * FROM farmer WHERE aadhaar=?1", nativeQuery = true)
    Farmer findByAadhaar(String aadhaar);
    @Query(value = "SELECT * FROM farmer WHERE email=?1", nativeQuery = true)
    Farmer findByEmail(String email);
    }
