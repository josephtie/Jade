package com.ashergadiel.manage.repository;

import com.ashergadiel.manage.domain.DocumentVente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DocumentVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentVenteRepository extends JpaRepository<DocumentVente, Long> {}
