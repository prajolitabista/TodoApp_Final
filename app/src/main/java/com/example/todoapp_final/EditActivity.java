package com.example.todoapp_final;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class EditActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        fragmentManager = getSupportFragmentManager();
        fragment = new EditTodoFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.edit_activity_container, fragment)
                .commit();
    }
}