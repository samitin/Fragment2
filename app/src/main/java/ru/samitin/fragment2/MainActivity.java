package ru.samitin.fragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readSettings();
        initView();
    }
    private void initView(){
        initButtonMain();
        initButtonFavorite();
        initButtonSettings();
        initButtonBack();
    }
    private void initButtonBack(){
        Button buttonBack=findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                if (Settings.isBackAsRemove){
                    Fragment fragment=getVisibleFragment(fragmentManager);
                    if (fragment!=null)
                        fragmentManager.beginTransaction().remove(fragment).commit();
                    else
                        fragmentManager.popBackStack();
                }
            }
        });
    }

    private void initButtonSettings(){
        Button buttonSetings=findViewById(R.id.buttonSettings);
        buttonSetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new SettingsFragment());
            }
        });
    }
    private void initButtonFavorite(){
        Button buttonFavorite=findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new FavoriteFragment());
            }
        });
    }
    private void initButtonMain(){
        Button buttonMain=findViewById(R.id.buttonMain);
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new MainFragment());
            }
        });
    }

    private void readSettings(){
        SharedPreferences sharedPref=getSharedPreferences(Settings.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        Settings.isBackStack=sharedPref.getBoolean(Settings.IS_BACK_STACK_USED,false);
        Settings.isAddFragment=sharedPref.getBoolean(Settings.IS_ADD_FRAGMENT_USED,true);
        Settings.isBackAsRemove=sharedPref.getBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT,true);
        Settings.isDeleteBeforeAdd=sharedPref.getBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD,false);
    }
    private Fragment getVisibleFragment(FragmentManager fragmentManager){
        List<Fragment>fragments=fragmentManager.getFragments();
        for (int i=0;i<fragments.size();i++){
            Fragment fragment=fragments.get(i);
            if (fragment.isVisible())
                return fragment;
        }
        return null;
    }
    private void addFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if (Settings.isDeleteBeforeAdd){
            Fragment fragmentToRemove=getVisibleFragment(fragmentManager);
            if(fragmentToRemove!=null)
                fragmentTransaction.remove(fragmentToRemove);
        }
        if (Settings.isAddFragment)
            fragmentTransaction.add(R.id.fragment_container,fragment);
        else
            fragmentTransaction.replace(R.id.fragment_container,fragment);

        if (Settings.isBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}