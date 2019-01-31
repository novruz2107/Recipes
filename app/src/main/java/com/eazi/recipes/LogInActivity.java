package com.eazi.recipes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.eazi.recipes.Helpers.Profile;
import com.eazi.recipes.Helpers.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.eazi.recipes.R.id.action_menu_presenter;
import static com.eazi.recipes.R.id.surname;
import static com.google.android.gms.common.Scopes.PROFILE;

public class LogInActivity extends AppCompatActivity {

    TextInputEditText email_editText, password_editText;
    Button logIn, guestLogIn;
    public static final String PROFILE = "Profile";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        email_editText = (TextInputEditText) findViewById(R.id.email_login);
        password_editText = (TextInputEditText) findViewById(R.id.password_login);
        logIn = (Button) findViewById(R.id.sign_in_button);
        guestLogIn = (Button) findViewById(R.id.sign_as_a_guest);
        guestLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInfoDialog("All recipes will be available, however without signing up " +
                        "you cannot get discount coupons");


            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogIn();
            }
        });

        //Loading FirebaseAuth:
        mAuth = FirebaseAuth.getInstance();

    }

    private void attemptLogIn(){
        email_editText.setError(null);
        password_editText.setError(null);

        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //checking of password
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            password_editText.setError(getString(R.string.error_invalid_password));
            focusView = password_editText;
            cancel = true;
        }

        //checking of email
        if (TextUtils.isEmpty(email)) {
            email_editText.setError(getString(R.string.error_field_required));
            focusView = email_editText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            email_editText.setError(getString(R.string.error_invalid_email));
            focusView = email_editText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        showErrorDialog("Email or password is incorrect!");
                    }else{
                        AsyncTask.execute(new Runnable() {
                            Profile mProfile = new Profile();
                            @Override
                            public void run() {
                                final String uid = FirebaseAuth.getInstance().getUid();
                                final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                                final DatabaseReference ref = myRef.child("users").child(uid);
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot profile : dataSnapshot.getChildren()) {
                                            mProfile = profile.getValue(Profile.class);
                                            SharedPreferences.Editor editor = getSharedPreferences(PROFILE, MODE_PRIVATE).edit();
                                            editor.putString("name", mProfile.getName());
                                            editor.putString("surname", mProfile.getSurname());
                                            editor.putString("email", mProfile.getEmail());
                                            editor.putString("number", mProfile.getPhone());
                                            editor.putBoolean("isMale", mProfile.isMale());
                                            editor.putBoolean("isVegan", mProfile.isVegan());
                                            editor.putBoolean("isDiabetic", mProfile.isDiabetic());
                                            editor.apply();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });

                            }
                        });

                        //Open main activity
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    }
                }
            });

        }
    }

    private void showInfoDialog(String message){
        new AlertDialog.Builder(this)
                        .setMessage(message)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                            }
                        })
                        .setNegativeButton("Sign in", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
    }

    private void showErrorDialog(String message){

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle("Ops..")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void goToSignUpActivity(View v){
        startActivity(new Intent(getBaseContext(), SignUpActivity.class));
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}
