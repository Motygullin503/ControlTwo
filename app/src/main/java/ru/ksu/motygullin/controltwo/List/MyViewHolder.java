package ru.ksu.motygullin.controltwo.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.ksu.motygullin.controltwo.Note;
import ru.ksu.motygullin.controltwo.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private TextView mIdView;
    private TextView mContentView;
    private Context context;

    public MyViewHolder(View view, Context context) {
        super(view);
        this.context = context;
        mIdView = (TextView) view.findViewById(R.id.name);
        mContentView = (TextView) view.findViewById(R.id.small_note);
    }
    public void bind(Note note){
        mIdView.setText(note.getName());
        mContentView.setText(note.getText());
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }
}
