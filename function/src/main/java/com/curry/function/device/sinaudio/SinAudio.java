package com.curry.function.device.sinaudio;

import android.media.*;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.RequiresPermission;
import com.curry.function.R;
import com.curry.function.base.BaseActivity;
import com.curry.util.log.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-15 23:01
 * @description: 发生正弦波
 **/
public class SinAudio extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private final String TAG = "SinAudio";
    private SeekBar mSeekBar;
    private TextView mHint;

    private ImageView mPre, mPlay, mNext;

    private AudioTrack mAudio;
    private ExecutorService es;

    private final int HEIGHT = 128;
    private final int DURATION = 5;
    private final int SAMPLE_RATE = 44100;

    private Status status;

    enum Status {
        None,
        Ready,
        Pause,
        Playing
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sin_audio;
    }

    @Override
    protected String getWindowTitle() {
        return "SinAudio";
    }

    @Override
    protected void initView() {
        mSeekBar = findViewById(R.id.hz);
        mHint = findViewById(R.id.hint);
        mPre = findViewById(R.id.pre);
        mPlay = findViewById(R.id.play);
        mNext = findViewById(R.id.next);


        mPlay.setOnClickListener(this);
        mPre.setOnClickListener(this);
        mNext.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(this);

        mAudio = new AudioTrack(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(),
                new AudioFormat.Builder()
                        .setSampleRate(SAMPLE_RATE)
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build(),
                DURATION * SAMPLE_RATE,
                AudioTrack.MODE_STATIC,
                AudioManager.AUDIO_SESSION_ID_GENERATE
        );
        status = Status.None;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pre) {
            int length = DURATION * SAMPLE_RATE;
            mSeekBar.setProgress(mSeekBar.getProgress() - 1);
            mAudio.write(sin(new byte[length], mSeekBar.getProgress(), length), 0, length);
            if (mSeekBar.getProgress() != 0 && status != Status.Ready) status = Status.Ready;
        } else if (v.getId() == R.id.play) {
            if (status == Status.Ready || status == Status.Pause) {
                status = Status.Playing;
                startTimer();
                mAudio.play();
                mPlay.setImageResource(R.drawable.ic_pause);
            } else if (status == Status.Playing) {
                status = Status.Pause;
                endTimer();
                mAudio.stop();
                mPlay.setImageResource(R.drawable.ic_play);
            }
        } else if (v.getId() == R.id.next) {
            int length = DURATION * SAMPLE_RATE;
            mSeekBar.setProgress(mSeekBar.getProgress() + 1);
            mAudio.write(sin(new byte[length], mSeekBar.getProgress(), length), 0, length);
            if (status != Status.Ready) status = Status.Ready;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mHint.setText("频率(hz):\t" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (status == Status.Playing) {
            status = Status.Pause;
            mAudio.stop();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getProgress() == 0) return;
        int length = DURATION * SAMPLE_RATE;
        mAudio.write(sin(new byte[length], seekBar.getProgress(), length), 0, length);
        if (status != Status.Ready) status = Status.Ready;
        Logger.v(TAG, "write successfully");
    }

    private byte[] sin(byte[] wave, int frequency_hz, int length) {
        for (int i = 0; i < length; i++) {
            wave[i] = (byte) (HEIGHT * (1 - Math.sin(2 * Math.PI * i / (SAMPLE_RATE / frequency_hz))));
        }
        return wave;
    }

    private void startTimer() {
        es = Executors.newSingleThreadExecutor();
        es.execute(new Timer());
    }

    private void endTimer() {
        if (es != null) {
            es.shutdownNow();
        }
    }

    private class Timer implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(5 * 1000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPlay.setImageResource(R.drawable.ic_play);
                        status = Status.Pause;
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
