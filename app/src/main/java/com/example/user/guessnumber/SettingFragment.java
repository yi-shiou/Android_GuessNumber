package com.example.user.guessnumber;


import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.example.user.guessnumber.dummy.modle.KeyCollectiot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private SharedPreferences prefs;
    private Preference modes_inputsize,setAns,showAns,goplaying;
    private SwitchPreference modes_different;

    private static SettingFragment settingFragment;

    public static SettingFragment newInstance(int index){
        if (settingFragment==null) settingFragment = new SettingFragment();

        return settingFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.setting, false);
        addPreferencesFromResource(R.xml.setting);
        prefs = getPreferenceManager().getSharedPreferences();
        prefs.registerOnSharedPreferenceChangeListener(this);

        modes_inputsize = findPreference(KeyCollectiot.KEY_MODES_INPUTSIZE);
        modes_different = (SwitchPreference) findPreference(KeyCollectiot.KEY_MODES_DIFFERENT);
        if (!GuessFragment.same_digit ) modes_different.setSwitchTextOff("off") ;
        else modes_different.setSwitchTextOn("on");

//        modes_different.setDefaultValue(GuessFragment.different_digit);
        setAns = findPreference(KeyCollectiot.KEY_SETANSWER);
        showAns=findPreference(KeyCollectiot.KEY_SHOWANSWER);
        showAns.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String tmpS = "";
                for(int i=0;i < GuessFragment.inputSize ;i++){
                    tmpS += GuessFragment.answer.get(i) ;
                }
                showAns.setSummary(tmpS);

                return true;// if true then no other listener.
            }
        });
        goplaying=findPreference(KeyCollectiot.KEY_GOPLAYING);
        goplaying.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //init  showAns
                showAns.setSummary("");
                //reyurn to game fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, GuessFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        });
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KeyCollectiot.KEY_MODES_INPUTSIZE)){

            modes_inputsize.setSummary(prefs.getString(key, "0").equals("0") ?
                    null : prefs.getString(key, "0"));
            String tmpS = (String) prefs.getString(key,""+GuessFragment.inputSize);
            GuessFragment.inputSize = Integer.valueOf(tmpS);
            GuessFragment.random(GuessFragment.same_digit);
            Toast.makeText(getActivity(),getString(R.string.Dialog_changed),Toast.LENGTH_SHORT).show();
        }else if (key.equals(KeyCollectiot.KEY_MODES_DIFFERENT)){
            //modes_different.setSummary(!GuessFragment.different_digit ? "On." : "Off.");
            GuessFragment.same_digit = prefs.getBoolean(key,false);
            Toast.makeText(getActivity(),getString(R.string.Dialog_changed),Toast.LENGTH_SHORT).show();
            GuessFragment.random(GuessFragment.same_digit);
        }else if (key.equals(KeyCollectiot.KEY_SETANSWER)){

            String tmpS = prefs.getString(key,"");
            if (!tmpS.equals("") ){
                if (GuessFragment.isMatch(tmpS)) {
                    setAns.setSummary(tmpS);
                    ArrayList<Integer> tmpList = new ArrayList<>(4);
                    for (int i = 0; i < GuessFragment.inputSize; i++) {
                        tmpList.add(Integer.valueOf((String) setAns.getSummary().toString().subSequence(i, i + 1)));
                    }
                    GuessFragment.answer = tmpList;
                }
                else
                    Toast.makeText(getActivity(), getString(R.string.Dialog_makeSense), Toast.LENGTH_SHORT).show();
            }
        }
    }


}//end class
