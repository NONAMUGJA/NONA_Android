package com.dgsw.nona.task;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.dgsw.nona.data.BoxData;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<Void, Integer, BoxData[]> {
    private TaskListener taskListener;

    private Exception exception;

    private WeakReference<Activity> activityWeakReference;

    private BoxData[] boxDataArray;

    public ImageLoadTask(Activity activity, BoxData[] boxDataArray) {
        this.activityWeakReference = new WeakReference<>(activity);
        this.boxDataArray = boxDataArray;
    }

    @Override
    protected BoxData[] doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        for (int i = 0; i < 9; i++)
            try {
                URL url = new URL("http://busan-c.iptime.org/nona/storage/img/uploads/" + boxDataArray[i].getImageID());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                InputStream inputStream = urlConnection.getInputStream();

                //이미지 용량 줄이기
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);

                if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    if (bitmap != null)
                        boxDataArray[i].setDrawable(new BitmapDrawable(activityWeakReference.get().getResources(), bitmap));
                    else
                        boxDataArray[i].setDrawable(null);
                }
                inputStream.close();
            } catch (IOException e) {
                exception = e;
                e.printStackTrace();
                cancel(true);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        return boxDataArray;
    }

    @Override
    protected void onPostExecute(BoxData[] results) {
        super.onPostExecute(results);
        if (taskListener != null) taskListener.onTaskFinished(results);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (taskListener != null) taskListener.onTaskExceptionCanceled(exception);
    }

    public ImageLoadTask setTaskListener(TaskListener taskListener) {
        this.taskListener = taskListener;
        return this;
    }

    public interface TaskListener {
        void onTaskFinished(BoxData[] results);
        void onTaskExceptionCanceled(Exception e);
    }
}
