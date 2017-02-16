package com.example.user.guessnumber;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.Preference;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import com.example.user.guessnumber.dummy.modle.KeyCollectiot;

public class GuessFragment extends Fragment {

    private View view;
    private TextView input;
    private ImageButton imageButtonBack,imageButtonInput;
    private TextView tv0,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    private SharedPreferences prefs;
    private ListView lv;
    private ArrayList<String> items;
    private ArrayAdapter<String> adeptet;

    public static int inputSize;
    public static boolean same_digit;
    public static ArrayList<Integer> answer;
//    private String xAxB;
//    private int tmp;

    private boolean is_NewGame = false;

    public static GuessFragment newInstance(int index){
        // index == 0 mean new game
        GuessFragment guessFragment = new GuessFragment();
        if (index == 0){
            guessFragment.is_NewGame = true;
        }

        return guessFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_guess, container, false);
        prefs = getActivity().getPreferences(1);
//        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        myfindViewById();

        // init input Hint
        inputSize = prefs.getInt(KeyCollectiot.KEY_INPUT_SIZE,4);
        same_digit= prefs.getBoolean(KeyCollectiot.KEY_MODES_DIFFERENT,false);

        input.setText("");
        if(!same_digit) input.setHint(getString(R.string.Input_hint_input) + inputSize + getString(R.string.Input_hint_different));
        else  input.setHint(getString(R.string.Input_hint_input) + inputSize + getString(R.string.Input_hint_digits));

        if (is_NewGame) random(inputSize,same_digit);

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

    public static void random(int size, boolean sameDigit) {
        int tmp;
        ArrayList<Integer> randomList = new ArrayList<>();

        if (!sameDigit){
            for (int i = 0; i < size; i++) {
                do {
                    tmp = (int) (Math.random() * 10);
                } while (randomList.contains(tmp));
                randomList.add(tmp);
            }
        }else {
            for (int i = 0; i < size; i++) {
                tmp = (int) (Math.random() * 10);
                randomList.add(tmp);
            }
        }
        answer = randomList;
    }

    private String match(){
        int a = 0,b = 0,tmpI;
        int[] ans_num = new int[10],guess_num = new int[10];
        for (int i = 0; i < inputSize; i++){
            tmpI = Integer.parseInt(input.getText().toString().substring(i, i + 1));
            ans_num[answer.get(i)]++;
            guess_num[tmpI]++;
            if (tmpI == answer.get(i)) {
                ++a;
                --b;
            }
        }
        for (int i = 0; i < 10 ;i++){
            if (ans_num[i] != 0 && guess_num[i] != 0){
                b += Math.min(ans_num[i],guess_num[i]);
            }
        }
        /*
        ArrayList<Integer> guessList= new ArrayList<Integer>(),indexOfMachedNum = new ArrayList<Integer>();
        for (int i = 0; i < inputSize; i++){
            tmpi = Integer.parseInt(input.getText().toString().substring(i, i + 1));
            guessList.add(tmpi);
            if (tmpi == answer.get(i)) {
                a++;
                indexOfMachedNum.add(i);
            }
        }
        //  j   is index of guessList
        for (int j = 0; j < inputSize ; j++){
            for (int i = 1; i < inputSize; i++){// (i+j)%4  is index of answer
                if (indexOfMachedNum.contains((i+j)%4)) continue;
                if(guessList.get(j) == answer.get((i+j)%4)) {
                    b++;
                    indexOfMachedNum.add((i+j)%4);
                    break;
                }
            }
        }

        xAxB = a + "A" + b + "B";*/
        return a + "A" + b + "B" ;
    }

    public static Boolean isNumbers(String tmpS){
        String tmp;
        for (int i = 0; i < inputSize; i++) {
            for (int n =0; n <10;n++){
                tmp = "" + n;
                if (tmpS.substring(i, i + 1)== tmp) return false;
            }
        }
        return true;
    }
    //check input has make sense (include inputsize and different digits)
    public static Boolean isMatch(String tmpS){
        if (tmpS.length() != inputSize) return false;
        return isNumbers(tmpS);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageButtonBack:
                    int length = input.getText().toString().length();
                    if (length > 0) input.setText(input.getText().toString().substring(0, length - 1));

                    break;
                case R.id.imageButtonInput:
                    String tmpS = input.getText().toString();
                    if (isMatch(tmpS)) {
                        String xAxB = match();
                        items.add(input.getText().toString() + "  ...  " + xAxB);
                        input.setText("");
                        lv.setAdapter(adeptet);

                        if (xAxB.equals(inputSize+"A0B")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle(getString(R.string.Dialog_Congratulation))
                                    .setPositiveButton(R.string.action_newGame, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            init();
                                        }
                                    })
                                    .setMessage(R.string.Dialog_guessed)
                                    .show();
                        }

                    } else {
                        Toast.makeText(v.getContext(), getString(R.string.Dialog_makeSense), Toast.LENGTH_SHORT).show();
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
