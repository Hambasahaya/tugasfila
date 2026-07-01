package com.siakad.model;

import jakarta.persistence.*;

@Entity
@Table(name = "krs")
public class KRS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private Mahasiswa mahasiswa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jadwal_kuliah_id", nullable = false)
    private JadwalKuliah jadwalKuliah;

    @Column(name = "tahun_ajaran", length = 20)
    private String tahunAjaran;

    // Grades (KHS)
    @Column(name = "nilai_angka")
    private Double nilaiAngka;

    @Column(name = "nilai_huruf", length = 2)
    private String nilaiHuruf;

    // Attendance
    @Column(name = "jumlah_hadir", columnDefinition = "integer default 0")
    private Integer jumlahHadir = 0;

    @Column(name = "jumlah_izin", columnDefinition = "integer default 0")
    private Integer jumlahIzin = 0;

    @Column(name = "jumlah_sakit", columnDefinition = "integer default 0")
    private Integer jumlahSakit = 0;

    @Column(name = "jumlah_alpa", columnDefinition = "integer default 0")
    private Integer jumlahAlpa = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(Mahasiswa mahasiswa) { this.mahasiswa = mahasiswa; }
    
    public JadwalKuliah getJadwalKuliah() { return jadwalKuliah; }
    public void setJadwalKuliah(JadwalKuliah jadwalKuliah) { this.jadwalKuliah = jadwalKuliah; }
    
    public String getTahunAjaran() { return tahunAjaran; }
    public void setTahunAjaran(String tahunAjaran) { this.tahunAjaran = tahunAjaran; }
    
    public Double getNilaiAngka() { return nilaiAngka; }
    public void setNilaiAngka(Double nilaiAngka) { this.nilaiAngka = nilaiAngka; }
    
    public String getNilaiHuruf() { return nilaiHuruf; }
    public void setNilaiHuruf(String nilaiHuruf) { this.nilaiHuruf = nilaiHuruf; }
    
    public Integer getJumlahHadir() { return jumlahHadir; }
    public void setJumlahHadir(Integer jumlahHadir) { this.jumlahHadir = jumlahHadir; }
    
    public Integer getJumlahIzin() { return jumlahIzin; }
    public void setJumlahIzin(Integer jumlahIzin) { this.jumlahIzin = jumlahIzin; }
    
    public Integer getJumlahSakit() { return jumlahSakit; }
    public void setJumlahSakit(Integer jumlahSakit) { this.jumlahSakit = jumlahSakit; }
    
    public Integer getJumlahAlpa() { return jumlahAlpa; }
    public void setJumlahAlpa(Integer jumlahAlpa) { this.jumlahAlpa = jumlahAlpa; }
    
    // Helper method to calculate attendance percentage
    public double getPersentaseKehadiran() {
        if (jadwalKuliah == null || jadwalKuliah.getTotalPertemuan() == null || jadwalKuliah.getTotalPertemuan() == 0) {
            return 0.0;
        }
        return ((double) (jumlahHadir + jumlahIzin + jumlahSakit) / jadwalKuliah.getTotalPertemuan()) * 100;
    }
    
    public boolean isAbsensiMemenuhi() {
        return getPersentaseKehadiran() >= 75.0;
    }
}
