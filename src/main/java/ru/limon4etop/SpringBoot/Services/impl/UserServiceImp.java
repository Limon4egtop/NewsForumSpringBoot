package ru.limon4etop.SpringBoot.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.limon4etop.SpringBoot.Services.UserService;
import ru.limon4etop.SpringBoot.models.UserData;
import ru.limon4etop.SpringBoot.repos.userRepo;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private userRepo userRepo;

    @Autowired
    public UserServiceImp(final userRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void addUser(final UserData user) {
        this.userRepo.save(user);
    }

    @Override
    public void delUser(final String userId) {
        UserData deleteUser = new UserData();
        deleteUser.setId(userId);
        this.userRepo.delete(deleteUser);
    }

    @Override
    public void saveUserInfo(final UserData userData) {
        this.userRepo.save(userData);
    }

    @Override
    public UserData findUserById(final String userId) {
        return this.userRepo.findUserById(userId);
    }

    @Override
    public List<UserData> getAllUsersData() {
        return userRepo.findAll();
    }
}
