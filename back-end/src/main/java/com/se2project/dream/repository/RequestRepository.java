package com.se2project.dream.repository;

import com.se2project.dream.entity.Knowledge;
import com.se2project.dream.entity.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository  extends CrudRepository<Request, Long> {
    @Query(value = "SELECT * FROM request WHERE agronomist_fk = ?1 and status='pendant' ORDER BY date desc", nativeQuery = true)
    Iterable<Request> findAllByAgronomistP(Long agronomist);
    @Query(value = "SELECT * FROM request WHERE agronomist_fk = ?1 and status='closed' ORDER BY date desc", nativeQuery = true)
    Iterable<Request> findAllByAgronomistC(Long agronomist);
    @Query(value = "SELECT * FROM request WHERE agronomist_fk = ?1 and status='noFeed' ORDER BY date desc", nativeQuery = true)
    Iterable<Request> findAllByAgronomistNF(Long agronomist);

    @Query(value = "SELECT * FROM request WHERE farmer_fk = ?1 and status='pendant' ORDER BY date desc ", nativeQuery = true)
    Iterable<Request> findAllByFarmerP(Long farmer);
    @Query(value = "SELECT * FROM request WHERE farmer_fk = ?1 and status='closed' ORDER BY date desc", nativeQuery = true)
    Iterable<Request> findAllByFarmerC(Long farmer);
    @Query(value = "SELECT * FROM request WHERE farmer_fk = ?1 and status='noFeed' ORDER BY date desc", nativeQuery = true)
    Iterable<Request> findAllByFarmerNF(Long farmer);
}
