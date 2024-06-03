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
        holder.setOnClickListener(noteModel);
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }

    public void setNoteList(List<NoteModel> noteList) {
        this.noteModelList = noteList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LayyBinding binding;
        private final SelectListener selectListener;

        public MyViewHolder(LayyBinding binding, SelectListener selectListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.selectListener = selectListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectListener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            selectListener.onItemClicked(pos);
                        }
                    }
                }
            });
        }

        public void bind(NoteModel noteModel) {
            binding.textViewUpper.setText(noteModel.getTitle());
            binding.textViewLower.setText(noteModel.getContent());
        }

        public void setOnClickListener(NoteModel noteModel) {
            binding.idlayy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inn = new Intent(v.getContext(), MainActivity2.class);
                    inn.putExtra("ttle", noteModel.getTitle());
                    inn.putExtra("contnt", noteModel.getContent());
                    inn.putExtra("noteid", noteModel.getId());
                    v.getContext().startActivity(inn);
                }
            });
        }
    }
}
