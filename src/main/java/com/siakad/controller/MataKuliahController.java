package com.siakad.controller;

import com.siakad.model.MataKuliah;
import com.siakad.repository.MataKuliahRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mata-kuliah")
public class MataKuliahController {

    private final MataKuliahRepository mataKuliahRepository;

    public MataKuliahController(MataKuliahRepository mataKuliahRepository) {
        this.mataKuliahRepository = mataKuliahRepository;
    }

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), size, Sort.by("nama").ascending());
        Page<MataKuliah> mataKuliahPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            mataKuliahPage = mataKuliahRepository.findByNamaContainingIgnoreCaseOrKodeContainingIgnoreCase(keyword, keyword, pageable);
        } else {
            mataKuliahPage = mataKuliahRepository.findAll(pageable);
        }
        
        model.addAttribute("mataKuliahPage", mataKuliahPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("activePage", "mata-kuliah");
        return "mata-kuliah/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("mataKuliah", new MataKuliah());
        model.addAttribute("pageTitle", "Tambah Mata Kuliah");
        model.addAttribute("activePage", "mata-kuliah");
        return "mata-kuliah/form";
    }

    @PostMapping
    public String store(
            @Valid @ModelAttribute MataKuliah mataKuliah,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", mataKuliah.getId() == null ? "Tambah Mata Kuliah" : "Edit Mata Kuliah");
            model.addAttribute("activePage", "mata-kuliah");
            return "mata-kuliah/form";
        }
        mataKuliahRepository.save(mataKuliah);
        redirectAttributes.addFlashAttribute("success", "Data mata kuliah berhasil disimpan.");
        return "redirect:/mata-kuliah";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("mataKuliah", mataKuliahRepository.findById(id).orElse(new MataKuliah()));
        model.addAttribute("pageTitle", "Edit Mata Kuliah");
        model.addAttribute("activePage", "mata-kuliah");
        return "mata-kuliah/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        mataKuliahRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Data mata kuliah berhasil dihapus.");
        return "redirect:/mata-kuliah";
    }
}
