package com.devnock.video_streamer.screens.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer.util.Util;
import com.devnock.video_streamer.PlayerActivity;
import com.devnock.video_streamer.R;
import com.devnock.video_streamer.networks.ApiUtils;
import com.devnock.video_streamer.networks.answers.GetVideoUrlAnswer;
import com.devnock.video_streamer.networks.services.VideoService;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Sergey Mochulsky on 18.09.2017.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareProgressDialog();
        setContentView(R.layout.activity_main);
        initView();
        setupLinks();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void setupLinks() {
        showProgressDialog();
        ApiUtils.createService(VideoService.class)
                .getListAllFiles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetListVideoSuccess, this::onError);
    }

    private void prepareProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please, wait");
    }


    public void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    public void hideProgressBar() {
        progressDialog.hide();
    }


    private void onError(Throwable throwable) {
        hideProgressBar();
        Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();
    }

    private void onGetListVideoSuccess(ArrayList<String> names) {
        logNames(names);
        hideProgressBar();
        setupAdapter(names);
    }

    private void logNames(ArrayList<String> names) {
        Log.d(TAG, "onGetListVideoSuccess");
        for(String name : names){
            Log.d(TAG, "Name: " + name);
        }
    }


    private void setupAdapter(ArrayList<String> names) {
        ItemAdapter adapter = new ItemAdapter(names, this::loadLinksAndOpen);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadLinksAndOpen(String fileName) {
        showProgressDialog();
        ApiUtils.createService(VideoService.class)
                .getVideoUrl(fileName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUrlLoadedSuccess, this::onError);
    }

    private void onUrlLoadedSuccess(GetVideoUrlAnswer getVideoUrlAnswer) {
        Log.d(TAG, "onUrlLoadedSuccess: " + getVideoUrlAnswer.getUrl());
        hideProgressBar();
        startVideoActivity(getVideoUrlAnswer.getUrl());
    }

    private void startVideoActivity(String url) {
        Intent mpdIntent = new Intent(this, PlayerActivity.class)
                .setData(Uri.parse(ApiUtils.VIDEO_BASE_URL + url))
                .putExtra(PlayerActivity.CONTENT_ID_EXTRA, url)
                .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS)
                .putExtra(PlayerActivity.PROVIDER_EXTRA, "");
        startActivity(mpdIntent);
    }

//    private void onSampleSelected(Samples.Sample sample) {
//        Intent mpdIntent = new Intent(this, PlayerActivity.class)
//                .setData(Uri.parse(sample.uri))
//                .putExtra(PlayerActivity.CONTENT_ID_EXTRA, sample.contentId)
//                .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, sample.type)
//                .putExtra(PlayerActivity.PROVIDER_EXTRA, sample.provider);
//        startActivity(mpdIntent);
//    }
//
}
