package com.example.joel.team3androidca;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<String> {
    private List<String> items;
    int resource;

    public MyAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);

        String bookId = items.get(position);
        TextView e = (TextView) v.findViewById(R.id.textView);
        e.setText(bookId);

//        final ImageView image = (ImageView) v.findViewById(R.id.imageView2);
//
//        if (bookId != null) {
//
//            new AsyncTask<String, Void, Bitmap>() {
//                @Override
//                protected Bitmap doInBackground(String... params) {
//                    return Book.getPhoto(params[0]);
//                }
//
//                @Override
//                protected void onPostExecute(Bitmap result) {
//                    image.setImageBitmap(result);
//                }
//            }.execute(bookId);
//
//        }
        return v;
    }
}
