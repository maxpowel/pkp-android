package com.wixet.pkp.palenciakernelpanic.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.wixet.pkp.palenciakernelpanic.Model.User;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PKPManager {


    private static PKPManager manager;
    public static PKPManager getManager(Context c){
        if(manager == null)
            manager = new PKPManager(c);

        return manager;
    }

    //private static final String HOST = "192.168.1.137:8000";
    private static final String HOST = "pkp.wixet.com";
    private static final String CLIENT_ID = "2_lb5hclhx06oocswswgco40sgoscwgcsg4kggsoc84k0g8gkok";
    private static final String CLIENT_SECRET = "xhk9g828s3k44cc8c0s4o8kggw8sw0gccsgcok4wsk04k84ss";


    public static final String ACCESS_TOKEN = "access_token";

    private static final String PREFS_NAME = "pkp_manager";
    private SharedPreferences settings;
    private Context context;

    private PKPManager(Context context){
        settings = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public boolean isLogged(){
        return getAccessToken() != null;
    }

    public void logout(){
        setAccessToken(null);
    }
    private String getAccessToken(){
        return settings.getString(ACCESS_TOKEN, null);
    }

    private void setAccessToken(String accessToken){
        settings.edit().putString(ACCESS_TOKEN, accessToken).apply();
    }

    HttpClient client = new HttpClient();

    public void login(String username, String password, ApiCallback callback){
        String url = "http://" + HOST + "/oauth/v2/token?grant_type=password&username=" + username
                + "&password=" + password + "&client_id="+ CLIENT_ID
                +"&client_secret=" + CLIENT_SECRET;

        HttpMethod method = new GetMethod(url);
        new LoginRequest(callback).execute(method);
    }

    public void registerUser(String username, String email, String firstname, String lastname, String password, ApiCallback callback){
        String url = "http://" + HOST + "/api/user/new";

        PostMethod method = new PostMethod(url);
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", username);
            obj.put("email", email);
            obj.put("firstname", firstname);
            obj.put("lastname", lastname);
            obj.put("password", password);
            StringRequestEntity requestEntity = new StringRequestEntity(
                    obj.toString(),
                    "application/json",
                    "UTF-8");
            method.setRequestEntity(requestEntity);
        }catch(Exception e){
            e.printStackTrace();
        }

        new RegisterUserRequest(callback).execute(method);
    }

    public void updateUser(String username, String email, String firstname, String lastname, String password, ApiCallback callback){
        String url = "http://" + HOST + "/api/user?access_token="+getAccessToken();

        PutMethod method = new PutMethod(url);
        JSONObject obj = new JSONObject();
        try {
            if(username != null)
                obj.put("username", username);

            if(email != null)
                obj.put("email", email);

            if(firstname != null)
                obj.put("firstname", firstname);

            if(lastname != null)
                obj.put("lastname", lastname);

            if(password != null)
                obj.put("password", password);

            StringRequestEntity requestEntity = new StringRequestEntity(
                    obj.toString(),
                    "application/json",
                    "UTF-8");
            method.setRequestEntity(requestEntity);
        }catch(Exception e){
            e.printStackTrace();
        }

        new RegisterUserRequest(callback).execute(method);
    }

    public void loadUser(ApiCallback callback){
        String url = "http://" + HOST + "/api/user?access_token="+ getAccessToken();

        HttpMethod method = new GetMethod(url);
        new LoadUserRequest(callback).execute(method);
    }



   /* public void doRequest() throws IOException {
        new DoGet().execute("http://192.168.1.137:8000/api/user?access_token=NzJmYTk0ODk5ZWE3OTg2ZTUxNWI2MDFhZWM1MzExMWExZTdmNjZjOGJmYzhhZmE5NDQ0MDAyNWY5N2VhZThjZg");
    }
*/


    // Oauth Login
    public class LoginResponse implements ApiResponse{
        private String accessToken;
        private String refreshToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
    private class LoginRequest extends DoRequest{
        public LoginRequest(ApiCallback callback){
            super(callback);
        }

        protected ApiResponse buildResponse(int statusCode, String json){
            LoginResponse res = new LoginResponse();
            if(statusCode == 200) {
                try {
                    JSONObject object = new JSONObject(json);

                    setAccessToken(object.getString("access_token"));
                    res.setAccessToken(object.getString("access_token"));
                    res.setRefreshToken(object.getString("refresh_token"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return res;
        }
    }

    // Load user
    public class LoadUserResponse implements ApiResponse{
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
    private class LoadUserRequest extends DoRequest{
        public LoadUserRequest(ApiCallback callback){
            super(callback);
        }

        protected ApiResponse buildResponse(int statusCode, String json){
            LoadUserResponse res = new LoadUserResponse();
            if(statusCode == 200) {
                try {
                    JSONObject object = new JSONObject(json);
                    User user = new User();
                    user.setId(object.getInt("id"));
                    user.setEmail(object.getString("email"));
                    user.setFirstname(object.getString("firstname"));
                    user.setLastname(object.getString("lastname"));
                    user.setUsername(object.getString("username"));
                    res.setUser(user);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return res;
        }
    }

    //Register user
    public class RegisterUserResponse implements ApiResponse{
        private boolean ok = false;

        public boolean isOk() {
            return ok;
        }

        public void setOk(boolean ok) {
            this.ok = ok;
        }
    }
    private class RegisterUserRequest extends DoRequest{
        public RegisterUserRequest(ApiCallback callback){
            super(callback);
        }

        protected ApiResponse buildResponse(int statusCode, String json){
            Log.d("REGISTER", json);
            RegisterUserResponse res = new RegisterUserResponse();
            if(statusCode == 200) {
                try {
                    JSONObject object = new JSONObject(json);
                    res.setOk(object.getBoolean("ok"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return res;
        }
    }

    /////////////////////

    private abstract class DoRequest extends AsyncTask<HttpMethod, Void, ApiResponse> {

        private ApiCallback callback;

        public DoRequest(ApiCallback callback){
            this.callback = callback;
        }

        abstract ApiResponse buildResponse(int satusCode, String json);

        protected ApiResponse doInBackground(HttpMethod... methods) {
            try {

                HttpMethod method = methods[0];
                int statusCode = client.executeMethod(method);

                if (statusCode != HttpStatus.SC_OK) {
                    System.err.println("Method failed: " + method.getStatusLine());
                }

                byte[] responseBody = method.getResponseBody();

                return buildResponse(statusCode, new String(responseBody));

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(ApiResponse response) {
            callback.success(response);
        }
    }
}
