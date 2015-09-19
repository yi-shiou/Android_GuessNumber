package com.example.user.guessnumber;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.guessnumber.dummy.modle.KeyCollectiot;

/**
 * Created by npes87184 on 2015/9/19.
 */
public class ScoreFragment extends Fragment {

    private View view;
    private TextView score;
    private TextView highest_detail;
    private TextView average_detail;
    private TextView average_score;
    private SharedPreferences prefs;

    public static ScoreFragment newInstance() {
        ScoreFragment scoreFragment = new ScoreFragment();
        return scoreFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_score, container, false);
        prefs = getActivity().getPreferences(1);
        myfindViewById();
        if(prefs.getInt(KeyCollectiot.KEY_HIGHEST_SCORE, Integer.MAX_VALUE)!=Integer.MAX_VALUE) {
            score.setText(String.valueOf(prefs.getInt(KeyCollectiot.KEY_HIGHEST_SCORE, Integer.MAX_VALUE)));
        } else {
            score.setText(getString(R.string.none));
        }

        if(prefs.getFloat(KeyCollectiot.KEY_AVERAGE_SCORE, Integer.MAX_VALUE)!=Integer.MAX_VALUE) {
            average_score.setText(String.valueOf(prefs.getFloat(KeyCollectiot.KEY_AVERAGE_SCORE, Integer.MAX_VALUE)));
        } else {
            average_score.setText(getString(R.string.none));
        }

        return view;
    }



    private void myfindViewById() {
        score = (TextView)view.findViewById(R.id.high_score);
        highest_detail = (TextView)view.findViewById(R.id.textView2);
        average_detail = (TextView)view.findViewById(R.id.textView10);
        average_score = (TextView)view.findViewById(R.id.textView11);

        final float scale = getResources().getDisplayMetrics().density;
        int size = (int)(8 * scale);
        highest_detail.setTextSize(size);
        average_detail.setTextSize(size);
        average_score.setTextSize(size);
        score.setTextSize(size);
    }

}
