package com.siakad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "jadwal_kuliah")
public class JadwalKuliah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mata_kuliah_id", nullable = false)
    private MataKuliah mataKuliah;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dosen_id", nullable = false)
    private Dosen dosen;

    @NotBlank(message = "Hari wajib diisi")
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String hari;

    @NotBlank(message = "Jam wajib diisi (misal: 08:00 - 10:00)")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String jam;

    @NotBlank(message = "Ruangan wajib diisi")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String ruangan;
    
    @NotNull(message = "Total pertemuan wajib diisi")
    @Column(nullable = false, columnDefinition = "integer default 14")
    private Integer totalPertemuan = 14;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public MataKuliah getMataKuliah() { return mataKuliah; }
    public void setMataKuliah(MataKuliah mataKuliah) { this.mataKuliah = mataKuliah; }
    
    public Dosen getDosen() { return dosen; }
    public void setDosen(Dosen dosen) { this.dosen = dosen; }
    
    public String getHari() { return hari; }
    public void setHari(String hari) { this.hari = hari; }
    
    public String getJam() { return jam; }
    public void setJam(String jam) { this.jam = jam; }
    
    public String getRuangan() { return ruangan; }
    public void setRuangan(String ruangan) { this.ruangan = ruangan; }
    
    public Integer getTotalPertemuan() { return totalPertemuan; }
    public void setTotalPertemuan(Integer totalPertemuan) { this.totalPertemuan = totalPertemuan; }
}
