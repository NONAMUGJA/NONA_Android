package com.dgsw.nona.task;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ImageUploadTask extends AsyncTask<BitmapDrawable, Integer, String> {
    private TaskListener taskListener;

    @Override
    protected String doInBackground(BitmapDrawable... bitmapDrawables) {
        HttpURLConnection urlConnection = null;
        Bitmap bitmap = bitmapDrawables[0].getBitmap();
        try {
            String attachmentName = "myFile";
            String attachmentFileName = "myFile";
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            // request 준비
            URL url = new URL("http://busan-c.iptime.org/nona/storage/img/upload.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Cache-Control", "no-cache");
            urlConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            urlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);

            // content wrapper시작
            DataOutputStream request = new DataOutputStream(
                    urlConnection.getOutputStream());

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    attachmentName + "\";filename=\"" +
                    attachmentFileName + "\"" + crlf);
            request.writeBytes(crlf);

            // Bitmap을 ByteBuffer로 전환
            byte[] pixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
            for (int i = 0; i < bitmap.getWidth(); ++i) {
                for (int j = 0; j < bitmap.getHeight(); ++j) {
                    //we're interested only in the MSB of the first byte,
                    //since the other 3 bytes are identical for B&W images
                    pixels[i + j] = (byte) ((bitmap.getPixel(i, j) & 0x80) >> 7);
                }
            }
            request.write(pixels);

            // content wrapper종료
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary +
                    twoHyphens + crlf);

            // buffer flush
            request.flush();
            request.close();

            // Response받기
            InputStream responseStream = new
                    BufferedInputStream(urlConnection.getInputStream());
            BufferedReader responseStreamReader =
                    new BufferedReader(new InputStreamReader(responseStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();

            //Response stream종료
            responseStream.close();
            Log.d("TAG", "doInBackground: " + stringBuilder.toString());
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (taskListener != null) taskListener.onTaskFinished(result);
    }

    public ImageUploadTask setTaskListener(TaskListener taskListener) {
        taskListener = taskListener;
        return this;
    }

    public interface TaskListener {
        void onTaskFinished(String result);
    }
}
