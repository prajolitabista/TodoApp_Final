package com.example.todoapp_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.example.todoapp_final.model.ETodo;
import com.example.todoapp_final.viewModel.TodoViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment fragment;
    FloatingActionButton floatingActionButton;
    TodoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        fragment = new ListTodoFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.list_activity_container, fragment)
                .commit();

        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_profile:
                startActivity(new Intent(MainActivity.this, Profile.class));
                break;
            case R.id.mnu_delete_all:
                DeleteAll();
                break;
            case R.id.mnu_delete_completed:
                DeleteAllCompleted();
                break;
            case R.id.mnu_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "User Logged-Out ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void DeleteAllCompleted() {
        List<ETodo> todoList = ListTodoFragment.viewModel.getAllTodos().getValue();
        for (int i = 0; i < todoList.size(); i++) {

            if (todoList.get(i).isCompleted()) {
                ETodo todo = todoList.get(i);
                ListTodoFragment.viewModel.deleteById(todo);
            }

        }
        Toast.makeText(getApplicationContext(), "Completed task/s DELETED", Toast.LENGTH_LONG).show();


    }

    void DeleteAll() {
        List<ETodo> todoList = ListTodoFragment.viewModel.getAllTodos().getValue();
        for (int i = 0; i < todoList.size(); i++) {
            ETodo todo = todoList.get(i);
            ListTodoFragment.viewModel.deleteById(todo);
        }
        Toast.makeText(getApplicationContext(), "All task's Deleted", Toast.LENGTH_LONG).show();
    }
}