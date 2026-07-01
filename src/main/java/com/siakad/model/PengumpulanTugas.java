package com.siakad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "pengumpulan_tugas")
public class PengumpulanTugas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tugas_id", nullable = false)
    private Tugas tugas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private Mahasiswa mahasiswa;

    @NotBlank(message = "Jawaban wajib diisi")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String jawaban;

    @Column(name = "waktu_kumpul", nullable = false)
    private LocalDateTime waktuKumpul;

    @Column(name = "nilai")
    private Double nilai;

    @PrePersist
    void prePersist() {
        waktuKumpul = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Tugas getTugas() { return tugas; }
    public void setTugas(Tugas tugas) { this.tugas = tugas; }
    
    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(Mahasiswa mahasiswa) { this.mahasiswa = mahasiswa; }
    
    public String getJawaban() { return jawaban; }
    public void setJawaban(String jawaban) { this.jawaban = jawaban; }
    
    public LocalDateTime getWaktuKumpul() { return waktuKumpul; }
    public void setWaktuKumpul(LocalDateTime waktuKumpul) { this.waktuKumpul = waktuKumpul; }
    
    public Double getNilai() { return nilai; }
    public void setNilai(Double nilai) { this.nilai = nilai; }
}
