package com.siakad.controller;

import com.siakad.model.*;
import com.siakad.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tugas")
public class TugasController {

    private final TugasRepository tugasRepository;
    private final PengumpulanTugasRepository pengumpulanTugasRepository;
    private final JadwalKuliahRepository jadwalKuliahRepository;
    private final MahasiswaRepository mahasiswaRepository;
    private final DosenRepository dosenRepository;
    private final KRSRepository krsRepository;

    public TugasController(TugasRepository tugasRepository, PengumpulanTugasRepository pengumpulanTugasRepository, 
                           JadwalKuliahRepository jadwalKuliahRepository, MahasiswaRepository mahasiswaRepository, 
                           DosenRepository dosenRepository, KRSRepository krsRepository) {
        this.tugasRepository = tugasRepository;
        this.pengumpulanTugasRepository = pengumpulanTugasRepository;
        this.jadwalKuliahRepository = jadwalKuliahRepository;
        this.mahasiswaRepository = mahasiswaRepository;
        this.dosenRepository = dosenRepository;
        this.krsRepository = krsRepository;
    }

    private String getRole(Authentication authentication) {
        return authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
    }

    @GetMapping
    public String index(Model model, Authentication authentication) {
        String role = getRole(authentication);
        model.addAttribute("activePage", "tugas");
        model.addAttribute("userRole", role);

        if ("DOSEN".equals(role)) {
            Dosen dsn = dosenRepository.findByEmail(authentication.getName()).orElse(null);
            if (dsn != null) {
                List<JadwalKuliah> jadwalList = jadwalKuliahRepository.findByDosen(dsn);
                List<Tugas> semuaTugas = new ArrayList<>();
                for (JadwalKuliah jk : jadwalList) {
                    semuaTugas.addAll(tugasRepository.findByJadwalKuliahOrderByDeadlineAsc(jk));
                }
                model.addAttribute("tugasList", semuaTugas);
                model.addAttribute("jadwalList", jadwalList);
            }
        } else if ("MAHASISWA".equals(role)) {
            Mahasiswa mhs = mahasiswaRepository.findByEmail(authentication.getName()).orElse(null);
            if (mhs != null) {
                List<KRS> krsList = krsRepository.findByMahasiswa(mhs);
                List<Tugas> tugasTersedia = new ArrayList<>();
                for (KRS krs : krsList) {
                    tugasTersedia.addAll(tugasRepository.findByJadwalKuliahOrderByDeadlineAsc(krs.getJadwalKuliah()));
                }
                model.addAttribute("tugasList", tugasTersedia);
                model.addAttribute("mahasiswaId", mhs.getId());
            }
        }
        
        return "tugas/index";
    }

    @PostMapping("/create")
    public String createTugas(@ModelAttribute Tugas tugas, @RequestParam Long jadwalKuliahId, Authentication authentication, RedirectAttributes redirectAttributes) {
        if ("DOSEN".equals(getRole(authentication))) {
            jadwalKuliahRepository.findById(jadwalKuliahId).ifPresent(jk -> {
                tugas.setJadwalKuliah(jk);
                tugasRepository.save(tugas);
                redirectAttributes.addFlashAttribute("successMessage", "Tugas berhasil dibuat!");
            });
        }
        return "redirect:/tugas";
    }

    @PostMapping("/submit")
    public String submitTugas(@RequestParam Long tugasId, @RequestParam String jawaban, Authentication authentication, RedirectAttributes redirectAttributes) {
        if ("MAHASISWA".equals(getRole(authentication))) {
            Mahasiswa mhs = mahasiswaRepository.findByEmail(authentication.getName()).orElse(null);
            if (mhs != null) {
                tugasRepository.findById(tugasId).ifPresent(t -> {
                    PengumpulanTugas pt = pengumpulanTugasRepository.findByTugasAndMahasiswa(t, mhs).orElse(new PengumpulanTugas());
                    pt.setTugas(t);
                    pt.setMahasiswa(mhs);
                    pt.setJawaban(jawaban);
                    pengumpulanTugasRepository.save(pt);
                    redirectAttributes.addFlashAttribute("successMessage", "Jawaban berhasil dikumpulkan!");
                });
            }
        }
        return "redirect:/tugas";
    }

    @GetMapping("/{id}/submissions")
    public String viewSubmissions(@PathVariable Long id, Model model, Authentication authentication) {
        if ("DOSEN".equals(getRole(authentication))) {
            tugasRepository.findById(id).ifPresent(t -> {
                model.addAttribute("tugas", t);
                model.addAttribute("pengumpulanList", pengumpulanTugasRepository.findByTugas(t));
                model.addAttribute("activePage", "tugas");
            });
            return "tugas/submissions";
        }
        return "redirect:/tugas";
    }

    @PostMapping("/nilai")
    public String beriNilai(@RequestParam Long pengumpulanId, @RequestParam Double nilai, Authentication authentication, RedirectAttributes redirectAttributes) {
        if ("DOSEN".equals(getRole(authentication))) {
            pengumpulanTugasRepository.findById(pengumpulanId).ifPresent(pt -> {
                pt.setNilai(nilai);
                pengumpulanTugasRepository.save(pt);
                redirectAttributes.addFlashAttribute("successMessage", "Nilai berhasil diberikan!");
            });
        }
        return "redirect:/tugas";
    }
}
