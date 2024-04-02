package com.truongjae.predictlove.repoitory;

import com.truongjae.predictlove.entity.Cookies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CookiesRepository extends JpaRepository<Cookies,Long> {
    List<Cookies> findAll();
}
