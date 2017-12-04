package com.devnock.video_streamer.networks.services;

import com.devnock.video_streamer.networks.answers.GetVideoUrlAnswer;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sergey Mochulsky on 18.09.2017.
 */


public interface VideoService {

    @GET("src/list-all-files")
    Observable<ArrayList<String>> getListAllFiles();

    @GET("src/video/start")
    Observable<GetVideoUrlAnswer> getVideoUrl(@Query("videoFile") String videoFileName);
}
