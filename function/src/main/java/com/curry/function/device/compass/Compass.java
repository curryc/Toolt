package com.curry.function.device.compass;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseFullScreenActivity;
import com.curry.util.adpter.SingleTypeAdapter;
import com.curry.util.view.FlowLayout;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-14 16:37
 * @description: 指南针
 **/
public class Compass extends BaseFullScreenActivity
        implements SensorEventListener, View.OnClickListener {
    private final String TAG = "Compass";

    private CompassView mCompass;
    private SensorManager mSensorManager;
    private FlowLayout mFlow;
    private SingleTypeAdapter mAdapter;

    private float[] accelerometerValues = new float[3];
    private float[] magneticValues = new float[3];

    @Override
    protected int LayoutId() {
        return R.layout.compass;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void initView() {
        mFlow = findViewById(R.id.flow);

        // 指南针view
        mCompass = new CompassView(this, 0, App.getThemeColor(), 0);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        params.leftMargin = params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.horizontal_margin_mid);
        ((FrameLayout) findViewById(R.id.container)).addView(mCompass, params);
        mCompass.setOnClickListener(this);

        // 传感器数据获取
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        Sensor magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            //赋值调用clone方法
            magneticValues = event.values.clone();
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = event.values.clone();
        }

        float[] R = new float[9];
        float[] values = new float[3];
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
        SensorManager.getOrientation(R, values);
        mCompass.setDirection((int) Math.toDegrees(values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        TextView t = new TextView(this);
        GradientDrawable d = new GradientDrawable();

        GradientDrawable background = new GradientDrawable();
        background.setTint(App.getThemeColor());
        background.setCornerRadius(getResources().getDimensionPixelOffset(R.dimen.button_radius_sml));
        t.setBackground(background);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.rightMargin = 10;
        params.topMargin = 5;
        params.bottomMargin = 5;
        t.setLayoutParams(params);
        t.getRootView().setPadding(10, 0, 10, 0);
        t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t.setText(String.valueOf(mCompass.getDirection()));
        mFlow.addView(t);
    }
}
