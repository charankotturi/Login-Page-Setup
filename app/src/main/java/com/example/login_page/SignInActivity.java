package com.example.login_page;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnSignIn;
    private TextView txtRegister, txtSignInErrorMsg;
    private CardView errorCard;
    private static String TAG = "SignActivity";

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_id = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                errorCard.setVisibility(View.GONE);

                    /*

                Inbuilt email id validation " Patterns.EMAIL_ADDRESS.matcher(Email_Id).matches()".

                 */

                if(!email_id.equals("") && !password.equals("") && Patterns.EMAIL_ADDRESS.matcher(email_id).matches()){
                    ApplicationDataBase db = ApplicationDataBase.getInstance(SignInActivity.this);
                    User verification = db.userDao().getUserByEmail_id(email_id);

                    if(verification != null){
                        if (password.equals(verification.getPassword())){
                            Log.d(TAG, "onClick: inside sign-in page >>>>>" + verification.getOnline());

                            verification.setOnline(true);
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            intent.putExtra("OBJECT", verification.getEmail_id());
                            startActivity(intent);

                        }else{
                            errorCard.setVisibility(View.VISIBLE);
                            txtSignInErrorMsg.setText("Enter a valid email id or password!");
                        }
                    }else{
                        errorCard.setVisibility(View.VISIBLE);
                        txtSignInErrorMsg.setText("Cannot find the email id. Please register first!");
                    }

                }else{
                    errorCard.setVisibility(View.VISIBLE);
                    txtSignInErrorMsg.setText("Enter email id or password!");
                }
            }
        });

        ApplicationDataBase db = ApplicationDataBase.getInstance(this);
        List<User> testUser = db.userDao().getAllUsers();

//        String text = "";
//        errorCard.setVisibility(View.VISIBLE);

        for(User u: testUser){

            if (u.getOnline()){
                Intent intent1 = new Intent(SignInActivity.this, MainActivity.class);
                intent1.putExtra("OBJECT", u.getEmail_id());
                startActivity(intent1);
            }
            
            /*
            *********
            This is for testing the database!
            *********
             */
//            text += "\nid:" + u.getUser_id()
//            +"\nEmail_Id:" + u.getEmail_id()
//                    +"\nphoneNumber:" + u.getMobile_number()
//                    +"\nfirst_name:" + u.getFirst_name()
//                    +"\nlast_name:" + u.getLast_name()
//                    +"\nAddress:" + u.getAddress()
//                    +"\nPassword:" + u.getPassword()
//                    +"\nOnline:" + u.getOnline()
//            +"\n************";
//        }
//        if(!text.equals("")){
//            txtSignInErrorMsg.setText(text);

        }

    }

    private void initView(){
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtRegister = findViewById(R.id.txtRegister);
        errorCard = findViewById(R.id.errorCard);
        txtSignInErrorMsg = findViewById(R.id.txtSignInErrorMsg);
    }
}