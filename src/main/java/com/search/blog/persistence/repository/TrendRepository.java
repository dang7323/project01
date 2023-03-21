package com.search.blog.persistence.repository;

import com.search.blog.persistence.Entity.TrendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.search.blog.code.Constants.RANK_LIMIT;

@Repository
public interface TrendRepository extends JpaRepository<TrendEntity, Long> {
    @Query(value = "select keyword, count(keyword) as count FROM trend group by keyword order by count(keyword) desc limit " + RANK_LIMIT, nativeQuery = true)
    List<TrendCriteria> countSearchTrend();
}