package com.example.joel.team3androidca;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class BookDetailsActivity extends AppCompatActivity {

    int[] ids = {R.id.editTextTitle, R.id.editTextAuthor, R.id.editTextIsbn, R.id.editTextCategory, R.id.editTextPrice, R.id.editTextStock, R.id.editTextBookId};
    String[] keys = {"Title", "Author", "ISBN", "CategoryID", "Price", "Stock", "BookID"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Intent i = getIntent();
        String title = i.getStringExtra("Title");

        //AsyncTask to retrieve & display Book obj
        AsyncTask<String, Void, Book> task = new AsyncTask<String, Void, Book>() {
            @Override
            protected Book doInBackground(String... params) {
                String bookId = Book.getBookIdFromTitle(params[0]);
                return Book.getBook(bookId);
            }

            @Override
            protected void onPostExecute(Book result) {
                show(result);
            }
        };
        task.execute(title);

        //Update Book
        Button btn = (Button) findViewById(R.id.buttonUpdate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book b = new Book();

                startActivityForResult(new Intent(BookDetailsActivity.this, MainActivity.class),  1);

                for (int i = 0; i < ids.length; i++) {
                    EditText t = (EditText) findViewById(ids[i]);
                    b.put(keys[i], t.getText().toString());
                }

                new AsyncTask<Book, Void, Void>() {
                    @Override
                    protected Void doInBackground(Book... params) {
                        Book.updateBook(params[0]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                    }
                }.execute(b);
            }
        });
    }


    void show(Book book) {
        for (int i = 0; i < keys.length; i++) {
            EditText e = (EditText) findViewById(ids[i]);
            e.setText(book.get(keys[i]));
        }

        //AsyncTask to retrieve & display picture
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                return Book.getPhoto(params[0]);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                image.setImageBitmap(result);
            }
        }.execute(book.get("ISBN"));
    }


}
