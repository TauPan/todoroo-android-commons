/*
 * ASTRID: Android's Simple Task Recording Dashboard
 *
 * Copyright (c) 2009 Tim Su
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.todoroo.androidcommonscommons.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

import com.todoroo.androidcommonscommons.service.Autowired;
import com.todoroo.androidcommonscommons.service.DependencyInjectionService;

public class NumberPickerDialog extends AlertDialog implements OnClickListener {

    @Autowired
    private Integer numberPickerDialogLayout;

    @Autowired
    private Integer numberPickerId;

    public interface OnNumberPickedListener {
        void onNumberPicked(NumberPickerWidget view, int number);
    }

    private final NumberPickerWidget           mPicker;
    private final OnNumberPickedListener mCallback;

    public NumberPickerDialog(Context context, OnNumberPickedListener callBack,
            String title, int initialValue, int incrementBy, int start, int end) {
        super(context);
        DependencyInjectionService.getInstance().inject(this);

        mCallback = callBack;

        setButton(context.getText(android.R.string.ok), this);
        setButton2(context.getText(android.R.string.cancel), (OnClickListener) null);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(numberPickerDialogLayout, null);
        setView(view);

        setTitle(title);
        mPicker = (NumberPickerWidget) view.findViewById(numberPickerId);
        mPicker.setIncrementBy(incrementBy);
        mPicker.setRange(start, end);
        mPicker.setCurrent(initialValue);
    }

    public void setInitialValue(int initialValue) {
        mPicker.setCurrent(initialValue);
    }

    public void onClick(DialogInterface dialog, int which) {
        if (mCallback != null) {
            mPicker.clearFocus();
            mCallback.onNumberPicked(mPicker, mPicker.getCurrent());
        }
    }
}
