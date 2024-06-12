package com.example.musicplayerdome.local.bean;

public class LocalSongBean {
    private String songName;
    private String singer;
    public String songPath;

    public LocalSongBean(String songName, String singer, String songPath) {
        this.songName = songName;
        this.singer = singer;
        this.songPath = songPath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }
}
