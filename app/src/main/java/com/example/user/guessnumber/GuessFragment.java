package com.example.user.guessnumber;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.guessnumber.dummy.modle.KeyCollectiot;

import java.util.ArrayList;

public class GuessFragment extends Fragment {

    private View view;
    private TextView input;
    private ImageButton imageButtonBack,imageButtonInput;
    private TextView tv0,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    private ListView lv;
    private ArrayList<String> items;
    private ArrayAdapter<String> adeptet;
    private int count = 0;
    private SharedPreferences prefs;

    public static int inputSize = 4;
    public static ArrayList<Integer> answer;
    private String xAxB;
    private int tmp;

    public static GuessFragment newInstance(int index){
        GuessFragment guessFragment = new GuessFragment();
        if (index==0) random();

        return guessFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_guess, container, false);

        prefs = getActivity().getPreferences(1);

        myfindViewById();
        input.setText("");
        input.setHint("Input " + inputSize + " different digits.");

        // for ListView
        items = new ArrayList<String>();
        adeptet = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,items);//or  getActivity()
        lv.setAdapter(adeptet);

        return view;
    }

    public void init() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, GuessFragment.newInstance(0))
                .addToBackStack(null)
                .commit();
    }

    public static void random() {
        int tmp;
        ArrayList<Integer> randomList = new ArrayList<>();

        for (int i = 0; i < inputSize; i++) {
            do {
                tmp = (int) (Math.random() * 10);
            } while (randomList.contains(tmp));
            randomList.add(tmp);
        }
        answer = randomList;
    }

    private String match(){
        int a = 0,b = 0;
        ArrayList<Integer> guessList= new ArrayList<Integer>();
        for (int i = 0; i < inputSize; i++)
            guessList.add(Integer.parseInt(input.getText().toString().substring(i, i + 1)));

        //match i is index of guessList
        for (int i = 0; i < inputSize; i++) {
            // j is index of answer List
            for (int j = 0; j < inputSize; j++){
                if(guessList.get(i) == answer.get(j)) {
                    if (i == j) a++;
                    else b++;
                }
            }
        }

        xAxB = a + "A" + b + "B";
        return a + "A" + b + "B" ;
    }

    //check input has make sense (include inputsize and different digits)
    public static Boolean isMatch(String tmpS){
        if (tmpS.length() != inputSize) return false;
        for (int i = 0; i < inputSize; i++) {
            if (tmpS.lastIndexOf(tmpS.substring(i, i + 1)) != i ) return false;
        }

        return true;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageButtonBack:
                    tmp = input.getText().toString().length();
                    if (tmp > 0) input.setText(input.getText().toString().substring(0, tmp - 1));

                    break;
                case R.id.imageButtonInput:
                    String tmpS = input.getText().toString();
                    if (isMatch(tmpS)) {
                        xAxB = match();
                        items.add(input.getText().toString() + "  ...  " + xAxB);
                        input.setText("");
                        lv.setAdapter(adeptet);
                        count++;
                        if (xAxB.equals(inputSize+"A0B")) {

                            // save the number of ending game
                            int ending_number = prefs.getInt(KeyCollectiot.KEY_ENDING, 0);
                            prefs.edit().putInt(KeyCollectiot.KEY_ENDING, (ending_number+1)).commit();

                            float total = prefs.getFloat(KeyCollectiot.KEY_AVERAGE_SCORE, Integer.MAX_VALUE);
                            total = (total*ending_number + (float)count)/(ending_number+1);
                            prefs.edit().putFloat(KeyCollectiot.KEY_AVERAGE_SCORE, total).commit();

                            if(count < prefs.getInt(KeyCollectiot.KEY_HIGHEST_SCORE, Integer.MAX_VALUE)) {
                                prefs.edit().putInt(KeyCollectiot.KEY_HIGHEST_SCORE, count).commit();
                            }
                            count = 0;
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle(getString(R.string.congratulation))
                                    .setPositiveButton(R.string.action_newGame, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            init();
                                        }
                                    })
                                    .setMessage(getString(R.string.good))
                                    .show();
                        }

                    } else {
                        Toast.makeText(v.getContext(), "check to make sense", Toast.LENGTH_SHORT).show();
                    }

                    break;
                default:
                    if (input.getText().toString().length() < inputSize)
                        input.setText(input.getText().toString() + ((TextView) v).getText().toString());
            }

        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()){
                case R.id.imageButtonBack:
                    input.setText("");
                    break;
                case R.id.imageButtonInput:
                    break;
                default:
                    v.setSelected(v.isSelected() ? false : true);
//                    v.slistener
            }
            return true;
        }
    };

//    private View.OnDragListener onDragListener = new View.OnDragListener() {
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//            return true;
//        }
//    };

    private void myfindViewById(){
        input = (TextView) view.findViewById(R.id.textInput);
        lv = (ListView) view. findViewById(R.id.listView2);
        imageButtonBack=(ImageButton) view.findViewById(R.id.imageButtonBack);
        imageButtonInput=(ImageButton) view.findViewById(R.id.imageButtonInput);
        tv0 = (TextView) view.findViewById(R.id.textView0);
        tv1 = (TextView) view.findViewById(R.id.textView1);
        tv2 = (TextView) view.findViewById(R.id.textView2);
        tv3 = (TextView) view.findViewById(R.id.textView3);
        tv4 = (TextView) view.findViewById(R.id.textView4);
        tv5 = (TextView) view.findViewById(R.id.textView5);
        tv6 = (TextView) view.findViewById(R.id.textView6);
        tv7 = (TextView) view.findViewById(R.id.textView7);
        tv8 = (TextView) view.findViewById(R.id.textView8);
        tv9 = (TextView) view.findViewById(R.id.textView9);
        imageButtonBack.setOnClickListener(onClickListener);
        imageButtonBack.setLongClickable(true);
        imageButtonBack.setOnLongClickListener(onLongClickListener);
        imageButtonInput.setOnClickListener(onClickListener);
        imageButtonInput.setLongClickable(true);
        imageButtonInput.setOnLongClickListener(onLongClickListener);
        tv0.setOnClickListener(onClickListener);
        tv0.setLongClickable(true);
        tv0.setOnLongClickListener(onLongClickListener);
//        tv0.setOnDragListener(onDragListener);
        tv1.setOnClickListener(onClickListener);
        tv1.setLongClickable(true);
        tv1.setOnLongClickListener(onLongClickListener);
        tv2.setOnClickListener(onClickListener);
        tv2.setLongClickable(true);
        tv2.setOnLongClickListener(onLongClickListener);
        tv3.setOnClickListener(onClickListener);
        tv3.setLongClickable(true);
        tv3.setOnLongClickListener(onLongClickListener);
        tv4.setOnClickListener(onClickListener);
        tv4.setLongClickable(true);
        tv4.setOnLongClickListener(onLongClickListener);
        tv5.setOnClickListener(onClickListener);
        tv5.setLongClickable(true);
        tv5.setOnLongClickListener(onLongClickListener);
        tv6.setOnClickListener(onClickListener);
        tv6.setLongClickable(true);
        tv6.setOnLongClickListener(onLongClickListener);
        tv7.setOnClickListener(onClickListener);
        tv7.setLongClickable(true);
        tv7.setOnLongClickListener(onLongClickListener);
        tv8.setOnClickListener(onClickListener);
        tv8.setLongClickable(true);
        tv8.setOnLongClickListener(onLongClickListener);
        tv9.setOnClickListener(onClickListener);
        tv9.setLongClickable(true);
        tv9.setOnLongClickListener(onLongClickListener);

    }

}
