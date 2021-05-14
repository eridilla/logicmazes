package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.UserTable;

public interface UserService {
    void addUser(UserTable userTable) throws UserException;
    UserTable getUser(String username) throws UserException;
    void reset() throws UserException;
}
