package com.curry.toolt.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;
import com.curry.toolt.R;
import com.curry.toolt.base.TopToolbarActivity;

public class ThemeActivity extends TopToolbarActivity {
    private final String TAG = "themeActivity";

    private ImageView mColorCircle;
    private EditText mRed,mGreen,mBlue;
    private Button mSave;


    @Override
    protected void initContainer(FrameLayout container) {
        View root = getLayoutInflater().inflate(R.layout.activity_theme, container, false);
        container.addView(root);

        mColorCircle = findViewById(R.id.color_circle);
        mRed = findViewById(R.id.red);
        mGreen = findViewById(R.id.green);
        mBlue = findViewById(R.id.blue);
        mSave = findViewById(R.id.save);

        mColorCircle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //得到imgview的长宽
                double imgwidth=mColorCircle.getWidth();
                double imgheight=mColorCircle.getHeight();
                //bitmap的长宽
                Bitmap bitmap = mColorCircle.getDrawingCache(false);
                double bitwidth=bitmap.getWidth();
                double bitheight=bitmap.getHeight();
                //触摸的位置在imgview的坐标位置
                double x=event.getX();
                double y=event.getY();

                //转换为bitmap的坐标
                double actx0,acty0;
                double stretchx,stretchy;   //在imgview中，图片实际显示大小，dp为单位

                //获取actx0，acty0，为实际的图片原点在imageview的dp坐标
                if(bitheight<300&&bitwidth<300){   //图片的长宽都小于imgview大小，单位是dp
                    actx0=(300-bitwidth)/2;
                    acty0=(300-bitheight)/2;
                    stretchx=bitwidth;stretchy=bitheight;
                }else{//其他情况都需要压缩，分为宽度大，还是高度大两种情况
                    if(bitwidth>=bitheight){
                        actx0= (double) 0.0;
                        acty0= (double) ((300.0-(bitheight*300.0)/bitwidth)/2.0);
                        stretchx=300;stretchy=(bitheight*300.0)/bitwidth;  //在
                    }else{
                        acty0=(double)0.0;
                        actx0= (double) ((300.0-(bitwidth*300.0)/bitheight)/2.0);
                        stretchx=(bitwidth*300.0)/bitheight;stretchy=300;
                    }
                }

                //更新为屏幕坐标
                actx0=(actx0/300)*imgwidth;
                acty0=(acty0/300)*imgheight;
                stretchx=(stretchx/300)*imgwidth;
                stretchy=(stretchy/300)*imgheight;

                //判断触摸点是否在图片上
                if(x<actx0||x>actx0+stretchx||y<acty0||y>acty0+stretchy)
                    return true;

                //更新在bitmap坐标系中的坐标
                actx0=(x-actx0)/stretchx*bitwidth;
                acty0=(y-acty0)/stretchy*bitheight;

                //获取图片颜色,在手抬起来时
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int color = bitmap.getPixel((int)actx0, (int)acty0);
                    int r = Color.red(color);
                    int g = Color.green(color);
                    int b = Color.blue(color);
                    int a = Color.alpha(color);
//                    textView.setText("color=" + color + "a=" + a + ",r=" + r + ",g=" + g + ",b=" + b + "x=" + actx0 + "y=" + acty0);
                }
                return true;
            }
        });
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        toolbar.setTitle(getString(R.string.drawer_theme));
    }
}