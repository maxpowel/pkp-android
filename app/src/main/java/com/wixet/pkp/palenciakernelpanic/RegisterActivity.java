package com.wixet.pkp.palenciakernelpanic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wixet.pkp.palenciakernelpanic.Api.ApiCallback;
import com.wixet.pkp.palenciakernelpanic.Api.ApiResponse;
import com.wixet.pkp.palenciakernelpanic.Api.PKPManager;


public class RegisterActivity extends ActionBarActivity {


    EditText email;
    EditText username;
    EditText firstname;
    EditText lastname;
    EditText password;
    EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setToolbar();

        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        password = (EditText) findViewById(R.id.password);
        passwordConfirm = (EditText) findViewById(R.id.password_confirm);

        Button register = (Button) findViewById(R.id.register_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email.setEnabled(false);
                username.setEnabled(false);
                firstname.setEnabled(false);
                lastname.setEnabled(false);
                password.setEnabled(false);
                passwordConfirm.setEnabled(false);

                String userEmail = email.getText()+"";
                final String userUsername = username.getText()+"";
                String userFirstname = firstname.getText()+"";
                String userLastname = lastname.getText()+"";
                final String userPassword = password.getText()+"";
                String userPasswordConfirm = passwordConfirm.getText()+"";

                if(userPassword.equals(userPasswordConfirm)){
                    Toast.makeText(getApplicationContext(),
                            "Registrando usuario", Toast.LENGTH_LONG).show();
                    PKPManager.getManager(getApplicationContext()).registerUser(userUsername, userEmail, userFirstname, userLastname, userPassword,
                            new ApiCallback() {
                                @Override
                                public void success(ApiResponse response) {
                                    if(((PKPManager.RegisterUserResponse) response).isOk()){
                                        PKPManager.getManager(getApplicationContext()).login(userUsername, userPassword, new ApiCallback() {
                                            @Override
                                            public void success(ApiResponse response) {
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                    } else{
                                        Toast.makeText(getApplicationContext(),
                                                "Error al crear cuenta!!", Toast.LENGTH_LONG).show();
                                    }

                                    email.setEnabled(true);
                                    username.setEnabled(true);
                                    firstname.setEnabled(true);
                                    lastname.setEnabled(true);
                                    password.setEnabled(true);
                                    passwordConfirm.setEnabled(true);

                                }
                            });
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Las contraseñas coinciden", Toast.LENGTH_LONG).show();

                    email.setEnabled(true);
                    username.setEnabled(true);
                    firstname.setEnabled(true);
                    lastname.setEnabled(true);
                    password.setEnabled(true);
                    passwordConfirm.setEnabled(true);
                }




            }
        });
    }

    Toolbar toolbar;
    public Toolbar getToolbar(){
        return toolbar;
    }
    public void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);
        //toolbar.setTitle("que pasa");
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
