package com.siakad.service;

import com.siakad.model.Dosen;
import com.siakad.repository.DosenRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class DosenService {

    private final DosenRepository dosenRepository;

    public DosenService(DosenRepository dosenRepository) {
        this.dosenRepository = dosenRepository;
    }

    public Page<Dosen> findAll(String keyword, Pageable pageable) {
        if (StringUtils.hasText(keyword)) {
            return dosenRepository.findByNamaContainingIgnoreCaseOrNidnContainingIgnoreCaseOrKeahlianContainingIgnoreCase(
                    keyword,
                    keyword,
                    keyword,
                    pageable
            );
        }
        return dosenRepository.findAll(pageable);
    }

    public List<Dosen> findRecent() {
        return dosenRepository.findAll(PageRequest.of(0, 5, Sort.by("createdAt").descending())).getContent();
    }

    public Dosen findById(Long id) {
        return dosenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dosen tidak ditemukan"));
    }

    public long count() {
        return dosenRepository.count();
    }

    @Transactional
    public Dosen save(Dosen dosen) {
        if (dosen.getId() != null) {
            Dosen existing = findById(dosen.getId());
            existing.setNidn(dosen.getNidn());
            existing.setNama(dosen.getNama());
            existing.setKeahlian(dosen.getKeahlian());
            existing.setJabatan(dosen.getJabatan());
            existing.setEmail(dosen.getEmail());
            existing.setNoHp(dosen.getNoHp());
            return dosenRepository.save(existing);
        }
        return dosenRepository.save(dosen);
    }

    @Transactional
    public void deleteById(Long id) {
        dosenRepository.deleteById(id);
    }
}
