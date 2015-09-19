package com.example.user.guessnumber;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        Bundle bundle = new Bundle();
//        bundle.putInt("inputsize", inputSizeeee);
//        bundle.putString("input", input.getText().toString());
//
//        random();
//        bundle.putIntegerArrayList("answer", answer);
        GuessFragment guessFragment = GuessFragment.newInstance(0);
//        guessFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, guessFragment)
                .commit();
//        init();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SettingDialogFragment newFragment = new SettingDialogFragment();
        Bundle args = new Bundle();

        int id = item.getItemId();
        switch (id){
            case R.id.action_newGame:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, GuessFragment.newInstance(0))
                        .commit();
                break;
            case R.id.action_rule:

                int mIndex = 0;
                args.putInt("index", mIndex);
                newFragment.setArguments(args);
                newFragment.show(fragmentManager, "dialog");
                break;

            case R.id.action_score:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, ScoreFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.action_settings:
/*/
                mIndex = 1;
                args.putInt("index", mIndex);
                newFragment.setArguments(args);
                newFragment.show(fragmentManager,"dialog");
//*/
                 fragmentManager.beginTransaction()
                    .replace(R.id.main_container, SettingFragment.newInstance(0))
                    .addToBackStack(null)
                    .commit();
//*/
                break;
            case R.id.action_quit:
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
