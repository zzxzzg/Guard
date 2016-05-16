package com.guard.skutest;

import com.alibaba.fastjson.annotation.JSONField;
import com.carme.caruser.entity.ProductDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by yxwang on 16/5/13.
 */
public enum SKUHelper {
    INSTANCE;

    public static final List<ProductDetail.SpecsBean> TEST_SKUS=new ArrayList<>();
    public static final List<ProductDetail.SkusOnsellBean> TEST_BEAN=new ArrayList<>();

    static{
        for(int i=0;i<10;i++){
            ProductDetail.SpecsBean spec=new ProductDetail.SpecsBean();
            spec.setSpecName(i+"");
            List<ProductDetail.SpecsBean.ValuesBean> values=new ArrayList<>();
            for(int j=0;j<10;j++){
                ProductDetail.SpecsBean.ValuesBean value=new ProductDetail.SpecsBean.ValuesBean();
                value.setSpecValueName(j+"");
                value.setSpecValueId(i*10+j);
                values.add(value);
            }
            spec.setValues(values);
            TEST_SKUS.add(spec);
        }



        for(int i=0;i<20;i++){
            ProductDetail.SkusOnsellBean skusOnsellBean=new ProductDetail.SkusOnsellBean();
            skusOnsellBean.setPrice(100f);
            skusOnsellBean.setStorage(20);
            skusOnsellBean.setSkuId(1);
            String str="";
            for(int j=0;j<10;j++){
                Random random=new Random(System.currentTimeMillis());
                int result=random.nextInt(10);
                str=str+(result+j*10)+",";
            }
            if(str.endsWith(",")){
                str=str.substring(0,str.length());
            }
            skusOnsellBean.setSkuSpecCode(str);
            TEST_BEAN.add(skusOnsellBean);
        }
    }

    public static final int[] PROME_TABLE=new int[]{
            2,3,5,7,11,13,17,19,23,29,
            31,37,41,43,47,53,59,61,67,71,
            73,79,83,89,97,101,103,107,109,113,
            127, 131, 137, 139, 149, 151, 157, 163, 167,173,
            179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
            233, 239, 241, 251, 257, 263, 269, 271, 277 ,281,
            283, 293, 307, 311, 313, 317, 331, 337, 347, 349,
            353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
            419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
            467, 479, 487, 491, 499, 503, 509, 521, 523, 541,
            547, 557, 563, 569, 571, 577, 587, 593, 599, 601,
            607, 613, 617, 619, 631, 641, 643, 647, 653, 659,
            661, 673, 677, 683, 691, 701, 709, 719, 727, 733,
            739, 743, 751, 757, 761, 769, 773, 787, 797, 809,
            811, 821, 823, 827, 829, 839, 853, 857, 859, 863,
            877, 881, 883, 887, 907, 911, 919, 929, 937, 941,
            947, 953, 967, 971, 977, 983, 991, 997
    };

    Map<Long,Integer> VALUE_PROME_MAP=new HashMap<>();//<Value, prome>
    List<SKUData> mSKUDatas=new ArrayList<>();
    List<List<Long>> mGroups=new ArrayList<>();

    public void initSKU(List<ProductDetail.SpecsBean> skus) throws Exception {
        int i=0;
        for(ProductDetail.SpecsBean sku:skus){
            List<Long> g=new ArrayList<>();
            for(ProductDetail.SpecsBean.ValuesBean value:sku.getValues()){
                if(i>PROME_TABLE.length){
                    throw new Exception("sku is to long!");
                }
                VALUE_PROME_MAP.put(value.getSpecValueId(),PROME_TABLE[i]);
                g.add(value.getSpecValueId());
                i++;
            }
            mGroups.add(g);
        }
    }

    public void initData(List<ProductDetail.SkusOnsellBean> beans){
        for(ProductDetail.SkusOnsellBean bean:beans){
            String str[]=bean.getSkuSpecCode().split(",");
            SKUData data=new SKUData();
            data.mSkuId=bean.getSkuId();
            data.mSkuSpecCode=bean.getSkuSpecCode();
            data.mPrice=bean.getPrice();
            data.mStorage=bean.getStorage();
            long value=1;
            for(int i=0;i<str.length;i++){
                value=value*VALUE_PROME_MAP.get(Long.parseLong(str[i]));
            }
            data.promeValue=value;
            mSKUDatas.add(data);
        }
    }

    public boolean isValueEnable(String currentSpec,Long value){
        Map<Integer,Long> groupIds=new HashMap<>();
        String str[]=currentSpec.split(",");

        //查找当前选中项的sku 分组并记录
        for(int i=0;i<str.length;i++){
            for(int j=0;j<mGroups.size();j++){
                List<Long> group=mGroups.get(j);
                if(group.contains(Long.parseLong(str[i]))){
                    groupIds.put(j,Long.parseLong(str[i]));
                    break;
                }
            }
        }

        //查找待判断项是不是在已选择的分组中
        int groupId=-1;
        for(int j=0;j<mGroups.size();j++){
            List<Long> group=mGroups.get(j);
            if(group.contains(value)){
                groupId=j;
            }
        }

        //在已选择分组中,那么替换同组选项
        if(groupIds.containsKey(groupId)){
            String replace=groupIds.get(groupId)+"";
            for(int i=0;i<str.length;i++){
                if(str[i].equals(replace)){
                    str[i]=value+"";
                }
            }
        }else{//否则直接添加
            String temp=currentSpec+","+value;
            str=temp.split(",");
        }

        //计算当前组的质数乘积
        long promeValue=1;
        for(int i=0;i<str.length;i++){
            promeValue=promeValue*VALUE_PROME_MAP.get(Long.parseLong(str[i]));
        }

        //寻找是否存在
        SKUData finalSKUData=null;
        for(int i=0;i<mSKUDatas.size();i++){
            if(mSKUDatas.get(i).promeValue%promeValue==0){
                finalSKUData=mSKUDatas.get(i);
                break;
            }
        }

        if(finalSKUData!=null){
            return true;
        }else{
            return false;
        }

    }


    private class SKUData{
        public int mSkuId;
        public String mSkuSpecCode;
        public float mPrice;
        public int mStorage;
        public long promeValue;
    }







//    static{
//        System.loadLibrary("skumodel");
//    }
    //public native void initSKU(String json);
}
