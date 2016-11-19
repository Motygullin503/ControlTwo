package ru.ksu.motygullin.controltwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.ksu.motygullin.controltwo.Activities.EditActivity;
import ru.ksu.motygullin.controltwo.Activities.MainActivity;

public class InfoFragment extends Fragment {

    public static final int NAME_EDIT_KEY = 101;
    public static final int TEXT_EDIT_KEY = 102;

    private int position;
    private Note note;
    private TextView tvName;
    private TextView tvText;
    private Button editButton;

    public static InfoFragment newInstance(Note note) {

        Bundle args = new Bundle();
        args.putSerializable("note", note);
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_info, container, false);

        final Note note = (Note) getArguments().getSerializable("note");
        position = getArguments().getInt("position");

        tvName = (TextView) v.findViewById(R.id.item_name);
        tvText = (TextView) v.findViewById(R.id.item_text);
        editButton = (Button) v.findViewById(R.id.edit);

        tvName.setText(note.getName());
        tvText.setText(note.getText());

        fillInformation(note);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra("note", note);
                startActivityForResult(intent, position);
            }
        });
        return v;
    }

    public void fillInformation(Note note) {
        this.note = note;
        if (note != null) {
            tvName.setText(note.getName());
            tvText.setText(note.getText());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == EditActivity.EDITED_KEY) {
            Note notification = (Note) data.getSerializableExtra("note");
            if (notification != null) {
                List<Note> notifications = NotesProvider.getInstance(getContext()).getNotesList();
                notifications.get(requestCode).text = notification.text;
                notifications.get(requestCode).name = notification.name;
                NotesProvider.getInstance(getContext()).saveContacts(notifications);
                this.note = notification;
                fillInformation(note);
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).NotifyDataSetChanged();
                }
                Toast.makeText(getActivity(), "Notification changed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}