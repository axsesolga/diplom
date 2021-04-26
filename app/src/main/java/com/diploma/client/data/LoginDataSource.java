package com.diploma.client.data;

import android.os.AsyncTask;

import com.diploma.client.data.model.User;
import com.diploma.client.network.API;

import java.util.concurrent.ExecutionException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    class LoginAsyncTask extends AsyncTask<String, Void, Result<User>> {
        protected Result<User> doInBackground(String... pair) {
            String username = pair[0];
            String password = pair[1];

            try {
                return new Result.Success<>(API.login(username, password));

            } catch (Exception e) {
                e.printStackTrace();
                return new Result.Error(e);
            }
        }
    }

    public Result<User> login(String username, String password) {
        if (username.isEmpty() || password.isEmpty())
            return new Result.Error(new SecurityException("All fields are requested"));
        try {
            return new LoginAsyncTask().execute(username, password).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new Result.Error(e);
        }
    }

    public Result<User> register(String username, String password, String mail, String name, String userType) {
        if (username.isEmpty() || password.isEmpty())
            return new Result.Error(new SecurityException("All fields are requested"));
        try {
            return new RegisterAsyncTask().execute(username, password, mail, name, userType).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new Result.Error(e);
        }
    }

    class RegisterAsyncTask extends AsyncTask<String, Void, Result<User>> {
        protected Result<User> doInBackground(String... pair) {
            String username = pair[0];
            String password = pair[1];
            String mail = pair[2];
            String name = pair[3];
            String userType = pair[4];

            if (username.isEmpty() || password.isEmpty() || mail.isEmpty() ||name.isEmpty() || userType.isEmpty())
                return new Result.Error(new SecurityException("Missing Data"));
            try {
                return new Result.Success<>(API.register(username, password, mail, name, userType));//, mail, isArtist));
            } catch (Exception e) {
                e.printStackTrace();
                return new Result.Error(e);
            }
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

}