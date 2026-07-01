package com.siakad.repository;

import com.siakad.model.Mahasiswa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Long> {

    Page<Mahasiswa> findByNamaContainingIgnoreCaseOrNimContainingIgnoreCaseOrProgramStudiContainingIgnoreCase(
            String nama,
            String nim,
            String programStudi,
            Pageable pageable
    );

    Optional<Mahasiswa> findByNim(String nim);
    
    boolean existsByNim(String nim);

    long countByProgramStudi(String programStudi);
    
    Optional<Mahasiswa> findByEmail(String email);
}
