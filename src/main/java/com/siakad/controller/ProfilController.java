package com.siakad.controller;

import com.siakad.repository.DosenRepository;
import com.siakad.repository.MahasiswaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profil")
public class ProfilController {

    private final MahasiswaRepository mahasiswaRepository;
    private final DosenRepository dosenRepository;

    public ProfilController(MahasiswaRepository mahasiswaRepository, DosenRepository dosenRepository) {
        this.mahasiswaRepository = mahasiswaRepository;
        this.dosenRepository = dosenRepository;
    }

    @GetMapping
    public String index(Model model, Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        model.addAttribute("activePage", "profil");
        model.addAttribute("userRole", role);
        model.addAttribute("username", username);

        if ("MAHASISWA".equals(role)) {
            mahasiswaRepository.findByEmail(username).ifPresent(mhs -> model.addAttribute("mahasiswa", mhs));
        } else if ("DOSEN".equals(role)) {
            dosenRepository.findByEmail(username).ifPresent(dsn -> model.addAttribute("dosen", dsn));
        }

        return "profil/index";
    }
}
