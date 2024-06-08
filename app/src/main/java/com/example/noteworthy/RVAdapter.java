package com.example.noteworthy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteworthy.databinding.LayyBinding;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private List<NoteModel> noteModelList = new ArrayList<>();
    private final SelectListener selectListener;
    private NoteClickListener noteClickListener;
    private NoteLongClickListener noteLongClickListener;

    public RVAdapter(SelectListener selectListener) {
        this.selectListener = selectListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayyBinding binding = LayyBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding, selectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoteModel noteModel = noteModelList.get(position);
        holder.bind(noteModel);
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }

    public void setNoteList(List<NoteModel> noteList) {
        this.noteModelList = noteList;
        notifyDataSetChanged();
    }
    public void setOnClickListener(NoteClickListener noteClickListener){
        this.noteClickListener = noteClickListener;

    }
    public void setOnLongClickListener(NoteLongClickListener noteLongClickListener){
        this.noteLongClickListener = noteLongClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LayyBinding binding;



        public MyViewHolder(LayyBinding binding, SelectListener selectListener) {
            super(binding.getRoot());
            this.binding = binding;

            setClickListener();
            setLongClickListener();

        }
        public void setClickListener(){
            binding.idlayy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    noteClickListener.onClick(noteModelList.get(position));
                }
            });

        }
        public void setLongClickListener(){
            binding.idlayy.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    noteLongClickListener.onLongClick(noteModelList.get(position));
                    noteModelList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,noteModelList.size());
                    return true;
                }
            });
        }

        public void bind(NoteModel noteModel) {
            binding.textViewUpper.setText(noteModel.getTitle());
            binding.textViewLower.setText(noteModel.getContent());
        }

//        public void setOnClickListener(NoteModel noteModel) {
//            binding.idlayy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), MainActivity2.class);
//                    intent.putExtra("noteId", noteModel.getId());
//                    v.getContext().startActivity(intent);
//                }
//            });

//        }
    }
    public interface NoteClickListener{
        void onClick(NoteModel noteModel);
    }
    public interface NoteLongClickListener{
        void onLongClick(NoteModel noteModel);
    }
}
