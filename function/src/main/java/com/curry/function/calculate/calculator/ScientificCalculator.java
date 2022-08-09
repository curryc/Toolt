package com.curry.function.calculate.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;
import com.curry.util.log.Logger;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-03 10:23
 * @description: 科学计算器的主程序, 这里定义科学计算器的各个按钮的功能和处理
 * 定义了两个字符串,一个用于显示,一个抽象成更容易计算的字符串
 * 定义了两种乘法,考虑了不同情况下,即省略与不省略的情况下不同的优先级,显然省略的时候优先级会更高,在计算根号的时候体现的更加明显
 **/
public class ScientificCalculator extends BaseBackActivity
        implements View.OnClickListener {
    private final String TAG = "calculator";
    private Button mEqu;
    private Button mSecond;
    private Button mSin;
    private Button mCos;
    private Button mTan;
    private Button mLg;
    private Button mLn;
    private Button mLeft;
    private Button mRoot;
    private Button mAc;
    private Button mDel;
    private Button mFac;
    private Button mRec;
    private Button mPai;

    private TextView mText;
    private TextView mAns;

    private String mFormula;//展示在屏幕的formula
    private String mAbFormula;//抽象给计算器的formula
    private Calculator cal;

    @Override
    protected int LayoutId() {
        return R.layout.calculator_scientific;
    }

    @Override
    protected String getWindowTitle() {
        return getString(R.string.calculator);
    }

    //初始化所有监听器
    @Override
    protected void initView() {
        setBackgroundColor(App.getThemeColor(),
                R.id.sci_0,
                R.id.sci_1,
                R.id.sci_2,
                R.id.sci_3,
                R.id.sci_4,
                R.id.sci_5,
                R.id.sci_6,
                R.id.sci_7,
                R.id.sci_8,
                R.id.sci_9,
                R.id.sci_add,
                R.id.sci_sub,
                R.id.sci_mut,
                R.id.sci_div,
                R.id.sci_mod,
                R.id.sci_point,
                R.id.sci_equ,
                R.id.sci_ac,
                R.id.sci_del,
                R.id.sci_2nd,
                R.id.sci_sin,
                R.id.sci_cos,
                R.id.sci_tan,
                R.id.sci_pow,
                R.id.sci_lg,
                R.id.sci_ln,
                R.id.sci_left,
                R.id.sci_right,
                R.id.sci_root,
                R.id.sci_fac,
                R.id.sci_rec,
                R.id.sci_pai,
                R.id.sci_e
        );

        setClickListener(this,
                R.id.sci_0,
                R.id.sci_1,
                R.id.sci_2,
                R.id.sci_3,
                R.id.sci_4,
                R.id.sci_5,
                R.id.sci_6,
                R.id.sci_7,
                R.id.sci_8,
                R.id.sci_9,
                R.id.sci_add,
                R.id.sci_sub,
                R.id.sci_mut,
                R.id.sci_div,
                R.id.sci_mod,
                R.id.sci_point,
                R.id.sci_pow,
                R.id.sci_right,
                R.id.sci_e
        );

        mFormula = "";
        mAbFormula = "";
        cal = new Calculator();

        mEqu = findViewById(R.id.sci_equ);
        mAc = findViewById(R.id.sci_ac);
        mDel = findViewById(R.id.sci_del);
        mSecond = findViewById(R.id.sci_2nd);
        mSin = findViewById(R.id.sci_sin);
        mCos = findViewById(R.id.sci_cos);
        mTan = findViewById(R.id.sci_tan);
        mLg = findViewById(R.id.sci_lg);
        mLn = findViewById(R.id.sci_ln);
        mLeft = findViewById(R.id.sci_left);
        mRoot = findViewById(R.id.sci_root);
        mFac = findViewById(R.id.sci_fac);
        mRec = findViewById(R.id.sci_rec);
        mPai = findViewById(R.id.sci_pai);

        mText = findViewById(R.id.sci_text);
        mAns = findViewById(R.id.sci_ans);

        //当第二功能键被点击，需要改变三角函数的键文字
        mSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSin.setText(mSin.getText().equals(getString(R.string.sin)) ?
                        R.string.arc_sin : R.string.sin);
                mCos.setText(mCos.getText().equals(getString(R.string.cos)) ?
                        R.string.arc_cos : R.string.cos);
                mTan.setText(mTan.getText().equals(getString(R.string.tan)) ?
                        R.string.arc_tan : R.string.tan);
            }
        });
        mFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFormula.isEmpty()) {
                    mFormula = mFormula + "0!";
                    mAbFormula = mAbFormula + "0!";
                } else {
                    mFormula = mFormula + "!";
                    mAbFormula = mAbFormula + "!";
                }
                updateText();
            }
        });
        mRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = mFormula + "^(-1)";
                mAbFormula = mAbFormula + "^-1";
                updateText();
            }
        });
        mPai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = mFormula + "Π";
                mAbFormula = mAbFormula + "p";
                updateText();
            }
        });
        //用小写字母表示三角函数,大写字母表示反三角函数,判断是否存在2sin(2)这种形式,需要加上乘号
        mSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = mSin.getText().equals(getString(R.string.sin)) ?
                        mFormula + "sin(" : mFormula + "arcsin(";
                if (mAbFormula.length() >= 1) {
                    mAbFormula = mSin.getText().equals(getString(R.string.sin)) ?
                            Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) ? mAbFormula + "xs(" : mAbFormula + "s("
                            : Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) ? mAbFormula + "xS(" : mAbFormula + "S(";
                } else {
                    mAbFormula = mSin.getText().equals(getString(R.string.sin)) ?
                            mAbFormula + "s(" : mAbFormula + "S(";
                }
                updateText();
            }
        });
        mCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = mCos.getText().equals(getString(R.string.cos)) ?
                        mFormula + "cos(" : mFormula + "arccos(";
                if (mAbFormula.length() >= 1) {
                    mAbFormula = mCos.getText().equals(getString(R.string.cos)) ?
                            Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) ? mAbFormula + "xc(" : mAbFormula + "c("
                            : Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) ? mAbFormula + "xC(" : mAbFormula + "C(";
                } else {
                    mAbFormula = mCos.getText().equals(getString(R.string.cos)) ?
                            mAbFormula + "c(" : mAbFormula + "C(";
                }
                updateText();
            }
        });
        mTan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = mTan.getText().equals(getString(R.string.tan)) ?
                        mFormula + "tan(" : mFormula + "arctan(";
                if (mAbFormula.length() >= 1) {
                    mAbFormula = mTan.getText().equals(getString(R.string.tan)) ?
                            Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) ? mAbFormula + "xt(" : mAbFormula + "t("
                            : Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) ? mAbFormula + "xT(" : mAbFormula + "T(";
                } else {
                    mAbFormula = mTan.getText().equals(getString(R.string.tan)) ?
                            mAbFormula + "t(" : mAbFormula + "T(";
                }
                updateText();
            }
        });
        //用小l表示ln,大l表示lg
        mLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = mFormula + "lg(";
                if (mAbFormula.length() >= 1) {
                    mAbFormula = Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) ? mAbFormula + "xL(" : mAbFormula + "L(";
                } else {
                    mAbFormula += "L(";
                }
                updateText();
            }
        });
        mLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = mFormula + "ln(";
                if (mAbFormula.length() >= 1) {
                    mAbFormula = Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) ? mAbFormula + "xl(" : mAbFormula + "l(";
                } else {
                    mAbFormula += "l(";
                }
                updateText();
            }
        });
        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = mFormula + "(";
                if (mAbFormula.length() >= 1) {
                    mAbFormula = Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) || mAbFormula.charAt(mAbFormula.length() - 1) == ')' ?
                            mAbFormula + "X(" : mAbFormula + "(";
                } else {
                    mAbFormula += "(";
                }
                updateText();
            }
        });
        mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = mFormula + "√";
                if (mAbFormula.length() >= 1) {
                    mAbFormula = Character.isDigit(mAbFormula.charAt(mAbFormula.length() - 1)) ? mAbFormula + "X√" : mAbFormula + "√";
                } else {
                    mAbFormula += "√";
                }
                updateText();
            }
        });
        mDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFormula.length() == 0) {
                    return;
                }
                String isTri = "";
                String isArcTri = "";
                String isLog = "";
                String isBra = "";
                isTri = mFormula.length() >= 4 ? mFormula.substring(mFormula.length() - 4) : "";//三角函数
                if (isTri.equals("sin(") || isTri.equals("cos(") || isTri.equals("tan(")) {
                    mFormula = mFormula.substring(0, mFormula.length() - 4);
                    if (mFormula.length() >= 5 && mFormula.charAt(mFormula.length() - 5) == 'x') {
                        //不应当删除这个可能存在的乘号,因为我们已经再mFormula中显式的表达了这个乘号,若在mAbFormula中删除,会出现错误,下面同理
                        delAbFormula(3, false);
                    } else {
                        //应当删除这个可能的乘号
                        delAbFormula(3, true);
                    }
                    updateText();
                    return;
                }
                isArcTri = mFormula.length() >= 7 ? mFormula.substring(mFormula.length() - 7) : "";//反三角函数
                if (isArcTri.equals("arcsin(") || isArcTri.equals("arccos(") || isArcTri.equals("arctan(")) {
                    mFormula = mFormula.substring(0, mFormula.length() - 7);
                    if (mFormula.length() >= 8 && mFormula.charAt(mFormula.length() - 8) == 'x') {
                        delAbFormula(3, false);
                    } else {
                        delAbFormula(3, true);
                    }
                    updateText();
                    return;
                }
                isLog = mFormula.length() >= 3 ? mFormula.substring(mFormula.length() - 3) : "";//对数函数
                if (isLog.equals("lg(") || isLog.equals("ln(")) {
                    mFormula = mFormula.substring(0, mFormula.length() - 3);
                    if (mFormula.length() >= 4 && mFormula.charAt(mFormula.length() - 4) == 'x') {
                        delAbFormula(3, false);
                    } else {
                        delAbFormula(3, true);
                    }
                    updateText();
                    return;
                }
                isBra = mFormula.length() >= 1 ? mFormula.substring(mFormula.length() - 1) : "";//左括号
                if (isBra.equals("(")) {
                    mFormula = mFormula.substring(0, mFormula.length() - 1);
                    if (mFormula.length() >= 2 && mFormula.charAt(mFormula.length() - 2) == 'x') {
                        delAbFormula(3, false);
                    } else {
                        delAbFormula(3, true);
                    }
                    updateText();
                    return;
                }
                if (mFormula.equals("0!")) {
                    mFormula = "";
                    mAbFormula = "";
                    updateText();
                    return;
                }
                mFormula = mFormula.substring(0, mFormula.length() - 1);
                mAbFormula = mAbFormula.substring(0, mAbFormula.length() - 1);
                updateText();
            }
        });
        mAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormula = "";
                mAbFormula = "";
                mAns.setText(getString(R.string.calculator_answer));
                mText.setText(getString(R.string.calculator_formula));
            }
        });
        mEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "calculate this formula", Toast.LENGTH_SHORT).show();
                if (mFormula.isEmpty()) {
                    return;
                }
                Logger.d(TAG, mAbFormula);
                double answer = cal.cal(mAbFormula);
                if (answer == Double.MIN_VALUE) {
                    mAns.setText(getString(R.string.calculator_bad_hint));
                } else {
                    //测试:利用后缀表达式进行计算
                    //mAns.setText(String.valueOf(cal.calByPostfix(postfix)));
                    //利用中缀表达式直接进行计算
                    mAns.setText((int) answer == answer ? String.valueOf((int) answer) : String.valueOf(answer));
                }
            }
        });
    }

    /**
     * 一般按钮的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        mFormula = mFormula + ((Button) v).getText();
        mAbFormula = mAbFormula + ((Button) v).getText();
        updateText();
    }

    private void updateText() {
        mText.setText(mFormula);
    }

    /**
     * 长度是带着那个可能的乘号的长度,flag为true代表应该删除这个乘号,false为不应该
     * @param length
     * @param flagForDel
     */
    private void delAbFormula(int length, boolean flagForDel) {
        //如果前面隐藏了一个乘号,并且这个乘号应当删除(取决于mFormula中是否有一个乘号,有就不应该删除,否则删除)
        if (flagForDel) {
            if (mAbFormula.length() >= length && mAbFormula.charAt(mAbFormula.length() - length) == 'X') {
                //应当删除这个可能存在的乘号并且这个在mFormula中被隐藏(拥有更高的优先级)乘号确实存在,删除之
                mAbFormula = mAbFormula.substring(0, mAbFormula.length() - length);
            } else {
                mAbFormula = mAbFormula.substring(0, mAbFormula.length() - length + 1);
            }
        } else {
            mAbFormula = mAbFormula.substring(0, mAbFormula.length() - length + 1);
        }
    }
}