package com.nikolmanolova.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nikolmanolova.dictionary.adapters.MeaningsAdapter;
import com.nikolmanolova.dictionary.adapters.PhoneticsAdapter;
import com.nikolmanolova.dictionary.data.HistoryEntity;
import com.nikolmanolova.dictionary.data.HistoryRepository;
import com.nikolmanolova.dictionary.models.APIResponse;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    Button btnHistory;
    TextView textView_word;
    RecyclerView recycler_phonetics, recycler_meanings;
    ProgressDialog progressDialog;
    PhoneticsAdapter phoneticsAdapter;
    MeaningsAdapter meaningsAdapter;
    HistoryRepository historyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        btnHistory = findViewById(R.id.btnHistory);
        textView_word = findViewById(R.id.textView_word);
        recycler_phonetics = findViewById(R.id.recycler_phonetics);
        recycler_meanings = findViewById(R.id.recycler_meanings);
        progressDialog = new ProgressDialog(this);

        historyRepository = new HistoryRepository(this);

        progressDialog.setTitle("Loading... ");
        progressDialog.show();

        if (historyRepository.getAll().size() == 0) {
            RequestManager manager = new RequestManager(MainActivity.this);
            manager.getWordMeaning(listener, "education");
            HistoryEntity entity = new HistoryEntity();
            entity.setWord("education");
            historyRepository.insert(entity);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching response for " + query);
                progressDialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getWordMeaning(listener, query);
                HistoryEntity entity = new HistoryEntity();
                entity.setWord(query);
                historyRepository.insert(entity);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btnHistory.setOnClickListener(onHistoryClick);
    }

    protected void onResume() {
        super.onResume();
        this.loadInitial();
    }

    private void loadInitial() {
        HistoryEntity last = historyRepository.getLast();
        if (last != null) {
            RequestManager manager = new RequestManager(MainActivity.this);
            manager.getWordMeaning(listener, historyRepository.getLast().getWord());
        }
    }

    private View.OnClickListener onHistoryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(MainActivity.this, HistoryActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
    };

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            progressDialog.dismiss();
            if (apiResponse == null) {
                Toast.makeText(MainActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(apiResponse);
        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(APIResponse apiResponse) {
        textView_word.setText("Word: " + apiResponse.getWord());
        recycler_phonetics.setHasFixedSize(true);
        recycler_phonetics.setLayoutManager(new GridLayoutManager(this, 1));
        phoneticsAdapter = new PhoneticsAdapter(this, apiResponse.getPhonetics());
        recycler_phonetics.setAdapter(phoneticsAdapter);
        recycler_meanings.setHasFixedSize(true);
        recycler_meanings.setLayoutManager(new GridLayoutManager(this, 1));
        meaningsAdapter = new MeaningsAdapter(this, apiResponse.getMeanings());
        recycler_meanings.setAdapter(meaningsAdapter);
    }
}