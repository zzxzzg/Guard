package com.guard.gof.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16-2-18.
 */
public class Limb extends Component {

    private List<Component> mComponent =new ArrayList<>();

    @Override
    public void addComponent(Component component) {
        if(!mComponent.contains(component)){
            mComponent.add(component);
        }
    }

    @Override
    public Component removeComponent(Component component) {
        if(mComponent.remove(component)) {
            return component;
        }
        return null;
    }
}
