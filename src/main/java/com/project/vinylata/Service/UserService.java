package com.project.vinylata.Service;

import com.project.vinylata.DTO.UserDto;
import com.project.vinylata.Exception.UserAlreadyExistsException;
import com.project.vinylata.Exception.UserNotFoundException;
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
import java.util.Optional;
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
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("this email has already been used!");
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
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
                        user.getAddress(),
                        user.getEmail())).collect(Collectors.toList());
    }

    @Override
    public void delete(String email) {
        if (userRepository.findByEmail(email).isEmpty()){
            throw new UserNotFoundException("user with this email has not existed");
        }
        userRepository.deleteUsersByEmail(email);
    }

    public void update(String email, UserDto userDto){
        Optional<User> findOutUser = userRepository.findByEmail(email);
        if (findOutUser.isEmpty()){
            throw new UserNotFoundException("user with this email has not existed");
        }
        User user = findOutUser.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        userRepository.save(user);
    }

    public UserDto getUserByEmail(String email){
        Optional<User> findOutUser = userRepository.findByEmail(email);
        if (findOutUser.isEmpty()){
            throw new UserNotFoundException("user with this email has not existed");
        }
        UserDto userDto = new UserDto();
        userDto.setId(findOutUser.get().getId());
        userDto.setFirstName(findOutUser.get().getFirstName());
        userDto.setLastName(findOutUser.get().getLastName());
        userDto.setEmail(findOutUser.get().getEmail());
        userDto.setPassword(passwordEncoder.encode(findOutUser.get().getPassword()));
        userDto.setAddress(findOutUser.get().getAddress());
        userDto.setPhoneNumber(findOutUser.get().getPhoneNumber());
        return userDto;
    }

}
