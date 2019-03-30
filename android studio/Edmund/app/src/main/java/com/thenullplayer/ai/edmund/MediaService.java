package com.thenullplayer.ai.edmund;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.os.PowerManager;

public class MediaService extends Service
{
    private MediaPlayer player;

    public MediaService()
    {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        player = new MediaPlayer();
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //setup listeners
        player.setOnPreparedListener(new MusicPreparedListener());
        player.setOnCompletionListener(new MusicCompletionListener());
        player.setOnErrorListener(new MusicErrorListener());
        //player.setDataSource("");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    //define music listeners

    private class MusicPreparedListener implements OnPreparedListener{
        @Override
        public void onPrepared(MediaPlayer mp)
        {
            //start playback
            mp.start();
        }
    }

    private class MusicCompletionListener implements OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            //check if playback has reached the end of a track
            if(player.getCurrentPosition()>0){
                mp.reset();
                //todo play next
            }
        }
    }

    private class MusicErrorListener implements OnErrorListener{
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra)
        {
            mp.reset();
            return false;
        }
    }
}
