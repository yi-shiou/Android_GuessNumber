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
    private TextView tv0;
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

        return view;
    }


    private void myfindViewById() {
        score = (TextView)view.findViewById(R.id.high_score);
        tv0 = (TextView)view.findViewById(R.id.textView2);

        final float scale = getResources().getDisplayMetrics().density;
        int size = (int)(7 * scale);
        tv0.setTextSize(size);
        score.setTextSize(size);
    }

}
