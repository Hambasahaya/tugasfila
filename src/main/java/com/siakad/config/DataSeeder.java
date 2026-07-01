package com.siakad.config;

import com.siakad.model.Dosen;
import com.siakad.model.Mahasiswa;
import com.siakad.repository.DosenRepository;
import com.siakad.repository.MahasiswaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(MahasiswaRepository mahasiswaRepository, DosenRepository dosenRepository,
                               com.siakad.repository.MataKuliahRepository mataKuliahRepository,
                               com.siakad.repository.JadwalKuliahRepository jadwalKuliahRepository,
                               com.siakad.repository.KRSRepository krsRepository) {
        return args -> {
            Mahasiswa mhs1 = null;
            if (mahasiswaRepository.count() == 0) {
                mhs1 = mahasiswaRepository.save(mahasiswa("2401001", "Fila Aulia", "Teknik Informatika", "2024", "fila.aulia@student.ac.id"));
                mahasiswaRepository.save(mahasiswa("2401002", "Raka Pratama", "Sistem Informasi", "2024", "raka.pratama@student.ac.id"));
                mahasiswaRepository.save(mahasiswa("2301003", "Nadia Putri", "Teknik Informatika", "2023", "nadia.putri@student.ac.id"));
                mahasiswaRepository.save(mahasiswa("2201004", "Bima Saputra", "Manajemen Informatika", "2022", "bima.saputra@student.ac.id"));
            } else {
                mhs1 = mahasiswaRepository.findByNim("2401001").orElse(null);
            }

            Dosen dsn1 = null;
            if (dosenRepository.count() == 0) {
                dsn1 = dosenRepository.save(dosen("0011028801", "Dr. Andi Wijaya", "Pemrograman Web", "Lektor", "andi.wijaya@kampus.ac.id"));
                dosenRepository.save(dosen("0022059002", "Siti Rahma, M.Kom", "Basis Data", "Asisten Ahli", "siti.rahma@kampus.ac.id"));
                dosenRepository.save(dosen("0030088703", "Dewi Lestari, M.T", "Rekayasa Perangkat Lunak", "Lektor", "dewi.lestari@kampus.ac.id"));
            } else {
                dsn1 = dosenRepository.findByNidn("0011028801").orElse(null);
            }
            
            if (mataKuliahRepository.count() == 0 && dsn1 != null && mhs1 != null) {
                com.siakad.model.MataKuliah mk1 = new com.siakad.model.MataKuliah();
                mk1.setKode("IF101");
                mk1.setNama("Pemrograman Web Lanjut");
                mk1.setSks(3);
                mk1.setSemester(3);
                mk1 = mataKuliahRepository.save(mk1);
                
                com.siakad.model.JadwalKuliah jk1 = new com.siakad.model.JadwalKuliah();
                jk1.setMataKuliah(mk1);
                jk1.setDosen(dsn1);
                jk1.setHari("Senin");
                jk1.setJam("08:00 - 10:30");
                jk1.setRuangan("Lab Komputer 1");
                jk1.setTotalPertemuan(14);
                jk1 = jadwalKuliahRepository.save(jk1);
                
                com.siakad.model.KRS krs1 = new com.siakad.model.KRS();
                krs1.setMahasiswa(mhs1);
                krs1.setJadwalKuliah(jk1);
                krs1.setTahunAjaran("2024/2025");
                krsRepository.save(krs1);
            }
        };
    }

    private Mahasiswa mahasiswa(String nim, String nama, String programStudi, String angkatan, String email) {
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setNim(nim);
        mahasiswa.setNama(nama);
        mahasiswa.setProgramStudi(programStudi);
        mahasiswa.setAngkatan(angkatan);
        mahasiswa.setEmail(email);
        mahasiswa.setNoHp("08" + nim);
        mahasiswa.setAlamat("Kota Bandung");
        return mahasiswa;
    }

    private Dosen dosen(String nidn, String nama, String keahlian, String jabatan, String email) {
        Dosen dosen = new Dosen();
        dosen.setNidn(nidn);
        dosen.setNama(nama);
        dosen.setKeahlian(keahlian);
        dosen.setJabatan(jabatan);
        dosen.setEmail(email);
        dosen.setNoHp("08" + nidn.substring(0, 10));
        return dosen;
    }
}
