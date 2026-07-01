package com.siakad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "mata_kuliah")
public class MataKuliah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Kode wajib diisi")
    @Size(max = 20)
    @Column(nullable = false, unique = true, length = 20)
    private String kode;

    @NotBlank(message = "Nama wajib diisi")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nama;

    @NotNull(message = "SKS wajib diisi")
    @Column(nullable = false)
    private Integer sks;

    @NotNull(message = "Semester wajib diisi")
    @Column(nullable = false)
    private Integer semester;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }
    
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public Integer getSks() { return sks; }
    public void setSks(Integer sks) { this.sks = sks; }
    
    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }
}
