package service;

import entity.User;
import config.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements IUserService {
    private Db db = new Db();
    private ResultSet rs;
    private int ok;
    @Override
    public int createUser(User user) {
        String sql = "INSERT INTO users VALUES (DEFAULT,?,?,?,?)";
        try {
            //Ouverture de la connexion
            db.initPrepar(sql);
            //Passage de valeur
            db.getPstm().setString(1, user.getFirstname());
            db.getPstm().setString(2, user.getLastname());
            db.getPstm().setString(3, user.getUsername());
            db.getPstm().setString(4, user.getPassword());
            //Exécution de la requête
            ok = db.executeMaj();
            //fermeture de la connexion

        }catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users ORDER BY username ASC";
        try {
            db.initPrepar(sql);
            rs = db.executeSelect();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUser(int id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1, id);
            rs = db.executeSelect();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
            db.closeConnection();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int updateUser(User user) {
        String sql = "UPDATE user SET firstname = ?, lastname = ?, username = ?, password = ? WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1, user.getFirstname());
            db.getPstm().setString(2, user.getLastname());
            db.getPstm().setString(3, user.getUsername());
            db.getPstm().setString(4, user.getPassword());
            db.getPstm().setInt(5, user.getId());
            ok = db.executeMaj();
            db.closeConnection();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1, id);
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
}
