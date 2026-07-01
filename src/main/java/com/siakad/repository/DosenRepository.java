package com.siakad.repository;

import com.siakad.model.Dosen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DosenRepository extends JpaRepository<Dosen, Long> {

    Page<Dosen> findByNamaContainingIgnoreCaseOrNidnContainingIgnoreCaseOrKeahlianContainingIgnoreCase(
            String nama,
            String nidn,
            String keahlian,
            Pageable pageable
    );

    Optional<Dosen> findByNidn(String nidn);
    
    boolean existsByNidn(String nidn);
    
    Optional<Dosen> findByEmail(String email);
}
