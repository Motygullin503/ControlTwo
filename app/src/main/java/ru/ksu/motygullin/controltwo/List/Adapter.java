package ru.ksu.motygullin.controltwo.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.ksu.motygullin.controltwo.InfoFragment;
import ru.ksu.motygullin.controltwo.Note;
import ru.ksu.motygullin.controltwo.NotesProvider;
import ru.ksu.motygullin.controltwo.R;

/**
 * Created by UseR7 on 19.11.2016.
 */

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Note> mNotes;
    private FragmentActivity activity;
    FragmentManager fragmentManager;

    public Adapter(FragmentActivity activity) {
        this.activity = activity;
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new MyViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Note user = mNotes.get(position);
        ((MyViewHolder) holder).bind(user);

        ((MyViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment fragment = InfoFragment.newInstance(user);
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction()
                        .replace(R.id.info_land, fragment, InfoFragment.class.getName())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        mNotes = getNotes();
        return mNotes.size();
    }

    private List<Note> getNotes (){

            return NotesProvider.getInstance(activity).getNotesList();
    }
}
