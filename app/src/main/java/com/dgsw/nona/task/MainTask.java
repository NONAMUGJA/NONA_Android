package com.dgsw.nona.task;

import android.os.AsyncTask;
import android.util.Log;

import com.dgsw.nona.data.BoxData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

public class MainTask extends AsyncTask<Void, Integer, BoxData[]> {
    private TaskListener taskListener;

    @Override
    protected BoxData[] doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BoxData[] boxDataArray = new BoxData[9];
        try {
            URL url = new URL("http://busan-c.iptime.org/nona/storage");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            InputStream inputStream = urlConnection.getInputStream();

            StringBuilder stringBuilder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                stringBuilder.append(inputLine).append("\n");
            if (stringBuilder.length() == 0) {
                return null;
            }

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_INTERNAL_ERROR) {
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                for (int i = 0; i < 9; i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        boxDataArray[i] = new BoxData(jsonObject.getString("sender"), jsonObject.getString("imageId"), jsonObject.getString("comment"), jsonObject.getString("receiver"), jsonObject.getString("itemTitle"), jsonObject.getString("count"), jsonObject.getString("lockpw"), null);
                    } catch (JSONException ignored) {
                    }
                }
                return boxDataArray;
            }
            inputStream.close();
            reader.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            cancel(true);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(BoxData[] boxDataArray) {
        super.onPostExecute(boxDataArray);
        if (taskListener != null) taskListener.onTaskFinished(boxDataArray);
    }

    public MainTask setTaskListener(TaskListener taskListener) {
        this.taskListener = taskListener;
        return this;
    }

    public interface TaskListener {
        void onTaskFinished(BoxData[] boxDataArray);
    }
}
