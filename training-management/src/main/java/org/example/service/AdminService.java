
package org.example.service;

import org.example.Admin;
import org.example.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository,
                        PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(String username, String password) {

        Admin admin = adminRepository.findByUsername(username)
                .orElse(null);

        return admin != null &&
                passwordEncoder.matches(password, admin.getPassword());
    }
}
