package com.skripsigg.heyow.utils.photos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Aldyaz on 12/2/2016.
 */

public class ImageBase64 {
    final String TAG = this.getClass().getSimpleName();

    public static String encode(Bitmap bitmap){
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        int quality = 100; //100: compress nothing
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bao);

        if(!bitmap.isRecycled()){//important! prevent out of memory
            bitmap.recycle();
            bitmap = null;
        }

        byte [] ba = bao.toByteArray();
        return Base64.encodeToString(ba, Base64.DEFAULT);
    }

    public static Bitmap decode(String encodedImage){
        final String pureBase64Encoded = encodedImage.substring(encodedImage.indexOf(",")  + 1);
        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.URL_SAFE);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
