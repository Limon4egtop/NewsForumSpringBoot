package ru.limon4etop.SpringBoot.Services;

import ru.limon4etop.SpringBoot.models.UserData;

import java.util.List;

public interface UserService {
    void addUser(final UserData user);
    void delUser(final String userId);
    void saveUserInfo(final UserData userData);
    UserData findUserById(final String userId);
    List<UserData> getAllUsersData();
}
