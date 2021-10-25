package com.ashergadiel.manage.repository;

import com.ashergadiel.manage.domain.DetailDocVte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DetailDocVte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailDocVteRepository extends JpaRepository<DetailDocVte, Long> {}
