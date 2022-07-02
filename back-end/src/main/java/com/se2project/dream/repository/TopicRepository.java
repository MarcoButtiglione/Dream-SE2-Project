package com.se2project.dream.repository;

import com.se2project.dream.entity.Knowledge;
import com.se2project.dream.entity.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository  extends CrudRepository<Topic, Long> {
    @Query(value = "SELECT * FROM topic  ORDER BY date desc", nativeQuery = true)
    Iterable<Topic> findAllTopic();

    @Query(value = "SELECT * FROM topic WHERE farmer_fk = ?1 ORDER BY date  desc", nativeQuery = true)
    Iterable<Topic> findByFarmer(Long farmerId);

    @Query(value = "SELECT * FROM topic WHERE farmer_fk= ?2 and tag like '%' ?1 '%' ORDER BY date desc", nativeQuery = true)
    Iterable<Topic> findByTag(String tag, Long farmerId);

    @Query(value = "SELECT * FROM topic WHERE  farmer_fk= ?2 and date= '' ?1 '' ", nativeQuery = true)
    Iterable<Topic> findByDateF(String date, Long farmerId);

    @Query(value = "SELECT * FROM topic WHERE date= '' ?1 '' ", nativeQuery = true)
    Iterable<Topic> findByDate(String date);


    @Query(value = "SELECT * FROM topic WHERE topic like '%' ?1 '%' or description like '%' ?1 '%' or tag like '%' ?1 '%' ORDER BY date desc", nativeQuery = true)
    Iterable<Topic> findByWord(String word);

    @Query(value = "SELECT * FROM topic WHERE farmer_fk= ?2 and (topic like '%' ?1 '%' or description like '%' ?1 '%' or tag like '%' ?1 '%' ) ORDER BY date desc", nativeQuery = true)
    Iterable<Topic> findByWordF(String word, Long farmerId);
}