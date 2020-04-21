package com.example.number_ticket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SearchView extends AppCompatActivity {
    private SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchView = findViewById(R.id.searchView); // SearchView
    }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            // 입력받은 문자열 처리
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            // 입력란의 문자열이 바뀔 때 처리
            return false;
        }
    };
}

