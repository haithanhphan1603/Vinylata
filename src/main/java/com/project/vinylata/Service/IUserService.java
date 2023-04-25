package com.project.vinylata.Service;

import com.project.vinylata.DTO.UserDto;
import com.project.vinylata.Model.User;
import com.project.vinylata.Model.UserRecord;

import java.util.List;

public interface IUserService {
    void add(UserDto userDto);

    List<UserRecord> getAllUsers();
    void delete(String email);
//    User getUserByEmail(String email);
//    User update(User user);
}
