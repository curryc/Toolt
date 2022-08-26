package com.curry.function.image.stitch;

import android.content.Intent;
import android.view.View;
import com.curry.function.R;
import com.curry.function.base.BaseBottomDialog;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-11 17:40
 * @description: 选择是哪种拼接的dialog
 **/
public class ChoseModeDialog extends BaseBottomDialog {
    public static String DATA = "data";
    private String[] uris;

    @Override
    protected int LayoutId() {
        return R.layout.image_stitch_chose_dialog;
    }

    @Override
    protected void initData() {
        super.initData();
        uris = getIntent().getStringArrayExtra(DATA);
    }

    @Override
    protected void initView() {
        findViewById(R.id.horizontal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoseModeDialog.this, NormalStitch.class);
                i.putExtra(NormalStitch.MODE, true);
                i.putExtra(DATA, uris);
                startActivity(i);
            }
        });
        findViewById(R.id.vertical).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoseModeDialog.this, NormalStitch.class);
                i.putExtra(NormalStitch.MODE, false);
                i.putExtra(DATA, uris);
                startActivity(i);
            }
        });
        findViewById(R.id.lines).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoseModeDialog.this, LinesStitch.class);
                i.putExtra(DATA, uris);
                startActivity(i);
            }
        });
    }
}
