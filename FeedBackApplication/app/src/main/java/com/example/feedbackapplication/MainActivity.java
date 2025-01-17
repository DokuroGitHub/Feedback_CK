package com.example.feedbackapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Button btnLogin;
    private static String KEY_ROLE = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get role for each user
        try {
            KEY_ROLE = getIntent().getStringExtra("role");
            String username = getIntent().getStringExtra("username");
            if (KEY_ROLE.equals("admin")) {
                setContentView(R.layout.admin_layout);
            }
            if (KEY_ROLE.equals("trainer")) {
                setContentView(R.layout.trainer_layout);
            }
            if (KEY_ROLE.equals("trainee")) {
                setContentView(R.layout.trainee_layout);
            }
        } catch (Exception e) {
            setContentView(R.layout.activity_main);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_assignment, R.id.nav_class, R.id.nav_module, R.id.nav_enrollment, R.id.nav_feedback, R.id.nav_result, R.id.nav_question, R.id.nav_question, R.id.nav_contact, R.id.nav_logout, R.id.nav_join,R.id.nav_add_module)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public Bundle getMyData() {
        //send data to fragment
        KEY_ROLE = getIntent().getStringExtra("role");
        Bundle hm = new Bundle();
        if(KEY_ROLE == null){
            hm.putString("val1","null");
            KEY_ROLE = "cant null to equal";
        }
        if(KEY_ROLE.equals("admin")){
            hm.putString("val1","admin");
        }
        if(KEY_ROLE.equals("trainer")){
            hm.putString("val1","trainer");
        }
        if(KEY_ROLE.equals("trainee")){
            hm.putString("val1","trainee");
        }
        return hm;
    }

}