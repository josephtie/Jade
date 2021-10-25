package com.ashergadiel.manage.repository;

import com.ashergadiel.manage.domain.DetailDocAch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DetailDocAch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailDocAchRepository extends JpaRepository<DetailDocAch, Long> {}
