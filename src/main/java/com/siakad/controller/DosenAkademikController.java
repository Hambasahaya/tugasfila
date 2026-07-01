package com.siakad.controller;

import com.siakad.model.KRS;
import com.siakad.model.Dosen;
import com.siakad.model.JadwalKuliah;
import com.siakad.repository.KRSRepository;
import com.siakad.repository.JadwalKuliahRepository;
import com.siakad.repository.DosenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dosen-akademik")
public class DosenAkademikController {

    private final KRSRepository krsRepository;
    private final JadwalKuliahRepository jadwalKuliahRepository;
    private final DosenRepository dosenRepository;

    public DosenAkademikController(KRSRepository krsRepository, JadwalKuliahRepository jadwalKuliahRepository, DosenRepository dosenRepository) {
        this.krsRepository = krsRepository;
        this.jadwalKuliahRepository = jadwalKuliahRepository;
        this.dosenRepository = dosenRepository;
    }

    private Dosen getLoggedInDosen(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals("ROLE_DOSEN")) {
            return dosenRepository.findByEmail(authentication.getName()).orElse(null);
        }
        return null;
    }

    @GetMapping("/kelas")
    public String kelasIndex(Model model, Authentication authentication) {
        Dosen dsn = getLoggedInDosen(authentication);
        if (dsn == null) return "redirect:/dashboard";

        List<JadwalKuliah> jadwalList = jadwalKuliahRepository.findByDosen(dsn);
        model.addAttribute("jadwalList", jadwalList);
        model.addAttribute("activePage", "kelas_dosen");
        return "dosen_akademik/kelas";
    }

    @GetMapping("/kelas/{jadwalId}/mahasiswa")
    public String detailKelas(@PathVariable Long jadwalId, Model model, Authentication authentication) {
        Dosen dsn = getLoggedInDosen(authentication);
        if (dsn == null) return "redirect:/dashboard";

        JadwalKuliah jadwal = jadwalKuliahRepository.findById(jadwalId).orElse(null);
        if (jadwal == null || !jadwal.getDosen().getId().equals(dsn.getId())) return "redirect:/dosen-akademik/kelas";

        List<KRS> krsList = krsRepository.findByJadwalKuliah(jadwal);
        model.addAttribute("jadwal", jadwal);
        model.addAttribute("krsList", krsList);
        model.addAttribute("activePage", "kelas_dosen");
        return "dosen_akademik/mahasiswa";
    }

    @PostMapping("/kelas/nilai")
    public String updateNilai(@RequestParam Long krsId, @RequestParam Double nilaiAngka, @RequestParam String nilaiHuruf, 
                              @RequestParam Integer jumlahHadir, @RequestParam Integer jumlahIzin, 
                              @RequestParam Integer jumlahSakit, @RequestParam Integer jumlahAlpa,
                              Authentication authentication, RedirectAttributes redirectAttributes) {
        Dosen dsn = getLoggedInDosen(authentication);
        if (dsn != null) {
            krsRepository.findById(krsId).ifPresent(krs -> {
                if (krs.getJadwalKuliah().getDosen().getId().equals(dsn.getId())) {
                    krs.setNilaiAngka(nilaiAngka);
                    krs.setNilaiHuruf(nilaiHuruf);
                    krs.setJumlahHadir(jumlahHadir);
                    krs.setJumlahIzin(jumlahIzin);
                    krs.setJumlahSakit(jumlahSakit);
                    krs.setJumlahAlpa(jumlahAlpa);
                    krsRepository.save(krs);
                    redirectAttributes.addFlashAttribute("successMessage", "Data berhasil diperbarui!");
                }
            });
        }
        return "redirect:/dosen-akademik/kelas"; // In a real app, redirect back to the class details
    }
}
