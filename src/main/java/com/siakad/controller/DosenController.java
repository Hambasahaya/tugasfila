package com.siakad.controller;

import com.siakad.model.Dosen;
import com.siakad.service.DosenService;
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
@RequestMapping("/dosen")
public class DosenController {

    private final DosenService dosenService;

    public DosenController(DosenService dosenService) {
        this.dosenService = dosenService;
    }

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), size, Sort.by("nama").ascending());
        Page<Dosen> dosenPage = dosenService.findAll(keyword, pageable);
        model.addAttribute("dosenPage", dosenPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("activePage", "dosen");
        return "dosen/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("dosen", new Dosen());
        model.addAttribute("pageTitle", "Tambah Dosen");
        model.addAttribute("activePage", "dosen");
        return "dosen/form";
    }

    @PostMapping
    public String store(
            @Valid @ModelAttribute Dosen dosen,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", dosen.getId() == null ? "Tambah Dosen" : "Edit Dosen");
            model.addAttribute("activePage", "dosen");
            return "dosen/form";
        }
        dosenService.save(dosen);
        redirectAttributes.addFlashAttribute("success", "Data dosen berhasil disimpan.");
        return "redirect:/dosen";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("dosen", dosenService.findById(id));
        model.addAttribute("pageTitle", "Edit Dosen");
        model.addAttribute("activePage", "dosen");
        return "dosen/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        dosenService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Data dosen berhasil dihapus.");
        return "redirect:/dosen";
    }
}
