package com.siakad.controller;

import com.siakad.model.KRS;
import com.siakad.model.Mahasiswa;
import com.siakad.model.JadwalKuliah;
import com.siakad.repository.KRSRepository;
import com.siakad.repository.JadwalKuliahRepository;
import com.siakad.repository.MahasiswaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/akademik")
public class AkademikController {

    private final KRSRepository krsRepository;
    private final JadwalKuliahRepository jadwalKuliahRepository;
    private final MahasiswaRepository mahasiswaRepository;

    public AkademikController(KRSRepository krsRepository, JadwalKuliahRepository jadwalKuliahRepository, MahasiswaRepository mahasiswaRepository) {
        this.krsRepository = krsRepository;
        this.jadwalKuliahRepository = jadwalKuliahRepository;
        this.mahasiswaRepository = mahasiswaRepository;
    }

    private Mahasiswa getTargetMahasiswa(Authentication authentication, Long mahasiswaId) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals("ROLE_ADMIN") && mahasiswaId != null) {
            return mahasiswaRepository.findById(mahasiswaId).orElse(null);
        } else if (role.equals("ROLE_MAHASISWA")) {
            return mahasiswaRepository.findByEmail(authentication.getName()).orElse(null);
        }
        return null;
    }

    // --- KRS & Jadwal Kuliah ---
    @GetMapping("/krs")
    public String krsIndex(@RequestParam(required = false) Long mahasiswaId, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        
        // If Admin but no mahasiswa selected, redirect to mahasiswa list
        if (role.equals("ROLE_ADMIN") && mahasiswaId == null) {
            redirectAttributes.addFlashAttribute("error", "Pilih mahasiswa terlebih dahulu untuk mengisi KRS.");
            return "redirect:/mahasiswa";
        }

        Mahasiswa mhs = getTargetMahasiswa(authentication, mahasiswaId);
        if (mhs == null) return "redirect:/dashboard";

        List<KRS> krsList = krsRepository.findByMahasiswa(mhs);
        List<JadwalKuliah> jadwalTersedia = jadwalKuliahRepository.findAll();
        
        // Remove already enrolled classes from available
        List<Long> enrolledJadwalIds = krsList.stream().map(k -> k.getJadwalKuliah().getId()).toList();
        jadwalTersedia.removeIf(j -> enrolledJadwalIds.contains(j.getId()));

        model.addAttribute("targetMahasiswa", mhs);
        model.addAttribute("krsList", krsList);
        model.addAttribute("jadwalTersedia", jadwalTersedia);
        model.addAttribute("activePage", "krs");
        return "akademik/krs";
    }

    @PostMapping("/krs/ambil")
    public String ambilKrs(@RequestParam Long jadwalId, @RequestParam(required = false) Long mahasiswaId, Authentication authentication, RedirectAttributes redirectAttributes) {
        Mahasiswa mhs = getTargetMahasiswa(authentication, mahasiswaId);
        if (mhs != null) {
            jadwalKuliahRepository.findById(jadwalId).ifPresent(jadwal -> {
                KRS krs = new KRS();
                krs.setMahasiswa(mhs);
                krs.setJadwalKuliah(jadwal);
                krs.setTahunAjaran("2024/2025");
                krsRepository.save(krs);
                redirectAttributes.addFlashAttribute("successMessage", "Berhasil mengambil mata kuliah " + jadwal.getMataKuliah().getNama());
            });
        }
        
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals("ROLE_ADMIN") && mahasiswaId != null) {
            return "redirect:/akademik/krs?mahasiswaId=" + mahasiswaId;
        }
        return "redirect:/akademik/krs";
    }
    
    @PostMapping("/krs/batal")
    public String batalKrs(@RequestParam Long krsId, @RequestParam(required = false) Long mahasiswaId, Authentication authentication, RedirectAttributes redirectAttributes) {
        Mahasiswa mhs = getTargetMahasiswa(authentication, mahasiswaId);
        if (mhs != null) {
            krsRepository.findById(krsId).ifPresent(krs -> {
                if (krs.getMahasiswa().getId().equals(mhs.getId())) {
                    krsRepository.delete(krs);
                    redirectAttributes.addFlashAttribute("successMessage", "Berhasil membatalkan mata kuliah");
                }
            });
        }
        
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals("ROLE_ADMIN") && mahasiswaId != null) {
            return "redirect:/akademik/krs?mahasiswaId=" + mahasiswaId;
        }
        return "redirect:/akademik/krs";
    }

    // --- KHS / Transkrip ---
    @GetMapping("/khs")
    public String khsIndex(@RequestParam(required = false) Long mahasiswaId, Model model, Authentication authentication) {
        Mahasiswa mhs = getTargetMahasiswa(authentication, mahasiswaId);
        if (mhs == null) return "redirect:/dashboard";

        List<KRS> krsList = krsRepository.findByMahasiswa(mhs);
        model.addAttribute("krsList", krsList);
        model.addAttribute("activePage", "khs");
        return "akademik/khs";
    }
}
