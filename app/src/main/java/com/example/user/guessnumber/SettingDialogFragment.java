package com.example.user.guessnumber;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user on 2015/9/12.
 */
public class SettingDialogFragment extends DialogFragment {

    private String mTitle="";

//    public SettingDialogFragment(){
//
//    }
    public static String ttmpS="0";
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v;

        switch (getArguments().getInt("index")) {
            case 0://  rule
                v = inflater.inflate(R.layout.fragment_rule, null);
                builder.setView(v)//.setPositiveButton(R.string.sure,dCilckListener)
                        .setNegativeButton(R.string.sure, dCilckListener);
                break;
            case 1:
                v = inflater.inflate(R.layout.fragment_rule, null);
                builder.setTitle("On contruction").setView(v)//.setPositiveButton(R.string.sure,dCilckListener)
                        .setNegativeButton(R.string.quit, dCilckListener);
                break;
            case 2:
//                ArrayList<Integer> answer = getArguments().getIntegerArrayList("answer");
                String tmpS = "testtttttttttttttttt";
//                for(int i=0;i<4;i++){
//                    tmpS += answer.get(i) ;
//                }
                Toast.makeText(getActivity(), tmpS, Toast.LENGTH_SHORT).show();
                v = inflater.inflate(R.layout.fragment_rule, null);
                builder.setView(v)//.setPositiveButton(R.string.sure,dCilckListener)
                        .setNegativeButton(R.string.sure, dCilckListener);
                break;
        }


        if (!mTitle.equals("")) builder.setTitle(mTitle);
        return builder.create();


//        return new Dialog(getActivity(), getTheme());
    }

    public void setTitle(String title){
        mTitle = title;
    }

    private DialogInterface.OnClickListener dCilckListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case 0:
                    break;
                case 1:

                    break;
            }
        }
    };
}
