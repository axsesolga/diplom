package com.diploma.client.data.model;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import com.diploma.client.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Picture {
    public int id;
    public String base64string;
    public String description;
    public int artist_id;
    public ArrayList<Artwork.Genre> genres;
    public ArrayList<Artwork.Style> styles;

    public Bitmap getBitmap()
    {

        byte[] decodedString = Base64.decode(base64string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return  decodedByte;
    }

    public Picture(int id, String base64string, String description, int artist_id, ArrayList<Artwork.Genre> genres, ArrayList<Artwork.Style> styles) {
        this.id = id;
        this.base64string = base64string;
        this.description = description;
        this.artist_id = artist_id;
        this.genres = genres;
        this.styles = styles;
    }


    public static String fileToBase64(Uri uri, ContentResolver contentResolver) throws IOException {
        InputStream in = contentResolver.openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        byte[] bytes = byteBuffer.toByteArray();

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    public File fileFromBase64(final Context context) throws IOException {
        final byte[] imgBytesData = android.util.Base64.decode(this.base64string,
                android.util.Base64.DEFAULT);

        final File file = File.createTempFile("image_" + Integer.toString(this.id), null, context.getCacheDir());
        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
