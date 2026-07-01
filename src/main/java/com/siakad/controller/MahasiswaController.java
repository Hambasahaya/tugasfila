package com.siakad.controller;

import com.siakad.model.Mahasiswa;
import com.siakad.service.MahasiswaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {

    private final MahasiswaService mahasiswaService;

    public MahasiswaController(MahasiswaService mahasiswaService) {
        this.mahasiswaService = mahasiswaService;
    }

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), size, Sort.by("nama").ascending());
        Page<Mahasiswa> mahasiswaPage = mahasiswaService.findAll(keyword, pageable);
        model.addAttribute("mahasiswaPage", mahasiswaPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("activePage", "mahasiswa");
        return "mahasiswa/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("mahasiswa", new Mahasiswa());
        model.addAttribute("pageTitle", "Tambah Mahasiswa");
        model.addAttribute("activePage", "mahasiswa");
        return "mahasiswa/form";
    }

    @PostMapping
    public String store(
            @Valid @ModelAttribute Mahasiswa mahasiswa,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", mahasiswa.getId() == null ? "Tambah Mahasiswa" : "Edit Mahasiswa");
            model.addAttribute("activePage", "mahasiswa");
            return "mahasiswa/form";
        }
        mahasiswaService.save(mahasiswa);
        redirectAttributes.addFlashAttribute("success", "Data mahasiswa berhasil disimpan.");
        return "redirect:/mahasiswa";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("mahasiswa", mahasiswaService.findById(id));
        model.addAttribute("pageTitle", "Edit Mahasiswa");
        model.addAttribute("activePage", "mahasiswa");
        return "mahasiswa/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        mahasiswaService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Data mahasiswa berhasil dihapus.");
        return "redirect:/mahasiswa";
    }
}
