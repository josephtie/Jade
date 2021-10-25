package com.ashergadiel.manage.repository;

import com.ashergadiel.manage.domain.DocumentSortie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DocumentSortie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentSortieRepository extends JpaRepository<DocumentSortie, Long> {}
