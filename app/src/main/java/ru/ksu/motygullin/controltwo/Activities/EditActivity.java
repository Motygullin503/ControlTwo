package ru.ksu.motygullin.controltwo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import ru.ksu.motygullin.controltwo.AsyncFragment;
import ru.ksu.motygullin.controltwo.Note;
import ru.ksu.motygullin.controltwo.R;
import ru.ksu.motygullin.controltwo.TaskInterface;

public class EditActivity extends AppCompatActivity implements TaskInterface {

    public static final int EDITED_KEY = 101;
    public static final int NOT_EDITED_KEY = 102;
    private static final String TAG = "edit";
    private Note note;
    private EditText etTitle;
    private EditText etText;
    private Button btnEdit;
    private Button btnCancel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etText = (EditText) findViewById(R.id.editNoteText);
        etTitle = (EditText) findViewById(R.id.editText);
        btnEdit = (Button) findViewById(R.id.btnOK);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if(getTask().isRunning()){
            progressBar.setVisibility(View.VISIBLE);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTask().startTask();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(NOT_EDITED_KEY);
                finish();
            }
        });

        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        if(note != null){
            etText.setText(note.getText());
            etTitle.setText(note.getName());
        }
    }

    @Override
    public void onTaskStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpgrade(int i) {
        progressBar.setProgress(i);
    }

    @Override
    public void onFinish() {
        progressBar.setVisibility(View.GONE);
        if(etText.getText().toString().equals(note.getText()) && etTitle.getText().toString().equals(note.getName())){
            setResult(NOT_EDITED_KEY);
        } else {
            Intent intent = new Intent();
            Note note = new Note(etTitle.getText().toString(), etText.getText().toString());
            intent.putExtra("note", note);
            setResult(EDITED_KEY, intent);
        }
        finish();
    }
    public AsyncFragment getTask() {
        AsyncFragment someStupidTask = (AsyncFragment) getSupportFragmentManager().findFragmentByTag(AsyncFragment.class.getName() + TAG);
        if(someStupidTask == null){
            someStupidTask = new AsyncFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(someStupidTask, AsyncFragment.class.getName() + TAG)
                    .commit();
        }
        return someStupidTask;
    }
}
