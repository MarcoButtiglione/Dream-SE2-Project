package com.se2project.dream.repository;

import com.se2project.dream.entity.Knowledge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

public interface KnowledgeRepository extends CrudRepository<Knowledge, Long> {
    @Query(value = "SELECT * FROM knowledge  ORDER BY date desc", nativeQuery = true)
    Iterable<Knowledge> findAllKnowledge();

    @Query(value = "SELECT * FROM knowledge WHERE agronomist_fk = ?1 ORDER BY date desc ", nativeQuery = true)
    Iterable<Knowledge> findByAgronomist(Long agronomist);

    @Query(value = "SELECT * FROM knowledge WHERE agronomist_fk= ?2 and category like '%' ?1 '%' ORDER BY date desc", nativeQuery = true)
    Iterable<Knowledge> findByCategory(String category, Long agronomist);

    @Query(value = "SELECT * FROM knowledge WHERE agronomist_fk= ?2 and title like '%' ?1 '%' ORDER BY date desc", nativeQuery = true)
    Iterable<Knowledge> findByTitle(String title, Long agronomist);

    @Query(value = "SELECT * FROM knowledge WHERE title like '%' ?1 '%' or description like '%' ?1 '%' or article like '%' ?1 '%' or category like '%' ?1 '%' ORDER BY likes", nativeQuery = true)
    Iterable<Knowledge> findByWordF(String word);

    @Query(value = "SELECT * FROM knowledge WHERE agronomist_fk= ?2 and (title like '%' ?1 '%' or description like '%' ?1 '%' or article like '%' ?1 '%' or category like '%' ?1 '%') ORDER BY date desc ", nativeQuery = true)
    Iterable<Knowledge> findByWordA(String word, Long agronomist);
}
