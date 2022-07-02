package com.se2project.dream.repository;

import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.NotificationFarmer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NotificationFarmerRepository extends CrudRepository<NotificationFarmer, Long> {
    @Query(value = "SELECT * FROM notification_farmer WHERE farmer_fk=?1 ORDER BY date desc", nativeQuery = true)
    Iterable<NotificationFarmer> findAllNotification(Long farmerId);
}
