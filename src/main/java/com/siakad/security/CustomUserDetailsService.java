package com.siakad.security;

import com.siakad.model.Dosen;
import com.siakad.model.Mahasiswa;
import com.siakad.repository.DosenRepository;
import com.siakad.repository.MahasiswaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MahasiswaRepository mahasiswaRepository;
    private final DosenRepository dosenRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(MahasiswaRepository mahasiswaRepository, DosenRepository dosenRepository, PasswordEncoder passwordEncoder) {
        this.mahasiswaRepository = mahasiswaRepository;
        this.dosenRepository = dosenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        
        // 1. Check if it's the default admin account
        if ("admin".equalsIgnoreCase(emailOrUsername)) {
            return User.withUsername("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .roles("ADMIN")
                    .build();
        }

        // 2. Check if it's a Mahasiswa by email
        Optional<Mahasiswa> mahasiswaOpt = mahasiswaRepository.findByEmail(emailOrUsername);
        if (mahasiswaOpt.isPresent()) {
            Mahasiswa mhs = mahasiswaOpt.get();
            return User.withUsername(mhs.getEmail())
                    .password(passwordEncoder.encode("mahasiswa123")) // Default password requested by user
                    .roles("MAHASISWA")
                    .build();
        }

        // 3. Check if it's a Dosen by email
        Optional<Dosen> dosenOpt = dosenRepository.findByEmail(emailOrUsername);
        if (dosenOpt.isPresent()) {
            Dosen dsn = dosenOpt.get();
            return User.withUsername(dsn.getEmail())
                    .password(passwordEncoder.encode("mahasiswa123")) // Default password requested by user
                    .roles("DOSEN")
                    .build();
        }

        // 4. Fallback for the dummy static users (for backward compatibility if they type "mahasiswa" or "dosen")
        if ("mahasiswa".equals(emailOrUsername)) {
            return User.withUsername("mahasiswa")
                    .password(passwordEncoder.encode("mahasiswa123"))
                    .roles("MAHASISWA")
                    .build();
        }
        if ("dosen".equals(emailOrUsername)) {
            return User.withUsername("dosen")
                    .password(passwordEncoder.encode("dosen123"))
                    .roles("DOSEN")
                    .build();
        }

        throw new UsernameNotFoundException("User tidak ditemukan dengan email: " + emailOrUsername);
    }
}
