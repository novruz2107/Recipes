package com.eazi.recipes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.eazi.recipes.Helpers.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static com.eazi.recipes.LogInActivity.PROFILE;

/**
 * Created by Novruz Engineer on 11/18/2018.
 */

public class ProfileActivity extends AppCompatActivity {

    Button signOut, saveAndReturn;
    FirebaseAuth auth;
    private TextInputEditText mEmailView, name, surname, number, newEmail;
    private TextInputEditText oldPassword, newPassword;
    private CheckBox isVegan, isDiabetic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        initializeUI();
        Toolbar toolbar = findViewById(R.id.toolbarOfProfile);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Favorites");

        SharedPreferences prefs = getSharedPreferences(PROFILE, MODE_PRIVATE);
        name.setText(prefs.getString("name", "Name"));
        surname.setText(prefs.getString("surname", "Surname"));
        mEmailView.setText(prefs.getString("email", "Email"));
        number.setText(prefs.getString("number", "Number"));
        isVegan.setChecked(prefs.getBoolean("isVegan", false));
        isDiabetic.setChecked(prefs.getBoolean("isDiabetic", false));

        auth = FirebaseAuth.getInstance();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            }
        });

        saveAndReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEmailView.getText().toString()) ||
                        TextUtils.isEmpty(newEmail.getText().toString()) ||
                        TextUtils.isEmpty(oldPassword.getText().toString()) ||
                        TextUtils.isEmpty(newPassword.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "Fill all of them", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please wait...", Snackbar.LENGTH_SHORT).show();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(mEmailView.getText().toString(), oldPassword.getText().toString());
                    Profile profile = new Profile(name.getText().toString(), surname.getText().toString(), mEmailView.getText().toString(),
                            number.getText().toString(), isVegan.isChecked(), isDiabetic.isChecked());
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = getSharedPreferences(PROFILE, MODE_PRIVATE).edit();
                            editor.putString("name", name.getText().toString());
                            editor.putString("surname", surname.getText().toString());
                            editor.putString("email", newEmail.getText().toString());
                            editor.putString("number", number.getText().toString());
                            editor.putBoolean("isVegan", isVegan.isChecked());
                            editor.putBoolean("isDiabetic", isDiabetic.isChecked());
                            editor.apply();
                        }
                    });

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updateEmail(newEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("novruz", "Email updated");
                                        } else {
                                            Log.d("novruz", "Error email not updated");
                                        }
                                    }
                                });
                                user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("novruz", "Password updated");
                                        } else {
                                            Log.d("novruz", "Error password not updated");
                                        }
                                    }
                                });
                            } else {
                                Log.d("novruz", "Auth failed");
                            }
                        }
                    });

                    final String uid = FirebaseAuth.getInstance().getUid();
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                            .child("Profile").setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("novruz", "User information has been updated on database.");
                            Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
                }
            }
        });


    }

    private void initializeUI(){
        signOut = findViewById(R.id.signOut);
        saveAndReturn = findViewById(R.id.saveAndReturn);
        mEmailView = findViewById(R.id.email1);
        newEmail = findViewById(R.id.newEmail);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        name = findViewById(R.id.name1);
        surname = findViewById(R.id.surname1);
        number = findViewById(R.id.number1);
        isVegan = findViewById(R.id.isVegan1);
        isDiabetic = findViewById(R.id.isDiabetic1);
    }
}
