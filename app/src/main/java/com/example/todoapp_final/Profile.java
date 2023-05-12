package com.example.todoapp_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.todoapp_final.users.users;

public class Profile extends AppCompatActivity {

    private FirebaseUser users;
    private DatabaseReference reference;
    private String userID;
    private Button list , logout;

    FragmentManager fragmentManager;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();
        fragment = new ListTodoFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.constraintLayout_list, fragment)
                .commit();

        setContentView(R.layout.activity_profile);

        users= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");

        userID = users.getUid();
        final TextView FullName = (TextView) findViewById(R.id.name_profile);
        final TextView phone = (TextView) findViewById(R.id.phone_profile);
        final TextView email = (TextView) findViewById(R.id.email_profile);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users user = snapshot.getValue(users.class);

                if(user!=null){
                    String name = user.getName();
                    String Email = user.getEmail();
                    String Phone = user.getPhone();

                    FullName.setText(name);
                    phone.setText(Phone);
                    email.setText(Email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        list= findViewById(R.id.see_list);
        logout= findViewById(R.id.profile_logout);

        list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,MainActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Profile.this, "User Logged Out ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profile.this,LoginActivity.class));
            }
        });
    }
}