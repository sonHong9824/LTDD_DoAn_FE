package com.example.template_project;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.MenuItem;
import android.view.View;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.template_project.fragment.CinemaFragment;
import com.example.template_project.fragment.HomeFragment;
import com.example.template_project.fragment.MovieShowingFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int FRAGMENT_HOME = 1;
    public static final int FRAGMENT_CINEMA = 2;
    public static final int FRAGMENT_MOVIE_SHOWING = 3;

    private int mCurrentFragment = FRAGMENT_HOME;


    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_toolbar){
            drawerLayout.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }

        } else if (id == R.id.nav_vp) {
            if(mCurrentFragment != FRAGMENT_MOVIE_SHOWING){
                replaceFragment(new MovieShowingFragment());
                mCurrentFragment = FRAGMENT_MOVIE_SHOWING;
            }
            
        } else if (id == R.id.nav_vr) {
            if(mCurrentFragment != FRAGMENT_CINEMA){
                replaceFragment(new CinemaFragment());
                mCurrentFragment = FRAGMENT_CINEMA;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.END);

        return true;
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

}