package ru.samitin.fragment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings,container,false);
        initView(view);
        return view;
    }
    private void initView(View view){
        initSwichBackStack(view);
        initRadioAdd(view);
        initRadioReplace(view);
        initSwichBackAsRemove(view);
        initSwichDeleteBeforeAdd(view);
    }
    private void initRadioReplace(View view){
        RadioButton radioButtonReplace=view.findViewById(R.id.radioButtonReplace);
        radioButtonReplace.setChecked(!Settings.isAddFragment);
        radioButtonReplace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Settings.isAddFragment=!b;
                writeSettings();
            }
        });
    }
    private void initRadioAdd(View view){
        RadioButton radioButtonAdd=view.findViewById(R.id.radioButtonAdd);
        radioButtonAdd.setChecked(Settings.isAddFragment);
        radioButtonAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Settings.isAddFragment=b;
                writeSettings();
            }
        });
    }
    private void initSwichBackStack(View view){
        SwitchCompat swichUseBackStack=view.findViewById(R.id.switchBackStack);
        swichUseBackStack.setChecked(Settings.isBackStack);
        swichUseBackStack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Settings.isBackStack=b;
                writeSettings();
            }
        });
    }
    private void initSwichBackAsRemove(View view){
        SwitchCompat switchBackAsRemove=view.findViewById(R.id.switchBackAsRemove);
        switchBackAsRemove.setChecked(Settings.isBackAsRemove);
        switchBackAsRemove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Settings.isBackAsRemove=b;
                writeSettings();
            }
        });
    }
    private void initSwichDeleteBeforeAdd(View view){
        SwitchCompat switchDeleteBeforeAdd=view.findViewById(R.id.switchDeleteBeforeAdd);
        switchDeleteBeforeAdd.setChecked(Settings.isDeleteBeforeAdd);
        switchDeleteBeforeAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Settings.isDeleteBeforeAdd=b;
                writeSettings();
            }
        });
    }
    private void writeSettings(){
        SharedPreferences sharedPref=requireActivity().getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putBoolean(Settings.IS_BACK_STACK_USED,Settings.isBackStack);
        editor.putBoolean(Settings.IS_ADD_FRAGMENT_USED,Settings.isAddFragment);
        editor.putBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT,Settings.isBackAsRemove);
        editor.putBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD,Settings.isDeleteBeforeAdd);
        editor.apply();
    }
}
