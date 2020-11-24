/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streetfighter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music
{
    private static MediaPlayer mediaPlayer;

    public MediaPlayer playMusic()
    {
        Media song1 = new Media(new File("src/streetfighter/Music/song1.mp3").toURI().toString());
        Media song2 = new Media(new File("src/streetfighter/Music/song2.mp3").toURI().toString());
        Media song3 = new Media(new File("src/streetfighter/Music/song3.mp3").toURI().toString());
        Media song4 = new Media(new File("src/streetfighter/Music/song4.mp3").toURI().toString());
        Media song5 = new Media(new File("src/streetfighter/Music/song5.mp3").toURI().toString());

        List<Media> playlist = new ArrayList<>();
        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);
        playlist.add(song4);
        playlist.add(song5);

        mediaPlayer = new MediaPlayer(playlist.get((int)(Math.random()*4)+1));

        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(10);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        return mediaPlayer;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void playWinSong() {
        Media winSong = new Media(new File("src/streetfighter/Music/winSong.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(winSong);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(10);
    }
}
