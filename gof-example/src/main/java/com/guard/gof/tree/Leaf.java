package com.guard.gof.tree;

import android.util.Log;

/**
 * Created by yxwang on 16-2-18.
 */
public class Leaf extends Component {
    @Override
    public void addComponent(Component component) {
        Log.d("sss", "叶子节点无法添加组件");
    }

    @Override
    public Component removeComponent(Component component) {
        Log.d("sss", "叶子节点无法删除组件");
        return null;
    }
}
