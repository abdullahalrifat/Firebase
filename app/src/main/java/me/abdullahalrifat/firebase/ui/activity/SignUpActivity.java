package me.abdullahalrifat.firebase.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.abdullahalrifat.firebase.R;
import me.abdullahalrifat.firebase.model.Item;
import me.abdullahalrifat.firebase.model.User;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputName,inputPhone;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputName = (EditText) findViewById(R.id.name);
        inputPhone = (EditText) findViewById(R.id.phone);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String Name=inputName.getText().toString();
                final String Phone=inputPhone.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //create User

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //get firebase user
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    connectDatabse(user,email,password,Name,Phone);
                                }
                            }
                        });







                        }
                    });
    }
    private void connectDatabse(FirebaseUser firebaseuser,String email,String password,String Name,String Phone)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

            // Creating new User node, which returns the unique key value
            // new User node would be /users/$userid/
            //getting the userid from authentication table
            String userId = firebaseuser.getUid();

            // creating User object
            User user = new User(Name,email,password,Phone);

            // pushing User to 'users' node using the userId
            mDatabase.child(userId).setValue(user, new DatabaseReference.CompletionListener() {
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });

    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
