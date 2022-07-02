package com.se2project.dream.repository;

import com.se2project.dream.entity.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface MeetingRepository  extends CrudRepository<Meeting, Long> {
    @Query(value = "SELECT * FROM meeting WHERE agronomist_fk=  ?1  and date= '' ?2 '' ORDER BY start_time ", nativeQuery = true)
    List<Meeting> findAllByAgronomistD(Long agronomistId, String date);

    @Query(value = "SELECT * FROM meeting WHERE farmer_fk=  ?1  and date= '' ?2 '' ORDER BY start_time ", nativeQuery = true)
    Iterable<Meeting> findAllByFarmerD(Long farmer,String date);

    @Query(value = "SELECT * FROM meeting WHERE agronomist_fk=  ?1  and date >=  ?2  ORDER BY date, start_time ", nativeQuery = true)
    Iterable<Meeting> findAllByAgronomist(Long agronomistId,LocalDate date);

    @Query(value = "SELECT * FROM meeting WHERE farmer_fk=  ?1  and date >=  ?2  ORDER BY date, start_time ", nativeQuery = true)
    Iterable<Meeting> findAllByFarmer(Long farmer,LocalDate date);

    @Query(value = "SELECT * FROM meeting WHERE agronomist_fk=  ?1  and state= 'conclused' ORDER BY date, start_time ", nativeQuery = true)
    Iterable<Meeting> findConclusedByAgronomist(Long agronomistId);

    @Query(value = "SELECT * FROM meeting WHERE farmer_fk=  ?1  and state='closed'  ORDER BY date, start_time ", nativeQuery = true)
    Iterable<Meeting> findAllClosed(Long farmer);

    @Query(value = "SELECT * FROM meeting WHERE agronomist_fk=  ?1  and date <=  ?2  ORDER BY date, start_time ", nativeQuery = true)
    Iterable<Meeting> findAllOldByAgronomist(Long agronomistId,LocalDate date);

    @Query(value = "SELECT count(*) FROM meeting WHERE farmer_fk=  ?1 and agronomist_fk= ?2 and date between ?3 and ?4 ", nativeQuery = true)
    int countMeeeting(Long farmer, Long agronomist,LocalDate start, LocalDate end);

}