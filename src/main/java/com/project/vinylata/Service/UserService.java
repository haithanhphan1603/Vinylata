package com.project.vinylata.Service;

import com.project.vinylata.DTO.UserDto;
import com.project.vinylata.Model.Role;
import com.project.vinylata.Model.User;
import com.project.vinylata.Model.UserRecord;
import com.project.vinylata.Repository.RoleRepository;
import com.project.vinylata.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService implements IUserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    public void add(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword((passwordEncoder.encode(userDto.getPassword())));
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public List<UserRecord> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserRecord(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhoneNumber(),
                        user.getEmail())).collect(Collectors.toList());
    }

    @Override
    public void delete(String email) {
        userRepository.deleteUsersByEmail(email);
    }


}
