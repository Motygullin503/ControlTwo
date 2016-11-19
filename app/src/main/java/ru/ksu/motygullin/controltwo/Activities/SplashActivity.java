package ru.ksu.motygullin.controltwo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import ru.ksu.motygullin.controltwo.AsyncFragment;
import ru.ksu.motygullin.controltwo.R;
import ru.ksu.motygullin.controltwo.TaskInterface;

public class SplashActivity extends AppCompatActivity implements TaskInterface {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        getFragment().startTask();



    }

    @Override
    public void onTaskStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpgrade(int i) {

    }

    @Override
    public void onFinish() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private AsyncFragment getFragment(){
        AsyncFragment fragment = (AsyncFragment) getSupportFragmentManager().findFragmentByTag(AsyncFragment.class.getName());
        if(fragment ==null){
            fragment = new AsyncFragment();
            getSupportFragmentManager().beginTransaction().add(fragment, AsyncFragment.class.getName()).commit();
        }
        return fragment;
    }
}
