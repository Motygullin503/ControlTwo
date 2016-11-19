package ru.ksu.motygullin.controltwo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import ru.ksu.motygullin.controltwo.InfoFragment;
import ru.ksu.motygullin.controltwo.List.Adapter;
import ru.ksu.motygullin.controltwo.Note;
import ru.ksu.motygullin.controltwo.NotesProvider;
import ru.ksu.motygullin.controltwo.R;

public class MainActivity extends AppCompatActivity {


    public static final int EDITED_KEY = 111;
    public static final int NOT_EDITED_KEY = 112;
    public static final String NAME_KEY = "name";
    public static final String TEXT_KEY = "text";


    ProgressBar progressBar;
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new Adapter(MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == EditActivity.EDITED_KEY) {
            Note notification = (Note) data.getSerializableExtra("notification");
            if (notification != null) {
                List<Note> notifications = NotesProvider.getInstance(getBaseContext()).getNotesList();
                notifications.get(requestCode).text = notification.text;
                notifications.get(requestCode).name = notification.name;
                NotesProvider.getInstance(getBaseContext()).saveContacts(notifications);

                Toast.makeText(this, "Notification changed", Toast.LENGTH_SHORT).show();

                InfoFragment fragment = (InfoFragment) getSupportFragmentManager().findFragmentByTag(InfoFragment.class.getName());
                if (fragment != null) {
                    fragment.fillInformation(notification);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void NotifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }
}
