package com.siakad.repository;

import com.siakad.model.JadwalKuliah;
import com.siakad.model.Dosen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JadwalKuliahRepository extends JpaRepository<JadwalKuliah, Long> {
    List<JadwalKuliah> findByDosen(Dosen dosen);
}
