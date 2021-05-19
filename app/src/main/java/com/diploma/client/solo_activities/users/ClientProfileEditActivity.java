package com.diploma.client.solo_activities.users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Client;
import com.diploma.client.network.API;

import java.util.concurrent.ExecutionException;

public class ClientProfileEditActivity extends AppCompatActivity {
    Client client = (Client) MainActivity.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit_info);


        EditText mailEdit = (EditText) findViewById(R.id.profileClientMailEdit);
        mailEdit.setText(client.mail);
        EditText nameEdit = (EditText) findViewById(R.id.profileClientNameEdit);
        nameEdit.setText(client.name);

        Button save = (Button) findViewById(R.id.profileClientSaveEdit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    client.mail = mailEdit.getText().toString();
                    client.name = nameEdit.getText().toString();

                    new SaveClient().execute(client).get();

                    MainActivity.updateUser();


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    static class SaveClient extends AsyncTask<Client, Void, Void> {
        protected Void doInBackground(Client... params) {
            try {
               API.updateBaseUser(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}