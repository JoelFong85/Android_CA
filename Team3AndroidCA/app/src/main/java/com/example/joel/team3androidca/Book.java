package com.example.joel.team3androidca;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book extends HashMap<String, String> {

    final static String baseURL = "http://172.17.42.39/BookJSONWebService/Service1.svc";
    final static String imageURL = "http://172.17.42.39/ImageService/Service1.svc/GetImage";

    // Book model
    public Book(String title, String author, String isbn, String category, String price, String stock, String bookId) {
        put("Title", title);
        put("Author", author);
        put("ISBN", isbn);
        put("CategoryID", category);
        put("Price", price);
        put("Stock", stock);
        put("BookID", bookId);

    }

    public Book() {
    }


    //Adding list of bookId (emp id) from JSON svc to a list.
    public static List<String> list() {
        List<String> list = new ArrayList<String>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "/Books");
        try {
            for (int i = 0; i < a.length(); i++) {
                String bookId = a.getString(i);
                JSONObject b = JSONParser.getJSONFromUrl(baseURL + "/Book/" + bookId);
                list.add(b.getString("Title"));
            }
        } catch (Exception e) {
            Log.e("Book.list()", "JSONArray error");
        }
        return (list);
    }

    //Get list of Searched books
    public static List<String> getSearchedBooksList(String searchText) {
        List<String> list = new ArrayList();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "/Search/" + searchText);
            for (int i = 0; i < a.length(); i++) {
                JSONObject c = (JSONObject) a.get(i);
                list.add(c.getString("Title"));
            }
        } catch (Exception e) {
            System.err.println("Parse Error");
        }
        return list;
    }

    //getting Book Id from Title
    public static String getBookIdFromTitle(String title) {
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "/Books");
        try {
            for (int i = 0; i < a.length(); i++) {
                String book = a.getString(i);
                JSONObject b = JSONParser.getJSONFromUrl(baseURL + "/Book/" + book);

                if (b.getString("Title").equals(title)) {
                    return b.getString("BookID");
                }
            }
        } catch (Exception e) {
            Log.e("Book.getBookIdFromTitle", "JSONArray error");
        }
        return (null);
    }

    //getting Book obj from bookID
    public static Book getBook(String bookId) {

        JSONObject b = JSONParser.getJSONFromUrl(baseURL + "/Book/" + bookId);
        try {
            return new Book(b.getString("Title"), b.getString("Author"),
                    b.getString("ISBN"), b.getString("CategoryID"), b.getString("Price"), b.getString("Stock"), b.getString("BookID"));
        } catch (Exception e) {
            Log.e("Book.getBook()", "JSONArray error");
        }
        return (null);
    }


    //getting Book cover from bookId
    public static Bitmap getPhoto(String isbn) {
        try {
            URL url = new URL(String.format("%s/%s", imageURL, isbn));
            URLConnection conn = url.openConnection();
            InputStream ins = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Book.getPhoto()", "Bitmap error");
        }
        return (null);
    }


    public static void updateBook(Book book) {
        JSONObject jbook = new JSONObject();
        try {
            jbook.put("Author", book.get("Author"));
            jbook.put("BookID", Integer.parseInt(book.get("BookID")));
            jbook.put("CategoryID", Integer.parseInt(book.get("CategoryID")));
            jbook.put("ISBN", book.get("ISBN"));
            BigDecimal bd = new BigDecimal(book.get("Price"));
            jbook.put("Price", bd);
            jbook.put("Stock", Integer.parseInt(book.get("Stock")));
            jbook.put("Title", book.get("Title"));

        } catch (Exception e) {
        }
        String result = JSONParser.postStream(baseURL + "/Update", jbook.toString());
    }

}
