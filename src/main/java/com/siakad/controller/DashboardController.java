package com.siakad.controller;

import com.siakad.service.DosenService;
import com.siakad.service.MahasiswaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final MahasiswaService mahasiswaService;
    private final DosenService dosenService;

    public DashboardController(MahasiswaService mahasiswaService, DosenService dosenService) {
        this.mahasiswaService = mahasiswaService;
        this.dosenService = dosenService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalMahasiswa", mahasiswaService.count());
        model.addAttribute("totalDosen", dosenService.count());
        model.addAttribute("recentMahasiswa", mahasiswaService.findRecent());
        model.addAttribute("recentDosen", dosenService.findRecent());
        model.addAttribute("activePage", "dashboard");
        return "dashboard";
    }
}
