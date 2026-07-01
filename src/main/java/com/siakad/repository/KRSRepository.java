package com.siakad.repository;

import com.siakad.model.KRS;
import com.siakad.model.Mahasiswa;
import com.siakad.model.JadwalKuliah;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface KRSRepository extends JpaRepository<KRS, Long> {
    List<KRS> findByMahasiswa(Mahasiswa mahasiswa);
    List<KRS> findByJadwalKuliah(JadwalKuliah jadwalKuliah);
    Optional<KRS> findByMahasiswaAndJadwalKuliah(Mahasiswa mahasiswa, JadwalKuliah jadwalKuliah);
}
