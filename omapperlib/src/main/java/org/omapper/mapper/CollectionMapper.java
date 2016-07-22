package org.omapper.mapper;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by yxwang on 16/7/18.
 */
public class CollectionMapper<T, S> extends AbstractMapper {

    /**
     * Instantiates a new abstract mapper.
     *
     * @param targetClass the target class
     * @param sourceClass
     */
    private Class<T> mTargetClass;
    private Class<S> mSourceClass;
    public CollectionMapper(Class<T> targetClass, Class<S> sourceClass) {
        super(targetClass, sourceClass);
        mTargetClass=targetClass;
        mSourceClass=sourceClass;
    }

    public void mapBean(Collection<T> target, Collection<S> source){
        mapCollectionBean(target,mTargetClass,source,mSourceClass);
    }
}
