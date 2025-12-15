package com.findyourpet.findyourpet.repository;

import com.findyourpet.findyourpet.model.PetPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetPostRepository extends JpaRepository<PetPost, Long> {
    List<PetPost> findByApprovedFalse();
    List<PetPost> findByApprovedTrue();
    List<PetPost> findByApprovedIsNull();
}
