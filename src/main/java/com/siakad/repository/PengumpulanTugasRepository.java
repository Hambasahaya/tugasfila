package com.siakad.repository;

import com.siakad.model.PengumpulanTugas;
import com.siakad.model.Tugas;
import com.siakad.model.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PengumpulanTugasRepository extends JpaRepository<PengumpulanTugas, Long> {
    List<PengumpulanTugas> findByTugas(Tugas tugas);
    Optional<PengumpulanTugas> findByTugasAndMahasiswa(Tugas tugas, Mahasiswa mahasiswa);
}
