package com.bookmyshow.bookmyshowapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.bookmyshowapi.models.Tier;

public interface TierRepository extends JpaRepository<Tier, Long>{
	List<Tier> findAllByScreenId(Long screenId);
}
