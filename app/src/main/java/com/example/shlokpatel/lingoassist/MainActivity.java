package com.example.shlokpatel.lingoassist;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    int preSelectedIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,new LearnFragment()).commit();
        BottomNavigationView navigation = findViewById(R.id.navigation_bottom);
        navigation.setOnNavigationItemSelectedListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            signInActivity();
        }
        else{
            //Nothing
        }
    }

    void signInActivity(){
        Intent intent=new Intent(MainActivity.this,SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.translate_activity:
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,new LearnFragment()).commit();
                return true;
            case R.id.my_account:
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,new AccountFragment()).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

