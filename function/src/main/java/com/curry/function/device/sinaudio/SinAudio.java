package com.curry.function.device.sinaudio;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.curry.function.R;
import com.curry.function.base.BaseActivity;


/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-15 23:01
 * @description: 发生正弦波
 **/
public class SinAudio extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private SeekBar mSeekBar;
    private TextView mHint;

    private ImageView mPre, mPlay, mNext;

    private AudioTrack mAudio;

    private final int BUFFER_SIZE = 250;
    private final int HEIGHT = 128;
    private final int LENGTH = 200;
    private final double TWOPI = Math.PI * 2;

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
                        .setSampleRate(22050)
                        .setEncoding(AudioFormat.ENCODING_PCM_8BIT)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build(),
                BUFFER_SIZE,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE
        );
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pre) {

        } else if (v.getId() == R.id.play) {
            mAudio.play();
        } else if (v.getId() == R.id.next) {

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mHint.setText("频率(hz): " + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mAudio.write(sin(new byte[LENGTH], 10000 / seekBar.getProgress(), LENGTH), 0, LENGTH);
    }

    private byte[] sin(byte[] wave, int waveLen, int length) {
        for (int i = 0; i < length; i++) {
            wave[i] = (byte) (HEIGHT * (1 - Math.sin(TWOPI * ((i % waveLen) * 1.00 / waveLen))));
        }
        return wave;
    }
}
