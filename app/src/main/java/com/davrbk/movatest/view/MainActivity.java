package com.davrbk.movatest.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.davrbk.movatest.R;
import com.davrbk.movatest.adapter.ImagesListAdapter;
import com.davrbk.movatest.presenter.MovaContract;
import com.davrbk.movatest.presenter.MovaPresenter;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, MovaContract.View {

    private EditText mSearchView;
    private MovaContract.Presenter presenter;
    private ImagesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MovaPresenter(this);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.init();
    }

    private void initViews() {
        RecyclerView imagesView = findViewById(R.id.recycler);
        imagesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        imagesView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ImagesListAdapter(this, presenter);
        imagesView.setAdapter(adapter);
        mSearchView = findViewById(R.id.search_edit);
        mSearchView.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_ENTER:
                    presenter.searchImage(mSearchView.getText().toString());
                    break;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void invalidateList() {
        adapter.notifyDataSetChanged();
    }
}
