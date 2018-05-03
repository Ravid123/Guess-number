package com.example.ravid.guessnumber.UI;

import android.content.Context;

/**
 * Created by Ravid on 03/05/2018.
 */

public interface IAlert {
    public void okAlert(Context context, String message);
    public void badAlert(Context context, String message);
}
