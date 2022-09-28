package com.curry.function.image.bilibili;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;
import com.curry.util.base.BaseEvent;
import com.curry.util.event.GetBitmapByUrlEvent;
import com.curry.util.event.MatchRegexEvent;
import com.curry.util.image.ImageUtils;
import com.curry.util.time.Formatter;
import com.curry.util.web.Web;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.MalformedURLException;
import java.util.Date;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-14 18:07
 * @description: 获取Bilibili视频封面
 **/
public class Bilibili extends BaseBackActivity {
    private final String PREFIX = "https://www.bilibili.com/video/";
    private final String REGEX = "http:\\/\\/i[0-9]\\.hdslb.com\\/bfs\\/archive\\/[0-9a-z]*\\.jpg";
    private final String TAG = "Bilibili";

    private final String GET_URL = "getBilibiliUrl";
    private final String GET_BITMAP = "getBilibiliBitmap";

    private ImageView mImage;
    private Button mSearch;
    private Bitmap bitmap;
    private EditText mBV;
    private String src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultEvent(BaseEvent<?> event) throws MalformedURLException {
        if (event instanceof MatchRegexEvent && event.getCode() == BaseEvent.EVERYTHING_RIGHT) {
            src = ((String[]) event.getData())[0];
            System.out.println(src);
            Web.getBitmapByUrl(src, GET_BITMAP);
            return;
        } else if (event instanceof GetBitmapByUrlEvent && event.getCode() == BaseEvent.EVERYTHING_RIGHT) {
            bitmap = (Bitmap) event.getData();
            mImage.setImageBitmap(bitmap);
            mSearch.setEnabled(true);
            setBackgroundColor(App.getThemeColor(), R.id.search);
            mSearch.setText(R.string.search);
            return;
        }
        // 网络错误   BV1sW4y1h77k
        com.curry.util.log.Logger.d(TAG, "wrong network");
    }

    @Override
    protected int LayoutId() {
        return R.layout.bilibili;
    }

    @Override
    protected void initView() {
        mImage = findViewById(R.id.image);
        mBV = findViewById(R.id.bv);
        mSearch = findViewById(R.id.search);

        setBackgroundColor(App.getThemeColor(), R.id.search, R.id.save);
        setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.search) {
                    if (mBV.getText().toString().equals("")) return;
                    Web.getStringsByReg(PREFIX + mBV.getText().toString(), REGEX, GET_URL);
                    bitmap = null;
                    mSearch.setEnabled(false);
                    setBackgroundColor(Color.GRAY, R.id.search);
                    mSearch.setText(R.string.searching);
                } else if (v.getId() == R.id.save) {
                    if(bitmap != null) {
                        ImageUtils.saveBitmap(Bilibili.this,
                                bitmap,
                                Formatter.getFileFormatString(new Date()),
                                mBV.getText().toString());
                        toastShort(getString(R.string.save_successfully));
                    }else{
                        toastShort(getString(R.string.hold_on));
                    }
                }
            }
        }, R.id.search, R.id.save);
    }

    @Override
    protected String getWindowTitle() {
        return getString(R.string.bilibili);
    }
}
