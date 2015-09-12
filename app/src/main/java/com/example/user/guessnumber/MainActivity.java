package com.example.user.guessnumber;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

//    private EditText editText;
//    private Button button;
    private ListView lv;
    private ArrayList<String> items;
    private ArrayAdapter<String> adeptet;

    public TextView input;

    public static int inputSizeeee = 4;
//    public static ArrayList<Integer> answer;
    private String xAxB;

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
        GuessFragment guessFragment = GuessFragment.newInstance();
//        guessFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, guessFragment)
                .addToBackStack(null)
                .commit();
//        init();

//        editText = (EditText) findViewById(R.id.editText);
//        button = (Button) findViewById(R.id.button);
//        lv = (ListView) findViewById(R.id.listView);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isMatch()) {
//                    xAxB = match();
//                    items.add(editText.getText().toString() + "  ...  " + xAxB);
//                    editText.setText("");
//                    lv.setAdapter(adeptet);
//
//                    if (xAxB.equals("4A0B")) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                        builder.setTitle("Congratulation !")
//                                .setPositiveButton(R.string.action_newGame, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        init();
//                                    }
//                                })
//                                .setMessage("You have guessed the number.")
//                                .show();
//                    }
//
//                } else {
//                    Toast.makeText(v.getContext(), "check to make sense", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        // for 4 num.
//        input.setText("");
//        random();
//
//        // for ListView
//        items = new ArrayList<String>();
//        adeptet = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
//        lv.setAdapter(adeptet);
    }

    private void init(){
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, GuessFragment.newInstance())
                .addToBackStack(null)
                .commit();
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
                init();
                break;
            case R.id.action_rule:
//                FragmentManager fragmentManager = getFragmentManager();
//                SettingDialogFragment newFragment = new SettingDialogFragment();
//                Bundle args = new Bundle();
                int mIndex = 0;
                args.putInt("index", mIndex);
                newFragment.setArguments(args);
                newFragment.show(fragmentManager, "dialog");
                break;
            case R.id.action_settings:
                mIndex = 1;
                args.putInt("index", mIndex);
                newFragment.setArguments(args);
                newFragment.show(fragmentManager,"dialog");
/*
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,new SettingDialogFragment())
                .addToBackStack(null)
                .commit();
//*/
                break;
            case R.id.action_showAnswer:
//                mIndex = 2;
//                args.putInt("index", mIndex);
//                newFragment.setArguments(args);
//                newFragment.show(fragmentManager,"dialog");
                String tmpS = "";
                for(int i=0;i<4;i++){
                    tmpS += GuessFragment.answer.get(i) ;
                }
                Toast.makeText(this,tmpS,Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
