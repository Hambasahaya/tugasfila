package com.siakad.repository;

import com.siakad.model.MataKuliah;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MataKuliahRepository extends JpaRepository<MataKuliah, Long> {
    Page<MataKuliah> findByNamaContainingIgnoreCaseOrKodeContainingIgnoreCase(String nama, String kode, Pageable pageable);
}
