package com.devnock.video_streamer.networks.answers;

/**
 * Created by Sergey Mochulsky on 18.09.2017.
 */


public class GetVideoUrlAnswer {

    private String url;

    public GetVideoUrlAnswer() {
    }

    public GetVideoUrlAnswer(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
