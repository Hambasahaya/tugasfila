package com.siakad.repository;

import com.siakad.model.Pengumuman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PengumumanRepository extends JpaRepository<Pengumuman, Long> {
    
    @Query("SELECT p FROM Pengumuman p WHERE p.targetRole = 'ALL' OR p.targetRole = :role ORDER BY p.createdAt DESC")
    List<Pengumuman> findByTargetRoleOrderByCreatedAtDesc(String role);
    
    List<Pengumuman> findAllByOrderByCreatedAtDesc();
}
