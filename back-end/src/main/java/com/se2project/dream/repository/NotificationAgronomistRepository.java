package com.se2project.dream.repository;

import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.NotificationAgronomist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NotificationAgronomistRepository extends CrudRepository<NotificationAgronomist, Long> {
    @Query(value = "SELECT * FROM notification_agronomist WHERE agronomist_fk=?1 ORDER BY date desc", nativeQuery = true)
    Iterable<NotificationAgronomist> findAllNotification(Long agronomistId);
}
