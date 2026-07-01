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
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "dosen")
public class Dosen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "NIDN wajib diisi")
    @Size(max = 20, message = "NIDN maksimal 20 karakter")
    @Column(nullable = false, unique = true, length = 20)
    private String nidn;

    @NotBlank(message = "Nama wajib diisi")
    @Size(max = 120, message = "Nama maksimal 120 karakter")
    @Column(nullable = false, length = 120)
    private String nama;

    @NotBlank(message = "Keahlian wajib diisi")
    @Size(max = 100, message = "Keahlian maksimal 100 karakter")
    @Column(nullable = false, length = 100)
    private String keahlian;

    @NotBlank(message = "Jabatan wajib diisi")
    @Size(max = 80, message = "Jabatan maksimal 80 karakter")
    @Column(nullable = false, length = 80)
    private String jabatan;

    @Email(message = "Format email tidak valid")
    @Size(max = 120, message = "Email maksimal 120 karakter")
    @Column(length = 120)
    private String email;

    @Size(max = 24, message = "Nomor HP maksimal 24 karakter")
    @Column(length = 24)
    private String noHp;

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

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
