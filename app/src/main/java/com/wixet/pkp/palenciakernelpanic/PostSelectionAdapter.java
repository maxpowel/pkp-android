package com.wixet.pkp.palenciakernelpanic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class PostSelectionAdapter extends BaseAdapter {
    private Context mContext;
    private Post[] data;
    private boolean selected = false;
    private boolean reload[];

    public PostSelectionAdapter(Context c, Post[] data) {
        mContext = c;
        this.data = data;
        reload = new boolean[data.length];
    }

    public int getCount() {
        //return mThumbIds.length;
        return data.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;


        if (convertView == null || reload[position]) {
            reload[position] = false;
            Log.d("LOOOG", "RECARGA " + position + " " + selected);
            // get layout from grid_item.xml ( Defined Below )

            gridView = inflater.inflate( R.layout.post_selection_view , null);

            // set value into textview


            final TextView textView = (TextView) gridView
                    .findViewById(R.id.textView3);

            textView.setText("A" + position);

            final TextView textView33 = (TextView) gridView
                    .findViewById(R.id.textView33);

            final Button boton = (Button) gridView.findViewById(R.id.view);
            if(data[position].getOwner() == null && !selected)
                boton.setVisibility(View.VISIBLE);

            if(selected) {
                Log.d("CAMBIANDO","OCULANDO BOTs "+position);
                textView33.setVisibility(View.VISIBLE);
                boton.setVisibility(View.GONE);
                textView33.setText(data[position].getOwner());
            }



            final int po = position;
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boton.setVisibility(View.GONE);
                    Log.d("CAMBAIDN", "LA DE " + po);
                    data[po].setOwner("YO "+po);
                    selected = true;
                    for(int i=0; i < reload.length; i++){
                        reload[i] = true;
                    }
                    notifyDataSetInvalidated();
                }
            });



            // set image based on selected text

            //ImageView imageView = (ImageView) gridView
            //        .findViewById(R.id.grid_item_image);

            //String arrLabel = gridValues[ position ];
/*
            if (arrLabel.equals("Windows")) {

                imageView.setImageResource(R.drawable.windows_logo);

            } else if (arrLabel.equals("iOS")) {

                imageView.setImageResource(R.drawable.ios_logo);

            } else if (arrLabel.equals("Blackberry")) {

                imageView.setImageResource(R.drawable.blackberry_logo);

            } else {

                imageView.setImageResource(R.drawable.android_logo);
            }
*/
        } else {

            gridView = (View) convertView;
        }

        return gridView;

    }


}
