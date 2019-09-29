package com.dthealth.dao;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import com.dthealth.dao.entity.LoggedInUser;
import com.dthealth.dao.entity.Message;
import com.dthealth.dao.entity.User;

import java.util.List;


@Database(entities = {Message.class, User.class, LoggedInUser.class}, version = 2)
public abstract class RoomDb extends RoomDatabase {

    public abstract MessageDao messageDao();

    public abstract LoggedInUserDao loggedInUserDao();

    public abstract UserDao userDao();

    @Dao
    public interface MessageDao {
        @Query("select * from message where my_id = (:myId) and object_id = (:objectId) order by create_time asc")
        List<Message> findMessageById(String myId, String objectId);

        @Query("select count(*) from message where state = 0")
        int countTotalNumberOfUnread();

        @Query("select count(*) from message where state = 0 and my_id = (:myId) and object_id = (:objectId)")
        int countNumberOfUnread(String myId, String objectId);

        @Insert
        void insert(Message message);

        @Insert
        void insertAll(Message... messages);

        @Delete
        void delete(Message message);
    }

    @Dao
    public interface LoggedInUserDao {
        @Query("select * from loggedinuser")
        List<LoggedInUser> getLoggedInUser();

        @Insert
        void insertLoggedInUser(LoggedInUser loggedInUser);

        @Update
        void updateLoggedInUser(LoggedInUser loggedInUser);
    }

    @Dao
    public interface UserDao {
        @Query("select * from user")
        List<User> findUserList();

        @Query("select * from user where id = (:id)")
        User findUserById(String id);

        @Insert
        void insertUser(User user);

        @Update
        void updateUser(User user);
    }
}
