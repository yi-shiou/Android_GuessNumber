package com.example.user.guessnumber;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class GuessFragment extends Fragment {

    private View view;
    private TextView input;
    private ImageButton imageButtonBack,imageButtonInput;
    private TextView tv0,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    private ListView lv;
    private ArrayList<String> items;
    private ArrayAdapter<String> adeptet;

    public static int inputSize = 4;
    public static boolean same_digit = false;
    public static ArrayList<Integer> answer;
    private String xAxB;
    private int tmp;

    public static GuessFragment newInstance(int index){
        GuessFragment guessFragment = new GuessFragment();
        if (index==0) random(same_digit);

        return guessFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_guess, container, false);

        myfindViewById();
        input.setText("");
        if(!same_digit) input.setHint(getString(R.string.Inpute_hint_input) + inputSize + getString(R.string.Inpute_hint_different));
        else  input.setHint(getString(R.string.Inpute_hint_input) + inputSize + getString(R.string.Inpute_hint_digits));
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

    public static void random(boolean sameDigit) {
        int tmp;
        ArrayList<Integer> randomList = new ArrayList<>();

        if (!sameDigit){
            for (int i = 0; i < inputSize; i++) {
                do {
                    tmp = (int) (Math.random() * 10);
                } while (randomList.contains(tmp));
                randomList.add(tmp);
            }
        }else {
            for (int i = 0; i < inputSize; i++) {
                tmp = (int) (Math.random() * 10);
                randomList.add(tmp);
            }
        }
        answer = randomList;
    }

    private String match(){
        int a = 0,b = 0,tmpi;
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

        xAxB = a + "A" + b + "B";
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
