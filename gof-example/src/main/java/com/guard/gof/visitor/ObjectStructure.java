package com.guard.gof.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16-2-16.
 */
public class ObjectStructure {
    List<Product> elements=new ArrayList<>();

    public void attach(Product product){
        elements.add(product);
    }

    public void detach(Product product){
        elements.remove(product);
    }

    public void execute(Visitor visitor){
        for(Product product:elements){
            product.accept(visitor);
        }
    }

}
