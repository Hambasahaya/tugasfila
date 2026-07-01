package com.siakad.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "mahasiswa")
public class Mahasiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "NIM wajib diisi")
    @Size(max = 20, message = "NIM maksimal 20 karakter")
    @Column(nullable = false, unique = true, length = 20)
    private String nim;

    @NotBlank(message = "Nama wajib diisi")
    @Size(max = 120, message = "Nama maksimal 120 karakter")
    @Column(nullable = false, length = 120)
    private String nama;

    @NotBlank(message = "Program studi wajib diisi")
    @Size(max = 80, message = "Program studi maksimal 80 karakter")
    @Column(nullable = false, length = 80)
    private String programStudi;

    @NotBlank(message = "Angkatan wajib diisi")
    @Pattern(regexp = "\\d{4}", message = "Angkatan harus 4 digit tahun")
    @Column(nullable = false, length = 4)
    private String angkatan;

    @Email(message = "Format email tidak valid")
    @Size(max = 120, message = "Email maksimal 120 karakter")
    @Column(length = 120)
    private String email;

    @Size(max = 24, message = "Nomor HP maksimal 24 karakter")
    @Column(length = 24)
    private String noHp;

    @Size(max = 255, message = "Alamat maksimal 255 karakter")
    private String alamat;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProgramStudi() {
        return programStudi;
    }

    public void setProgramStudi(String programStudi) {
        this.programStudi = programStudi;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
