package com.ashergadiel.manage.repository;

import com.ashergadiel.manage.domain.TypeProduit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeProduit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeProduitRepository extends JpaRepository<TypeProduit, Long> {}
