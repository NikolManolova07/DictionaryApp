package com.nikolmanolova.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikolmanolova.dictionary.data.HistoryEntity;
import com.nikolmanolova.dictionary.data.HistoryRepository;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    LinearLayout historyLayout;
    HistoryRepository historyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyLayout = findViewById(R.id.historyLayout);
        historyRepository = new HistoryRepository(this);

        refresh();
    }

    private void refresh() {
        List<HistoryEntity> data = historyRepository.getAll();

        historyLayout.removeAllViews();

        for (HistoryEntity entity : data) {
            final LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setPadding(20,20,20,20);

            final TextView textView = new TextView(this);
            textView.setText(entity.getWord());
            textView.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);
            textView.setPadding(40,0,0,0);

            ImageButton btnDelete = new ImageButton(this);
            btnDelete.setBackgroundResource(R.drawable.ic_delete);
            btnDelete.setTag(entity.getId());
            btnDelete.setOnClickListener(onDeleteClicked);

            layout.addView(btnDelete);
            layout.addView(textView);

            historyLayout.addView(layout);
        }
    }

    View.OnClickListener onDeleteClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String idString = v.getTag().toString();
            int id = Integer.parseInt(idString);
            historyRepository.delete(id);

            refresh();
        }
    };
}