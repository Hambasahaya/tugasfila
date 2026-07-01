package com.siakad.controller;

import com.siakad.model.Pengumuman;
import com.siakad.service.PengumumanService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pengumuman")
public class PengumumanController {

    private final PengumumanService pengumumanService;

    public PengumumanController(PengumumanService pengumumanService) {
        this.pengumumanService = pengumumanService;
    }

    @GetMapping
    public String index(Model model, Authentication authentication) {
        String role = "ALL";
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String authorityRole = authority.getAuthority().replace("ROLE_", "");
            if ("ADMIN".equals(authorityRole) || "DOSEN".equals(authorityRole) || "MAHASISWA".equals(authorityRole)) {
                role = authorityRole;
                break;
            }
        }

        model.addAttribute("pengumumanList", pengumumanService.findForRole(role));
        model.addAttribute("activePage", "pengumuman");
        model.addAttribute("userRole", role);
        return "pengumuman/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pengumuman", new Pengumuman());
        model.addAttribute("activePage", "pengumuman");
        return "pengumuman/form";
    }

    @PostMapping("/create")
    public String createSubmit(@Valid @ModelAttribute("pengumuman") Pengumuman pengumuman,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("activePage", "pengumuman");
            return "pengumuman/form";
        }
        
        pengumumanService.save(pengumuman);
        redirectAttributes.addFlashAttribute("successMessage", "Pengumuman berhasil ditambahkan!");
        return "redirect:/pengumuman";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return pengumumanService.findById(id)
                .map(pengumuman -> {
                    model.addAttribute("pengumuman", pengumuman);
                    model.addAttribute("activePage", "pengumuman");
                    return "pengumuman/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Pengumuman tidak ditemukan!");
                    return "redirect:/pengumuman";
                });
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable Long id,
                             @Valid @ModelAttribute("pengumuman") Pengumuman pengumuman,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("activePage", "pengumuman");
            return "pengumuman/form";
        }
        
        pengumuman.setId(id);
        pengumumanService.save(pengumuman);
        redirectAttributes.addFlashAttribute("successMessage", "Pengumuman berhasil diperbarui!");
        return "redirect:/pengumuman";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pengumumanService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Pengumuman berhasil dihapus!");
        return "redirect:/pengumuman";
    }
}
