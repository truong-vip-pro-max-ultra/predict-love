package com.truongjae.predictlove.repoitory;

import com.truongjae.predictlove.entity.PriceService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceServiceRepository extends JpaRepository<PriceService,Long> {
    PriceService findOneById(Long id);
}
