package com.frenzdeveloper.manajerkeuangan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.frenzdeveloper.manajerkeuangan.database.AppDatabase;
import com.frenzdeveloper.manajerkeuangan.database.entity.User;
import com.frenzdeveloper.manajerkeuangan.menufragment.Account;
import com.frenzdeveloper.manajerkeuangan.menufragment.Add;
import com.frenzdeveloper.manajerkeuangan.menufragment.Home;
import com.frenzdeveloper.manajerkeuangan.sp_agent.SPAgent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getInstance(getApplicationContext());

        Fragment home = new Home();

        //cek apakah akun guest sudah dibuat oleh sistem
        if (!isGuestExsist()){
            database.userDao().insert(1, "guest", "guest@example.com");
            User userGuest = database.userDao().getUserById(1);
            SPAgent.setIdLoggedIn(this, userGuest.user_id);
            SPAgent.setUsernameLoggedIn(this, userGuest.fullname);

        }

        //cek apakah balance sudah ada atau belum
        if (isBalanceExsist()){
            database.balanceDao().insertAll(getUser_Id(), 0.0);
        }

        fragmentChange(home);

        bnv = findViewById(R.id.navbottom);
        bnv.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;

            selectedFragment = new Home();

            switch (item.getItemId()){
                case R.id.add :
                    selectedFragment = new Add();
                    break;
                case R.id.account:
                    selectedFragment = new Account();
                    break;
            }

            fragmentChange(selectedFragment);

            return true;
        });

    }

    boolean isGuestExsist(){
        if (database.userDao().getUserById(1) == null){
            return false;
        }else {
            return true;
        }
    }

    boolean isBalanceExsist(){
        if (database.balanceDao().getOne(getUser_Id()) == 0){
            return false;
        }else {
            return true;
        }
    }

    int getUser_Id(){
        return SPAgent.getIdLoggedIn(this);
    }

    // fungsi untuk mengubah fragment
    protected void fragmentChange(Fragment selectedFragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameFragment, selectedFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }
}