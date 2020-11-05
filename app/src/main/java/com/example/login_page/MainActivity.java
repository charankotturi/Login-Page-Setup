package com.example.login_page;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "pot head";
    private MaterialToolbar toolBar;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private RecyclerView recView;
    private Adaptor adaptor;
    private TextView navUserName, navAddress, navPhoneNumber, navEmailId;
    private Button btnLogOut;
    private View progressBar;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();;

        Utils.initArrayList();

        setSupportActionBar(toolBar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.start_toggle, R.string.stop_toggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        adaptor = new Adaptor(MainActivity.this);

        Intent intent = getIntent();
        if(intent!= null) {
            String email_id = intent.getStringExtra("OBJECT");
            System.out.println(email_id);
            if(email_id != null){
                ApplicationDataBase db = ApplicationDataBase.getInstance(MainActivity.this);
                User user = db.userDao().getUserByEmail_id(email_id);
                user.setOnline(true);
                db.userDao().updateSingleUser(user);
                if(user != null){
                    updateFragment(user);
                }
            }
        }

        new ProgressBar().execute();


    }

    private class ProgressBar extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            volleyPost();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void updateFragment(User user){

        View view =  navView.getHeaderView(0);
        initNavView(view);

        String userName = user.getFirst_name() + " " + user.getLast_name();

        navUserName.setText(userName);
        navAddress.setText(user.getAddress());
        navPhoneNumber.setText(String.valueOf(user.getMobile_number()));
        navEmailId.setText(user.getEmail_id());

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApplicationDataBase db = ApplicationDataBase.getInstance(MainActivity.this);

                user.setOnline(false);

                db.userDao().updateSingleUser(user);

                Log.d(TAG, "onClick: status of the logOut in Main activity" + user.getOnline());
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });


    }

    private void initNavView(View view){
        navUserName = view.findViewById(R.id.txtUserName);
        navAddress = view.findViewById(R.id.navAddress);
        navPhoneNumber = view.findViewById(R.id.navPhoneNumber);
        navEmailId = view.findViewById(R.id.navEmailId);
        btnLogOut = view.findViewById(R.id.BtnLogOut);
    }

    private void volleyPost() {

        final Gson gson = new Gson();
        final ArrayList<UserModel> list = new ArrayList<>();
        String url = "https://reqres.in/api/users?page=1";

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                cardView.setVisibility(View.GONE);
                try {
                    JSONObject object =new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    for(int i=0;i<array.length();i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        String name =object1.getString("id");
                        String email =object1.getString("email");
                        String first_name = object1.getString("first_name");
                        String last_name = object1.getString("last_name");
                        String avatar = object1.getString("avatar");
                        list.add(new UserModel(Integer.valueOf(name), email, first_name, last_name, avatar));
                    }


                    recView.setAdapter(adaptor);

                    if(SortList(list) != null){
                        adaptor.setUserModels(SortList(list));
                    }else{
                        adaptor.setUserModels(list);
                    }

                    recView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    Utils.setDataItems(list);

                    System.out.println(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                cardView.setVisibility(View.VISIBLE);
                error.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "getInfo: >>>>>>>>>>>>>>>>>");
        queue.add(request);
        queue.start();
    }

    private ArrayList<UserModel> SortList(ArrayList<UserModel> usersList){

        Comparator<UserModel> comparable = new Comparator<UserModel>() {
            @Override
            public int compare(UserModel t1, UserModel t2) {
                return t1.getFirstName().toString().compareTo(t2.getFirstName().toString());
            }
        };

        System.out.println(usersList);

        Collections.sort(usersList, comparable);

        System.out.println(usersList);

        return usersList;
    }

    private void initView(){
        toolBar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.DrawerLayout);
        recView = findViewById(R.id.recView);
        progressBar = findViewById(R.id.progress_bar);
        cardView = findViewById(R.id.ApiFailureCard);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}