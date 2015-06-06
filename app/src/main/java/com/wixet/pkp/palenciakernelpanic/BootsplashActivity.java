package com.wixet.pkp.palenciakernelpanic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.wixet.pkp.palenciakernelpanic.Api.ApiCallback;
import com.wixet.pkp.palenciakernelpanic.Api.ApiResponse;
import com.wixet.pkp.palenciakernelpanic.Api.PKPManager;
import com.wixet.pkp.palenciakernelpanic.Model.User;


public class BootsplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootsplash);

        //setToolbar();

        Button davidButton = (Button) findViewById(R.id.david_button);

        davidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        //final PKPManager m = new PKPManager(this);
        /*m.login("test", "123456", new ApiCallback() {
            @Override
            public void success(ApiResponse response) {
                m.loadUser(new ApiCallback() {
                    @Override
                    public void success(ApiResponse response) {
                        User u = ((PKPManager.LoadUserResponse) response).getUser();
                        Log.d("USUARIUO ES ", ""+ u.getUsername() + " id: " + u.getId());


                    }
                });
            }
        });*/

        /*m.loadUser(new ApiCallback() {
            @Override
            public void success(ApiResponse response) {
                User u = ((PKPManager.LoadUserResponse) response).getUser();
                Log.d("USUARIUO ES ", ""+ u.getUsername() + " id: " + u.getId());


            }
        });*/
        //startActivity(intent);
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);
        toolbar.setTitle("Adorad al lider insensatos!!");
        setSupportActionBar(toolbar);
    }
}
