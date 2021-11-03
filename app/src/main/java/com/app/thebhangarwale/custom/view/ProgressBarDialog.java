package com.app.thebhangarwale.custom.view;

import android.content.Context;

import androidx.annotation.NonNull;

import com.app.thebhangarwale.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProgressBarDialog extends MaterialAlertDialogBuilder {

    public ProgressBarDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ProgressBarDialog(@NonNull Context context, int overrideThemeResId) {
        super(context, overrideThemeResId);
        init();
    }

    private void init(){
        setView(R.layout.dialog_progress_bar);
        setCancelable(false);
    }

}
