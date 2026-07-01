package com.siakad.controller;

import com.siakad.model.JadwalKuliah;
import com.siakad.repository.DosenRepository;
import com.siakad.repository.JadwalKuliahRepository;
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
@RequestMapping("/jadwal-kuliah")
public class JadwalKuliahController {

    private final JadwalKuliahRepository jadwalKuliahRepository;
    private final MataKuliahRepository mataKuliahRepository;
    private final DosenRepository dosenRepository;

    public JadwalKuliahController(JadwalKuliahRepository jadwalKuliahRepository, 
                                  MataKuliahRepository mataKuliahRepository, 
                                  DosenRepository dosenRepository) {
        this.jadwalKuliahRepository = jadwalKuliahRepository;
        this.mataKuliahRepository = mataKuliahRepository;
        this.dosenRepository = dosenRepository;
    }

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), size, Sort.by("id").descending());
        Page<JadwalKuliah> jadwalKuliahPage = jadwalKuliahRepository.findAll(pageable);
        
        model.addAttribute("jadwalKuliahPage", jadwalKuliahPage);
        model.addAttribute("activePage", "jadwal-kuliah");
        return "jadwal-kuliah/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("jadwalKuliah", new JadwalKuliah());
        model.addAttribute("mataKuliahList", mataKuliahRepository.findAll(Sort.by("nama").ascending()));
        model.addAttribute("dosenList", dosenRepository.findAll(Sort.by("nama").ascending()));
        model.addAttribute("pageTitle", "Tambah Jadwal Kuliah");
        model.addAttribute("activePage", "jadwal-kuliah");
        return "jadwal-kuliah/form";
    }

    @PostMapping
    public String store(
            @Valid @ModelAttribute JadwalKuliah jadwalKuliah,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mataKuliahList", mataKuliahRepository.findAll(Sort.by("nama").ascending()));
            model.addAttribute("dosenList", dosenRepository.findAll(Sort.by("nama").ascending()));
            model.addAttribute("pageTitle", jadwalKuliah.getId() == null ? "Tambah Jadwal Kuliah" : "Edit Jadwal Kuliah");
            model.addAttribute("activePage", "jadwal-kuliah");
            return "jadwal-kuliah/form";
        }
        jadwalKuliahRepository.save(jadwalKuliah);
        redirectAttributes.addFlashAttribute("success", "Data jadwal kuliah berhasil disimpan.");
        return "redirect:/jadwal-kuliah";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("jadwalKuliah", jadwalKuliahRepository.findById(id).orElse(new JadwalKuliah()));
        model.addAttribute("mataKuliahList", mataKuliahRepository.findAll(Sort.by("nama").ascending()));
        model.addAttribute("dosenList", dosenRepository.findAll(Sort.by("nama").ascending()));
        model.addAttribute("pageTitle", "Edit Jadwal Kuliah");
        model.addAttribute("activePage", "jadwal-kuliah");
        return "jadwal-kuliah/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        jadwalKuliahRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Data jadwal kuliah berhasil dihapus.");
        return "redirect:/jadwal-kuliah";
    }
}
