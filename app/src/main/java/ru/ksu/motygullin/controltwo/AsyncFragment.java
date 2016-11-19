package ru.ksu.motygullin.controltwo;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by UseR7 on 19.11.2016.
 */

public class AsyncFragment extends Fragment{
    private TaskInterface taskInterface;
    private NewAsyncTask task;

    public boolean isRunning(){
        return task!=null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        setTaskInterface(context);
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        setTaskInterface(activity);
        super.onAttach(activity);
    }

    public void startTask(){
        if(task ==null){
            task = new NewAsyncTask();
            task.execute();
        }
    }

    public void stopTask(){
        if(task != null){
            task.cancel(true);
            task = null;
        }
    }

    private void setTaskInterface(Context context){
        if(context instanceof TaskInterface) {
            taskInterface = (TaskInterface) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        taskInterface = null;
    }

    public class NewAsyncTask extends AsyncTask<Void, Integer, Void>{

        @Override
        protected void onPreExecute() {
           if (taskInterface!=null){
               taskInterface.onTaskStart();
           }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(taskInterface !=null){
                taskInterface.onUpgrade(values[0]);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int randomProgress = 0;
                // Simulate some heavy work.
                while (randomProgress < 100 && !isCancelled()) {
                    randomProgress += 5;
                    publishProgress(randomProgress);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            task = null;
            if(taskInterface!=null){
                taskInterface.onFinish();
            }
        }

    }
}
