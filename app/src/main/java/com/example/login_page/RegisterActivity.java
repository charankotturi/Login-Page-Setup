package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextConfirmPass
            , editTextPhoneNumber, editTextLastName, editTextFirstName, editTextSAddress;
    private TextView txtSignIn, txtPassError, txtSignUpError;
    private CardView SignUpErrorCard;
    private Button btnSignIn;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = editTextFirstName.getText().toString();
                String LastName = editTextLastName.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString();
                System.out.println(phoneNumber);
                String Email_Id = editTextEmail.getText().toString();
                String Password = editTextPassword.getText().toString();
                String Address = editTextSAddress.getText().toString();

                if(!firstName.equals("") && !LastName.equals("") && !phoneNumber.equals("") && !Email_Id.equals("") && !Address.equals("") &&
                    !Password.equals("")){

                    int number = Integer.valueOf(phoneNumber);

                    if (uniqueEmail(Email_Id)){
                        SignUpErrorCard.setVisibility(View.GONE);
                        if(Password.equals(editTextConfirmPass.getText().toString())) {

                            txtPassError.setVisibility(View.GONE);

                            User user = new User(firstName, LastName, Email_Id, Password, number, Address, true);
                            ApplicationDataBase db = ApplicationDataBase.getInstance(RegisterActivity.this);
                            db.userDao().insertSingleUser(user);

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("OBJECT", user.getEmail_id());
                            startActivity(intent);

                        }else{
                            txtPassError.setVisibility(View.VISIBLE);
                        }
                    }else {
                        SignUpErrorCard.setVisibility(View.VISIBLE);
                        txtSignUpError.setText("Enter a unique Email ID!");
                    }

                }else{

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    };
                    scrollView.post(runnable);

                    SignUpErrorCard.setVisibility(View.VISIBLE);
                    txtSignUpError.setText("Please fill all the form fields!");
                }
            }
        });

        /*
        This is an attempt to prompt a message ensuring that confirm message is same as original password!
         */

//        editTextConfirmPass.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                txtPassError.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String pass = editTextPassword.getText().toString();
//                if (charSequence.equals(pass)){
//                    txtPassError.setVisibility(View.GONE);
//                }
//                txtPassError.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String pass = editTextPassword.getText().toString();
//                if (editable.equals(pass)){
//                    txtPassError.setVisibility(View.GONE);
//                }
//            }
//        });



    }
    private boolean uniqueEmail(String email_id){
        ApplicationDataBase dataBase = ApplicationDataBase.getInstance(RegisterActivity.this);
        ArrayList<User> users = (ArrayList<User>) dataBase.userDao().getAllUsers();
        for (User u: users){
            if(u.getEmail_id().equals(email_id)){
                return false;
            }
        }
        return true;
    }

    private void initView(){
        editTextConfirmPass = findViewById(R.id.editTextConfirmPassword);
        editTextPassword = findViewById(R.id.editTextSPassword);
        editTextEmail = findViewById(R.id.editSTextEmail);
        editTextLastName = findViewById(R.id.editTxtLastName);
        editTextFirstName = findViewById(R.id.editTxtFirstName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        btnSignIn = findViewById(R.id.btnSignUp);
        txtSignIn = findViewById(R.id.txtSignIn);
        SignUpErrorCard = findViewById(R.id.SignUpErrorCard);
        editTextSAddress = findViewById(R.id.editTextSAddress);
        txtPassError = findViewById(R.id.txtPassError);
        txtSignUpError = findViewById(R.id.txtRegisterErrorMsg);
        scrollView = findViewById(R.id.scrollable);
    }
}