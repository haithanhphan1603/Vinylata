package com.project.vinylata.Security.dataSeeder;

import com.project.vinylata.Model.Role;
import com.project.vinylata.Model.User;
import com.project.vinylata.Repository.RoleRepository;
import com.project.vinylata.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //initialize Role
        if (roleRepository.findByName("ROLE_ADMIN") == null){
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("ROLE_USER") == null) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        //initialize User

        //admin account
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()){
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setFirstName("Admin");
            admin.setLastName("Vip");
            admin.setPassword((new BCryptPasswordEncoder()).encode("123456"));
            admin.setPhoneNumber("0123456789");
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
            roles.add(roleRepository.findByName("ROLE_USER"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        //Member Account
        if (userRepository.findByEmail("user@gmail.com").isEmpty()) {
            User user = new User();
            user.setEmail("user@gmail.com");
            user.setPassword((new BCryptPasswordEncoder()).encode("123456"));
            user.setFirstName("User");
            user.setLastName("NotVip");
            user.setPhoneNumber("0363828636");
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_USER"));
            user.setRoles(roles);
            userRepository.save(user);
        }


    }
}
