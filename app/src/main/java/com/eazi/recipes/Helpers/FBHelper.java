package com.eazi.recipes.Helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Novruz Engineer on 11/18/2018.
 */

public class FBHelper {

    final String uid = FirebaseAuth.getInstance().getUid();
    final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference ref = myRef.child("users").child(uid);


}
