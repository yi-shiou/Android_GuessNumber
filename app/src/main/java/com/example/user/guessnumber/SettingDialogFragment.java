package com.example.user.guessnumber;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015/9/12.
 */
public class SettingDialogFragment extends DialogFragment {

    private String mTitle="";
    private View view;
    private Boolean isaddEditText = false;

    //    public SettingDialogFragment(){
//
//    }
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        switch (getArguments().getInt("index")) {
            case 0://  rule
                view = inflater.inflate(R.layout.fragment_rule, null);
                builder.setView(view)//.setPositiveButton(R.string.sure,dCilckListener)
                        .setNegativeButton(R.string.sure, dCilckListener);
                break;
            case 1:// set
//                view = inflater.inflate(R.layout.list_setting, null);
//                mySetInflater(view);
//                if (isaddEditText) builder.setView(new EditText(this.getContext()));
//                builder.setTitle("On contruction").setView(view).setPositiveButton(R.string.sure, dCilckListener)
//                        .setNegativeButton(R.string.quit, dCilckListener);
//                if (isaddEditText) builder.setView(new EditText(this.getContext()));

//                builder.setItems(new String[] {"items1","items2"},null).setView(new EditText(view.getContext()));

                break;
            case 2:

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

/*    public void mySetInflater(View v){


        ArrayList<String> items = new ArrayList<>();
        items.add("New radon num");
        items.add("Set num");
        items.add("Show num");
        ArrayAdapter adapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1,items);
//        LiseAdapter adapter = new LiseAdapter(v.getContext(),items);
        ListView lv = (ListView) v.findViewById(R.id.listView_setting);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        GuessFragment.random(GuessFragment.different_digit);
                        break;
                    case 1:
                        isaddEditText = true;
                        break;
                    case 2:
                        String tmpS = "";
                        for(int i=0;i<4;i++){
                            tmpS += GuessFragment.answer.get(i) ;
                        }
                        Toast.makeText(getActivity(), tmpS, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


    }*/


//    public class LiseAdapter extends BaseAdapter {
//        private LayoutInflater myInflater;
//        private List<String> list;
//
//        public LiseAdapter (Context context,List<String> list){
//            myInflater = LayoutInflater.from(context);
//            this.list = list;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return list.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return list.indexOf(getItem(position));
//        }
//
//        private class ViewHolder{
//            TextView value;
//            ImageView icon;
//            public ViewHolder(TextView value,ImageView icon){
//                this.value = value;
//                this.icon = icon;
//            }
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            if (convertView==null){
//                convertView = myInflater.inflate(R.layout.list_item,null);
//                holder = new ViewHolder(
//                        (TextView) convertView.findViewById(R.id.list_text),
//                        (ImageView)convertView.findViewById(R.id.list_image)
//                );
//            }else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            holder.value.setText(list.get(position));
//            switch (position){
//                case 0:
//
//                    break;
//                case 1:
//                    break;
//                default:
//                    break;
//            }
//            return convertView;
//        }
//
//
//    }
}
