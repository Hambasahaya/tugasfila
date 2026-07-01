package com.siakad.service;

import com.siakad.model.Mahasiswa;
import com.siakad.repository.MahasiswaRepository;
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
public class MahasiswaService {

    private final MahasiswaRepository mahasiswaRepository;

    public MahasiswaService(MahasiswaRepository mahasiswaRepository) {
        this.mahasiswaRepository = mahasiswaRepository;
    }

    public Page<Mahasiswa> findAll(String keyword, Pageable pageable) {
        if (StringUtils.hasText(keyword)) {
            return mahasiswaRepository.findByNamaContainingIgnoreCaseOrNimContainingIgnoreCaseOrProgramStudiContainingIgnoreCase(
                    keyword,
                    keyword,
                    keyword,
                    pageable
            );
        }
        return mahasiswaRepository.findAll(pageable);
    }

    public List<Mahasiswa> findRecent() {
        return mahasiswaRepository.findAll(PageRequest.of(0, 5, Sort.by("createdAt").descending())).getContent();
    }

    public Mahasiswa findById(Long id) {
        return mahasiswaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mahasiswa tidak ditemukan"));
    }

    public long count() {
        return mahasiswaRepository.count();
    }

    @Transactional
    public Mahasiswa save(Mahasiswa mahasiswa) {
        if (mahasiswa.getId() != null) {
            Mahasiswa existing = findById(mahasiswa.getId());
            existing.setNim(mahasiswa.getNim());
            existing.setNama(mahasiswa.getNama());
            existing.setProgramStudi(mahasiswa.getProgramStudi());
            existing.setAngkatan(mahasiswa.getAngkatan());
            existing.setEmail(mahasiswa.getEmail());
            existing.setNoHp(mahasiswa.getNoHp());
            existing.setAlamat(mahasiswa.getAlamat());
            return mahasiswaRepository.save(existing);
        }
        return mahasiswaRepository.save(mahasiswa);
    }

    @Transactional
    public void deleteById(Long id) {
        mahasiswaRepository.deleteById(id);
    }
}
