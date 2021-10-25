package com.ashergadiel.manage.repository;

import com.ashergadiel.manage.domain.DocumentAchat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DocumentAchat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentAchatRepository extends JpaRepository<DocumentAchat, Long> {}
