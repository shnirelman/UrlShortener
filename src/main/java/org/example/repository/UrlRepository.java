package org.example.repository;

import org.example.repository.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    @Query
    Optional<UrlEntity> findByLongForm(String longForm);
    @Query
    Optional<UrlEntity> findByShortForm(String shortForm);
}
