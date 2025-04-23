package com.example.template_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.MenuItem;
import android.widget.TextView;

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

import com.example.template_project.SharedPreferences.PrefUser;
import com.example.template_project.fragment.CinemaBookingFragment;
import com.example.template_project.fragment.HomeFragment;
import com.example.template_project.fragment.MovieBookingFragment;
import com.example.template_project.fragment.MyTicketFragment;
import com.example.template_project.fragment.RePassFragment;
import com.example.template_project.model.User;
import com.google.android.material.navigation.NavigationView;

import vn.zalopay.sdk.ZaloPaySDK;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int FRAGMENT_HOME = 1;
    public static final int FRAGMENT_CINEMA = 2;
    public static final int FRAGMENT_MOVIE_SHOWING = 3;
    public static final int FRAGMENT_TICKET = 4;
    public static final int FRAGMENT_REPASS = 5;
    private int mCurrentFragment = FRAGMENT_HOME;
    private PrefUser prefUser;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView tv_name_user, tv_email_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        prefUser = new PrefUser(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        tv_name_user = headerView.findViewById(R.id.textUserName);
        tv_email_user = headerView.findViewById(R.id.textUserEmail);
        if (prefUser.isUserLoggedOut()) {
            tv_name_user.setText("Khách");
            tv_email_user.setText("(Chưa có thông tin)");
        } else {
            tv_name_user.setText(prefUser.getName());
            tv_email_user.setText(prefUser.getEmail());
            android.util.Log.e("MainActivity", "Không nhận được USER_DATA từ Intent");
        }

        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!prefUser.isUserLoggedOut()){
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_repass).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_ticket).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_repass).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_ticket).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
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
                replaceFragment(new MovieBookingFragment());
                mCurrentFragment = FRAGMENT_MOVIE_SHOWING;
            }
            
        } else if (id == R.id.nav_vr) {
            if(mCurrentFragment != FRAGMENT_CINEMA){
                replaceFragment(new CinemaBookingFragment());
                mCurrentFragment = FRAGMENT_CINEMA;
            }
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.nav_logout) {
            prefUser.logout();
            if (prefUser.isUserLoggedOut()) {
                tv_name_user.setText("Khách");
                tv_email_user.setText("(Chưa có thông tin)");
            } else {
                tv_name_user.setText(prefUser.getName());
                tv_email_user.setText(prefUser.getEmail());
                android.util.Log.e("MainActivity", "Không nhận được USER_DATA từ Intent");
            }
            invalidateOptionsMenu();
        } else if (id == R.id.nav_ticket) {
            if(mCurrentFragment != FRAGMENT_TICKET){
                replaceFragment(new MyTicketFragment());
                mCurrentFragment = FRAGMENT_TICKET;
            }
        } else if (id == R.id.nav_repass) {
            if(mCurrentFragment != FRAGMENT_REPASS){
                replaceFragment(new RePassFragment());
                mCurrentFragment = FRAGMENT_REPASS;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.END);

        return true;
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment) // Đảm bảo ID này khớp với activity_main.xml
                .addToBackStack(null) // Cho phép quay lại Fragment trước đó
                .commit();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}