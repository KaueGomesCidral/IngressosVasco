package com.example.history_service.infrastructure.persistence;

import com.example.history_service.domain.HistoryPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataHistoryPurchaseRepository extends JpaRepository<HistoryPurchase, UUID> {
    List<HistoryPurchase> findByUserId(UUID userId);
}