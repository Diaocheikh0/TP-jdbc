package service;

import entity.User;

import java.util.List;

public interface IUserService {
    public int createUser(User user);
    public List<User> getAllUsers();
    public User getUser(int id);
    public int updateUser(User user);
    public int delete(int id);
}
