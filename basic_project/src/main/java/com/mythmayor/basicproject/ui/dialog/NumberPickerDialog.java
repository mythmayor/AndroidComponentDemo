package com.mythmayor.basicproject.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.utils.ProjectUtil;

/**
 * Created by mythmayor on 2020/7/16.
 * NumberPicker选择器弹窗
 */
public class NumberPickerDialog {

    private volatile static BottomSheetDialog sDialog;
    public static final int CLICK_TYPE_CANCEL = 1;
    public static final int CLICK_TYPE_SURE = 2;

    public static void show(Context context, String[] array, OnSelectListener listener) {
        show(context, null, array, 0, true, listener);
    }

    public static void show(Context context, String[] array, int value, OnSelectListener listener) {
        show(context, null, array, value, true, listener);
    }

    public static void show(Context context, String[] array, boolean cancelable, OnSelectListener listener) {
        show(context, null, array, 0, cancelable, listener);
    }

    public static void show(Context context, String title, String[] array, OnSelectListener listener) {
        show(context, title, array, 0, true, listener);
    }

    public static void show(Context context, String title, String[] array, int value, boolean cancelable, OnSelectListener listener) {
        if (sDialog != null && sDialog.isShowing()) {
            //sDialog.dismiss();
            return;
        }
        if (!(context instanceof Activity)) {
            return;
        }
        initBottomSheetDialog(context, title, array, value, cancelable, listener);
        if (sDialog != null && !sDialog.isShowing() && !((Activity) context).isFinishing()) {
            sDialog.show();
        }
    }

    private static void initBottomSheetDialog(Context context, String title, String[] array, int value, boolean cancelable, OnSelectListener listener) {
        sDialog = new BottomSheetDialog(context, R.style.list_dialog_theme);
        sDialog.setCancelable(cancelable);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_number_picker, null);
        TextView tvcancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvtitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvsure = (TextView) view.findViewById(R.id.tv_sure);
        NumberPicker mNumberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        initNumberPicker(mNumberPicker, array, value);
        if (!TextUtils.isEmpty(title)) {
            tvtitle.setText(title);
        }
        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onSelected(CLICK_TYPE_CANCEL, 0, null);
                }
            }
        });
        tvsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                int value = mNumberPicker.getValue();
                if (listener != null) {
                    listener.onSelected(CLICK_TYPE_SURE, value, array[value]);
                }
            }
        });
        sDialog.setContentView(view);
        sDialog.show();
    }

    private static void initNumberPicker(NumberPicker mNumberPicker, String[] array, int value) {
        mNumberPicker.setDisplayedValues(array);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(array.length - 1);
        mNumberPicker.setValue(value);
        mNumberPicker.setWrapSelectorWheel(false);
        mNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        ProjectUtil.setNumberPickerDividerColor(mNumberPicker);
    }

    public static void dismiss() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }
        sDialog = null;
    }

    public interface OnSelectListener {
        void onSelected(int type, int value, String result);
    }
}
