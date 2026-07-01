package com.siakad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "tugas")
public class Tugas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jadwal_kuliah_id", nullable = false)
    private JadwalKuliah jadwalKuliah;

    @NotBlank(message = "Judul tugas wajib diisi")
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String judul;

    @NotBlank(message = "Deskripsi wajib diisi")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String deskripsi;

    @NotNull(message = "Tenggat waktu (deadline) wajib diisi")
    @Column(nullable = false)
    private LocalDateTime deadline;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public JadwalKuliah getJadwalKuliah() { return jadwalKuliah; }
    public void setJadwalKuliah(JadwalKuliah jadwalKuliah) { this.jadwalKuliah = jadwalKuliah; }
    
    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }
    
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    
    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
}
