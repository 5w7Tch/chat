package models.DAO;

import models.USER.User;


import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class mySqlDb implements Dao {
    private final BasicDataSource dbSource;

    public mySqlDb(BasicDataSource source) {
        dbSource = source;
    }

    @Override
    public void closeDbConnection() {
        try {
            dbSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addUser(User user) {
        String query = "INSERT INTO users (firstName, email, passwordHash, isAdmin) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setBoolean(4, user.isAdmin());
            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    user.setId(set.getInt(1)); // Use column index instead of column name
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id) throws SQLException {
        try(Connection con = dbSource.getConnection();){
            PreparedStatement friendRequest = con.prepareStatement("delete from friendRequests where fromUserId=? or toUserId=?;");
            friendRequest.setInt(1,id);
            friendRequest.setInt(2,id);
            friendRequest.executeUpdate();
            friendRequest.close();

            PreparedStatement friends = con.prepareStatement("delete from friends where user1Id=? or user2Id=?;");
            friends.setInt(1,id);
            friends.setInt(2,id);
            friends.executeUpdate();
            friends.close();

            PreparedStatement quizHistory = con.prepareStatement("delete from quizHistory where userId=?");
            quizHistory.setInt(1,id);
            quizHistory.executeUpdate();
            quizHistory.close();

            PreparedStatement quizHistoryQuiz = con.prepareStatement("delete from quizHistory where quizId in (select quizzes.quizId from quizzes where quizzes.author=?)");
            quizHistoryQuiz.setInt(1,id);
            quizHistoryQuiz.executeUpdate();
            quizHistoryQuiz.close();


            PreparedStatement notes = con.prepareStatement("delete from notes where fromId=? or toId=?;");
            notes.setInt(1,id);
            notes.setInt(2,id);
            notes.executeUpdate();
            notes.close();

            PreparedStatement challangeUserId = con.prepareStatement("delete from challenges where fromId=? or toId=?;");
            challangeUserId.setInt(1,id);
            challangeUserId.setInt(2,id);
            challangeUserId.executeUpdate();
            challangeUserId.close();


            PreparedStatement challange = con.prepareStatement("delete from challenges where quizId in (select quizzes.quizId from quizzes where quizzes.author=?)");
            challange.setInt(1,id);
            challange.executeUpdate();
            challange.close();


            PreparedStatement questions = con.prepareStatement("delete from questions where quizId in (select quizzes.quizId from quizzes where author=?);");
            questions.setInt(1,id);
            questions.executeUpdate();
            questions.close();


            PreparedStatement announcements = con.prepareStatement("delete from announcements where userId=?;");
            announcements.setInt(1,id);
            announcements.executeUpdate();
            announcements.close();


            PreparedStatement userAcheivments = con.prepareStatement("delete from userAchievements where userId=?;");
            userAcheivments.setInt(1,id);
            userAcheivments.executeUpdate();
            userAcheivments.close();


            PreparedStatement quiz = con.prepareStatement("delete from quizzes where author=?");
            quiz.setInt(1,id);
            quiz.executeUpdate();
            quiz.close();
            PreparedStatement users = con.prepareStatement("delete from users where userId=?;");
            users.setInt(1,id);
            boolean res =  users.executeUpdate()>0;
            users.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userNameExists(String username) throws SQLException {
        String query = "SELECT 1 FROM users WHERE firstName = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean accountExists(String username, String passwordHash) throws SQLException {
        String query = "SELECT 1 FROM users WHERE firstName = ? AND passwordHash = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public User getUser(String userName, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE firstName = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("userId");
                    String email = resultSet.getString("email");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");
                    User u = new User(id, userName, password, email, isAdmin);
                    u.setHash(password);
                    return u;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public User getUserById(Integer userId) throws SQLException {
        String query = "SELECT * FROM users WHERE userId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String userName = resultSet.getString("firstName");
                    String password = resultSet.getString("passwordHash");
                    String email = resultSet.getString("email");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");
                    User u = new User(userId, userName, password, email, isAdmin);
                    u.setHash(password);
                    return u;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public HashSet<Integer> getFriends(Integer userId) throws SQLException {
        String query1 = "SELECT friends.user1Id, friends.user2Id FROM friends  WHERE friends.user1Id = ? or friends.user2Id = ?";

        HashSet<Integer> friends = new HashSet<>();
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query1)) {
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer user1Id = resultSet.getInt("user1Id");
                    Integer user2Id = resultSet.getInt("user2Id");
                    if (user2Id.equals(userId)) {
                        friends.add(user1Id);
                    } else if (user1Id.equals(userId)) {
                        friends.add(user2Id);
                    }
                }
            }
        }
        return friends;
    }

//    @Override
//    public ArrayList<INote> getUserNotes(int userId) throws SQLException {
//        String query = "SELECT * FROM notes WHERE notes.toId = ?";
//        try (Connection connection = dbSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, userId);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                ArrayList<INote> notes = new ArrayList<>();
//                while (resultSet.next()) {
//                    int noteId = resultSet.getInt("noteId");
//                    int fromId = resultSet.getInt("fromId");
//                    int toId = resultSet.getInt("toId");
//                    String text = resultSet.getString("text");
//                    Date sendTime = resultSet.getDate("sendTime");
//                    INote note = new Note(noteId, fromId, toId, text, sendTime);
//                    notes.add(note);
//                }
//                return notes;
//            }
//        }
//    }
//    @Override
//    public ArrayList<IFriendRequest> getUserFriendRequests(int userId) throws SQLException {
//        String query = "SELECT * FROM friendRequests WHERE friendRequests.toUserId = ?";
//        try (Connection connection = dbSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, userId);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                ArrayList<IFriendRequest> friendRequests = new ArrayList<>();
//                while (resultSet.next()) {
//                    int requestId = resultSet.getInt("requestId");
//                    int fromUserId = resultSet.getInt("fromUserId");
//                    int toUserId = resultSet.getInt("toUserId");
//                    Date sendTime = resultSet.getDate("sendTime");
//                    IFriendRequest friendRequest = new FriendRequest(requestId, fromUserId, toUserId, sendTime);
//                    friendRequests.add(friendRequest);
//                }
//                return friendRequests;
//            }
//        }
//    }
//
//    @Override
//    public IFriendRequest getFriendRequestById(int friendRequestId) throws SQLException {
//        String query = "SELECT * FROM friendRequests WHERE friendRequests.requestId = ?";
//        try (Connection connection = dbSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, friendRequestId);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    int requestId = resultSet.getInt("requestId");
//                    int fromUserId = resultSet.getInt("fromUserId");
//                    int toUserId = resultSet.getInt("toUserId");
//                    Date sendTime = resultSet.getDate("sendTime");
//                    return new FriendRequest(requestId, fromUserId, toUserId, sendTime);
//                } else {
//                    return null;
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean addFriend(IFriendRequest friendRequest) throws SQLException {
//        String query = "INSERT INTO friends (user1Id, user2Id, timeStamp) VALUES (?, ?, ?)";
//        try (Connection connection = dbSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            statement.setInt(1, friendRequest.getFromId());
//            statement.setInt(2, friendRequest.getToId());
//            java.util.Date utilDate = new java.util.Date();
//            statement.setDate(3, new Date(utilDate.getTime()));
//            boolean rowInserted = statement.executeUpdate() > 0;
//            statement.close();
//            return rowInserted;
//        }
//    }
//
//    @Override
//    public boolean acceptFriendRequest(IFriendRequest friendRequest) throws SQLException {
//        return addFriend(friendRequest) && removeFriendRequest(friendRequest);
//    }
//
//    @Override
//    public boolean removeFriendRequest(IFriendRequest friendRequest) throws SQLException {
//        String query = "DELETE FROM friendRequests WHERE requestId = ?";
//        try (Connection connection = dbSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, friendRequest.getId());
//            boolean rowDeleted = statement.executeUpdate() > 0;
//            statement.close();
//            return rowDeleted;
//        }
//    }
//
//    @Override
//    public boolean addFriendRequest(IFriendRequest friendRequest) throws SQLException {
//        String query = "INSERT INTO friendRequests (fromUserId, toUserId, sendTime) VALUES (?, ?, ?)";
//        try (Connection connection = dbSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            statement.setInt(1, friendRequest.getFromId());
//            statement.setInt(2, friendRequest.getToId());
//            statement.setDate(3, new Date( friendRequest.getSendTime().getTime()));
//            boolean rowInserted = statement.executeUpdate() > 0;
//            statement.close();
//            return rowInserted;
//        }
//    }

    @Override
    public boolean friendConnectionExists(Integer user1, Integer user2) throws SQLException {
        String query = "SELECT * FROM friends WHERE (user1Id = ? and user2Id = ?) or (user1Id = ? and user2Id = ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user1);
            statement.setInt(2, user2);
            statement.setInt(3, user2);
            statement.setInt(4, user1);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
//
//    public ArrayList<IAnnouncement> getAnnouncements() throws SQLException {
//        String query = "SELECT * FROM announcements order by announcements.timeStamp DESC";
//        try (Connection connection = dbSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            try (ResultSet resultSet = statement.executeQuery()) {
//                ArrayList<IAnnouncement> announcements = new ArrayList<>();
//                while (resultSet.next()) {
//                    int id = resultSet.getInt("announcementId");
//                    int userId = resultSet.getInt("userId");
//                    String text = resultSet.getString("text");
//                    Date timeStamp = resultSet.getDate("timeStamp");
//                    IAnnouncement announcement = new Announcement(id, userId, text, timeStamp);
//                    announcements.add(announcement);
//                }
//                return announcements;
//            }
//        }
//    }

    @Override
    public Integer getUserByName(String userName) throws SQLException {
        String query = "SELECT * FROM users WHERE firstName = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("userId");
                }
            }
        }
        return -1;
    }
//
//    @Override
//    public ArrayList<IAnnouncement> getUserAnnouncements(int userId) throws SQLException
//    {
//        String query = "SELECT * FROM announcements where userId = ? order by announcements.timeStamp DESC";
//        try (Connection connection = dbSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, userId);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                ArrayList<IAnnouncement> announcements = new ArrayList<>();
//                while (resultSet.next()) {
//                    int id = resultSet.getInt("announcementId");
//                    String text = resultSet.getString("text");
//                    Date timeStamp = resultSet.getDate("timeStamp");
//                    IAnnouncement announcement = new Announcement(id, userId, text, timeStamp);
//                    announcements.add(announcement);
//                }
//                return announcements;
//            }
//        }
//    }
//    @Override
//    public ArrayList<IFriend> getUserFriends(int userId) throws SQLException
//    {
//        String query = "SELECT * FROM friends a WHERE a.user1Id = ? or a.user2Id = ?";
//        try (Connection connection = dbSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, userId);
//            statement.setInt(2, userId);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                ArrayList<IFriend> friends = new ArrayList<>();
//                while (resultSet.next()) {
//                    int friendId = resultSet.getInt("friendId");
//                    int user1 = resultSet.getInt("user1Id");
//                    int user2 = resultSet.getInt("user2Id");
//                    java.util.Date timeStamp = resultSet.getDate("timeStamp");
//                    friends.add(new Friend(friendId, user1 == userId ? user1 : user2, user1 == userId ? user2 : user1, timeStamp));
//                }
//                return friends;
//            }
//        }
//    }
//
    @Override
    public boolean promoteUser(Integer user_Id) throws SQLException {
        String query = "UPDATE users SET isAdmin = true WHERE userId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_Id);
            boolean res = statement.executeUpdate() > 0;
            statement.close();
            return res;
        }
    }

    @Override
    public boolean friendRequestExists(Integer user1, Integer user2) throws SQLException {
        String query = "SELECT * FROM friendRequests WHERE (fromUserId = ? and toUserId = ?) or (fromUserId = ? and toUserId = ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user1);
            statement.setInt(2, user2);
            statement.setInt(3, user2);
            statement.setInt(4, user1);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public Integer getUserCount() throws SQLException {
        String query = "SELECT * FROM users;";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                int count = 0;
                while (resultSet.next()) {
                    count++;
                }
                return count;
            }
        }
    }

    @Override
    public Integer getAdmins() throws SQLException {
        String query = "SELECT * FROM users;";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                int count = 0;
                while (resultSet.next()) {
                    boolean isAdmin = resultSet.getBoolean("isAdmin");
                    if(isAdmin){
                        count++;
                    }
                }
                return count;
            }
        }
    }


    @Override
    public List<Integer> searchUserByUsername(String userName) throws SQLException {
        List<Integer> userIds = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE firstName LIKE ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + userName + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                userIds.add(rs.getInt("userId"));
            }
        }
        return userIds;
    }
}

