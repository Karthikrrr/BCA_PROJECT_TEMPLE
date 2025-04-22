package com.business_website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.business_website.models.PlacementDetails;

@Repository
public interface PlacementsDetailsRepo extends JpaRepository<PlacementDetails, Long>{

    @Query(value = "SELECT * FROM usersdetails ORDER BY created_at DESC", nativeQuery = true)
    List<PlacementDetails> findAllByOrderByCreatedDateDesc();
}
