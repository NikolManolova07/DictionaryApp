package com.nikolmanolova.dictionary.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikolmanolova.dictionary.R;

public class PhoneticsViewHolder extends RecyclerView.ViewHolder {
    public TextView textView_phonetics;
    public ImageButton btnAudio;

    public PhoneticsViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_phonetics = itemView.findViewById(R.id.textView_phonetics);
        btnAudio = itemView.findViewById(R.id.btnAudio);
    }
}
