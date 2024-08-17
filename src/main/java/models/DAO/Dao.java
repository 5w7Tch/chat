package models.DAO;

import jdk.internal.net.http.common.Pair;
import models.USER.User;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface Dao {
    String DBID = "db";
    void closeDbConnection();
    boolean addUser(User user);
    boolean deleteUser(int id) throws SQLException;
    boolean userNameExists(String userName) throws SQLException;
    boolean accountExists(String userName, String passwordHash) throws SQLException;
    User getUser(String userName, String password) throws SQLException;
    User getUserById(Integer id) throws SQLException;
    ArrayList<User> getFriends(Integer userId) throws SQLException;
//    ArrayList<INote> getUserNotes(int userId) throws SQLException;

//    ArrayList<IFriendRequest> getUserFriendRequests(int userId) throws SQLException;
//    IFriendRequest getFriendRequestById(int friendRequestId) throws SQLException;
//    boolean addFriend(IFriendRequest friendRequest) throws SQLException;
//    boolean acceptFriendRequest(IFriendRequest friendRequest) throws SQLException;
//    boolean removeFriendRequest(IFriendRequest friendRequest) throws SQLException;
//    boolean addFriendRequest(IFriendRequest friendRequest) throws SQLException;


    boolean friendConnectionExists(Integer user1, Integer user2) throws SQLException;
    Integer getUserByName(String userName) throws SQLException;

    HashMap<String,String> searchUserByUsername (String userName) throws SQLException;
//    ArrayList<IAnnouncement> getUserAnnouncements(int userId) throws SQLException;
//    ArrayList<IFriend> getUserFriends(int userId) throws SQLException;
    boolean promoteUser(Integer user_id) throws SQLException;
    boolean friendRequestExists(Integer user1Id, Integer user2Id) throws SQLException;
    Integer getUserCount() throws SQLException;
    Integer getAdmins()throws SQLException;

}