package com.samdroid.notesv2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class converters {

    @TypeConverter
    public String fromBitmap(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        String imageEncoded = Base64.encodeToString(bytes, Base64.DEFAULT);
        return imageEncoded;

    }

    @TypeConverter
    public Bitmap toBitmap(String encodedImage){
        byte[] decodedByte = Base64.decode(encodedImage, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @TypeConverter
    public String get_string(List<String> str) {
        if (str == null)
            return null;
        StringBuilder string = new StringBuilder();
        for (String s : str) string.append(s).append(",");
        return string.toString();
    }

    @TypeConverter
    public List<String> set_string(String str) {
        if (str == null)
            return null;
        return new ArrayList<>(Arrays.asList(str.split(",")));
    }


}
