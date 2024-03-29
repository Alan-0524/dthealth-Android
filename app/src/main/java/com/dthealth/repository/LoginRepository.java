package com.dthealth.repository;

import com.dthealth.dao.entity.User;
import com.dthealth.dataSource.LoginDataSource;
import com.dthealth.model.Result;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository{
    private static volatile LoginRepository instance;
    private LoginDataSource dataSource;
    private static Result<User> loginResult = null;
    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public void login(String username, String password) {
        // handle login
        dataSource.login(username, password, result -> {
            if (result instanceof Result.Success) {
                setLoggedInUser(((Result.Success<User>) result).getData());
                loginResult = result;
                LoginDataSource.isLogining = false;
            }
        });
    }

    public static synchronized Result<User> getLoginResult() {
        return loginResult;
    }
}
