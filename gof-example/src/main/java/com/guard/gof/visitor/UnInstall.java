package com.guard.gof.visitor;

import android.util.Log;

/**
 * Created by yxwang on 16-2-16.
 */
public class UnInstall implements Visitor {
    @Override
    public void visit(CPU cpu) {
        Log.d("sss", "print the cpu uninstall info");
    }

    @Override
    public void visit(ARM arm) {
        Log.d("sss","print the arm uninstall info");
    }
}
