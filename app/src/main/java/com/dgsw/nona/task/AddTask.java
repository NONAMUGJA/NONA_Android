package com.dgsw.nona.task;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddTask extends AsyncTask<String, Integer, Boolean> {
    private TaskListener taskListener;

    @Override
    protected Boolean doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL("http://busan-c.iptime.org/nona/storage/add.php?no="+strings[0]+"&id="+strings[1]+"&imageId="+strings[2]+"&itemTitle="+strings[3]+"&count="+strings[4]+"comment="+strings[5]+"&lockpw="+strings[6]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            InputStream inputStream = urlConnection.getInputStream();

            StringBuilder stringBuilder = new StringBuilder();
            if (inputStream == null) {
                return false;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                stringBuilder.append(inputLine).append("\n");
            if (stringBuilder.length() == 0) {
                return false;
            }

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_INTERNAL_ERROR) {
                if (stringBuilder.toString().equals("SUCCESS"))
                return true;
            }
            inputStream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (taskListener != null) taskListener.onTaskFinished(result);
    }

    public AddTask setTaskListener(TaskListener taskListener) {
        this.taskListener = taskListener;
        return this;
    }

    public interface TaskListener {
        void onTaskFinished(Boolean result);
    }
}
