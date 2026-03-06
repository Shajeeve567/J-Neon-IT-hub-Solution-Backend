package com.SE.ITHub.repository;

import com.SE.ITHub.model.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Integer> {
}
