package com.example.android.AmritaResouce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.AmritaResouce.R;
import com.example.android.AmritaResouce.models.UploadDocumentModel;

import java.util.List;

public class UploadDocumentAdapter extends RecyclerView.Adapter<UploadDocumentAdapter.UploadDocumentViewholder> {

    Context context;
    List<UploadDocumentModel> DocumentList;

    public UploadDocumentAdapter(Context context, List<UploadDocumentModel> documentList) {
        this.context = context;
        DocumentList = documentList;
    }

    @NonNull
    @Override
    public UploadDocumentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UploadDocumentViewholder(
                LayoutInflater.from(context).inflate(R.layout.item_holder_recycler_home, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UploadDocumentViewholder holder, int position) {
        UploadDocumentModel document = DocumentList.get(position);

        holder.docTitle.setText(document.getTitle());
        holder.docSem.setText(document.getSem());
        holder.docAuthor.setText(document.getUserName());
        holder.docSubject.setText(document.getSubject());

    }

    @Override
    public int getItemCount() {
        return DocumentList.size();
    }

    public class UploadDocumentViewholder extends RecyclerView.ViewHolder {
        TextView docTitle, docSem, docAuthor, docSubject;

        public UploadDocumentViewholder(@NonNull View itemView) {
            super(itemView);

            docTitle = itemView.findViewById(R.id.tv_reycler_home_item_title);
            docSem = itemView.findViewById(R.id.tv_reycler_home_sem);
            docAuthor = itemView.findViewById(R.id.tv_reycler_home_author);
            docSubject = itemView.findViewById(R.id.tv_recycler_home_subject);
        }
    }
}
