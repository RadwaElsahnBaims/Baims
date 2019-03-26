package com.baims.app.presentation.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.baims.app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * Created by Radwa Elsahn on 12/8/2018.
 */
public class Base64Converter {

    public static String encodeAudio(String selectedPath) {

        byte[] audioBytes;
        try {

            // Just to check file size.. Its is correct i-e; Not Zero
            File audioFile = new File(selectedPath);
            long fileSize = audioFile.length();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(new File(selectedPath));
            byte[] buf = new byte[9999];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
            audioBytes = baos.toByteArray();

            // Here goes the Base64 string
            return Base64.encodeToString(audioBytes, Base64.DEFAULT);

        } catch (Exception e) {

        }
        return "";
    }

    public static String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //    public static String encodeFileToBase64Binary(String filePath) {
//        File originalFile = new File(filePath);
//        String encodedBase64;
//        try {
//            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
//            byte[] bytes = new byte[(int) originalFile.length()];
//            int numBytesRead = fileInputStreamReader.read(bytes);
//            if (numBytesRead != -1) {
//                Log.d("TAG", "encodeFileToBase64Binary: could not read all the file");
//                return null;
//            }
//
////            String value = Base64.encodeToString(bytes, Base64.DEFAULT);
//
////            byte[] data = text.getBytes("UTF-8");
////            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
////
//            encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
//            return encodedBase64;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}
