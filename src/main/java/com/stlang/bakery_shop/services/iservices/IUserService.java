package com.stlang.bakery_shop.services.iservices;


import com.stlang.bakery_shop.domains.User;
import com.stlang.bakery_shop.dto.RegisterDTO;

import java.util.List;

public interface IUserService {

    List<User> findAllUsers();
    User findById(long id);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(User user);

    User registerDTOToUser(RegisterDTO registerDTO);

}
