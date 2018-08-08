package com.example.joel.team3androidca;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showData("");
    }

    public void onClick(View v) {
        EditText editText = findViewById(R.id.editTextSearch);
        String text = changeSearchText(editText.getText().toString());
        showData(changeSearchText(text));
    }

    public void showData(String searchText) {
        new AsyncTask<String, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(String... params) {
                if (params[0].equals("")) {
                    return Book.list();
                } else {
                    return Book.getSearchedBooksList(params[0]);
                }
            }

            @Override
            protected void onPostExecute(List<String> result) {
                ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(),
                        R.layout.row, R.id.textView, result);
                ListView list = findViewById(R.id.listViewTitles);
                list.setAdapter(adapter);
                list.setOnItemClickListener(MainActivity.this);


//                MyAdapter adapter = new MyAdapter(MainActivity.this, R.layout.row, result);
//                setListAdapter(adapter);
            }
        }.execute(searchText);
    }

    //change to on item click
    @Override
    public void onItemClick(AdapterView<?> av, View v,
                            int position, long id) {
        String title = (String) av.getAdapter().getItem(position);
        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra("Title", title);
        startActivity(intent);
    }

    public String changeSearchText(String str) {
        String[] words = str.split(" ");
        StringBuilder sentence = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; ++i) {
            sentence.append("%20");
            sentence.append(words[i]);
        }
        return sentence.toString();
    }


}
