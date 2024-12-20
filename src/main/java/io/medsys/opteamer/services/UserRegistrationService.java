package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.UserRegistrationDTO;
import io.medsys.opteamer.model.Role;
import io.medsys.opteamer.model.User;
import io.medsys.opteamer.model.enums.ERole;
import io.medsys.opteamer.repositories.RoleRepository;
import io.medsys.opteamer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserRegistrationService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserRegistrationDTO registrationDTO) {
        Role role = roleRepository.findByName(ERole.ROLE_USER).get();

        User user = new User(registrationDTO.getUsername(), registrationDTO.getEmail(),
                passwordEncoder.encode(registrationDTO.getPassword()), new HashSet<>(Arrays.asList(role)));

        userRepository.save(user);
    }

}
