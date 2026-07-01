package com.siakad.repository;

import com.siakad.model.Tugas;
import com.siakad.model.JadwalKuliah;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TugasRepository extends JpaRepository<Tugas, Long> {
    List<Tugas> findByJadwalKuliahOrderByDeadlineAsc(JadwalKuliah jadwalKuliah);
}
