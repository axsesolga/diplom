package com.diploma.client.data;

import com.diploma.client.data.model.User;
import com.diploma.client.network.API;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<User> login(String username, String password) {
        if (username.isEmpty() || password.isEmpty())
            return new Result.Error(new SecurityException("All fields are requested"));
        try {
            if (!API.verifyUserExists(username))
                return new Result.Error(new SecurityException("Not valid username"));
            if (!API.verifyUserPassword(username, password))
                return new Result.Error(new SecurityException("Not valid password"));

            return new Result.Success<>(API.getUser(username));

        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(e);
        }
    }

    public Result<User> register(String username, String password, String mail, boolean isArtist) {
        if (username.isEmpty() || password.isEmpty() || mail.isEmpty())
            return new Result.Error(new SecurityException("Missing Data"));

        try {
            if (!API.verifyUserExists(username))
                return new Result.Error(new SecurityException("Not valid username"));
            if (!API.verifyMailAvailable(mail))
                return new Result.Error(new SecurityException("Email is taken"));

            return  new Result.Success<>(API.createUser(username, password, mail, isArtist));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(e);
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

}