package org.example.repository;

import org.example.repository.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    @Query
    Optional<UrlEntity> findByLongForm(String longForm);
    @Query
    Optional<UrlEntity> findByShortForm(String shortForm);

    @Query(nativeQuery = true,
           value = "SELECT id FROM urls")
    List<Long> findAll(Instant updatedDate);

    @Query(nativeQuery = true,
            value = "SELECT id FROM urls WHERE (urls.used_at < :updated_time)")
//            value = "SELECT id FROM urls WHERE (urls.updated_at < cast(:updated_time as timestamp))")
    List<Long> findIdsByUsedAtLessThan(@Param("updated_time") Instant updatedTime);

//    @Query
//    List<Long> getIdsByUsedAtLessThan(Instant usedAt);

    @Query(nativeQuery = true,
            value = "SELECT id FROM urls WHERE urls.id = :id")
    List<Long> findAllTest(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE urls SET used_at = :updated_time WHERE urls.id = :id")
    void updateUsedTime(@Param("id") Long id, @Param("updated_time") Instant updatedTime);
}
