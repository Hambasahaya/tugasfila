package com.siakad.service;

import com.siakad.model.Pengumuman;
import com.siakad.repository.PengumumanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PengumumanService {

    private final PengumumanRepository pengumumanRepository;

    public PengumumanService(PengumumanRepository pengumumanRepository) {
        this.pengumumanRepository = pengumumanRepository;
    }

    public List<Pengumuman> findAll() {
        return pengumumanRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Pengumuman> findForRole(String role) {
        if ("ADMIN".equals(role)) {
            return findAll();
        }
        return pengumumanRepository.findByTargetRoleOrderByCreatedAtDesc(role);
    }

    public Optional<Pengumuman> findById(Long id) {
        return pengumumanRepository.findById(id);
    }

    public Pengumuman save(Pengumuman pengumuman) {
        return pengumumanRepository.save(pengumuman);
    }

    public void deleteById(Long id) {
        pengumumanRepository.deleteById(id);
    }
}
