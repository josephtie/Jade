package com.ashergadiel.manage.repository;

import com.ashergadiel.manage.domain.DetailsStock;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DetailsStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailsStockRepository extends JpaRepository<DetailsStock, Long> {}
