package com.guard.skutest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NestRadioGroup mRadioGroup1;
    private NestRadioGroup mRadioGroup2;
    private NestRadioGroup mRadioGroup3;
    private NestRadioGroup mRadioGroup4;
    private NestRadioGroup mRadioGroup5;
    private NestRadioGroup mRadioGroup6;
    private NestRadioGroup mRadioGroup7;
    private NestRadioGroup mRadioGroup8;
    private NestRadioGroup mRadioGroup9;
    private NestRadioGroup mRadioGroup10;

    private NestRadioGroup[] radio=new NestRadioGroup[10];
    private List<ProductDetail.SpecsBean.ValuesBean> mCurrentSelected=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SKUHelper.INSTANCE.initSKUC("haha");

        setContentView(R.layout.activity_main);
        try {
            SKUHelper.INSTANCE.initSKU(SKUHelper.TEST_SKUS);
            SKUHelper.INSTANCE.initData(SKUHelper.TEST_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mRadioGroup1=(NestRadioGroup) findViewById(R.id.radio_group1);
        mRadioGroup2=(NestRadioGroup) findViewById(R.id.radio_group2);
        mRadioGroup3=(NestRadioGroup) findViewById(R.id.radio_group3);
        mRadioGroup4=(NestRadioGroup) findViewById(R.id.radio_group4);
        mRadioGroup5=(NestRadioGroup) findViewById(R.id.radio_group5);
        mRadioGroup6=(NestRadioGroup) findViewById(R.id.radio_group6);
        mRadioGroup7=(NestRadioGroup) findViewById(R.id.radio_group7);
        mRadioGroup8=(NestRadioGroup) findViewById(R.id.radio_group8);
        mRadioGroup9=(NestRadioGroup) findViewById(R.id.radio_group9);
        mRadioGroup10=(NestRadioGroup) findViewById(R.id.radio_group10);

        radio[0]=mRadioGroup1;
        radio[1]=mRadioGroup2;
        radio[2]=mRadioGroup3;
        radio[3]=mRadioGroup4;
        radio[4]=mRadioGroup5;
        radio[5]=mRadioGroup6;
        radio[6]=mRadioGroup7;
        radio[7]=mRadioGroup8;
        radio[8]=mRadioGroup9;
        radio[9]=mRadioGroup10;

        for(int i=0;i<10;i++) {
            radio[i].setOnCheckedChangeListener(new NestRadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(NestRadioGroup group, int checkedId) {
                    dothe();
                }
            });
        }
    }

    private void dothe(){
        String str=getCheckedString();
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                boolean b=SKUHelper.INSTANCE.isValueEnable(str,SKUHelper.TEST_SKUS.get(i).getValues().get(j).getSpecValueId());
                radio[i].getChildAt(j).setEnabled(b);

            }
        }
    }

    private String getCheckedString(){
        String str="";
        for(int i=0;i<10;i++){
            if(radio[i].getCheckedRadioButtonId()==-1){
                continue;
            }
            str=str+SKUHelper.TEST_SKUS.get(i).getValues().get(getCheckedIndex(radio[i])).getSpecValueId()+",";
        }
        if(str.endsWith(",")){
            str=str.substring(0,str.length()-1);
        }
        return str;
    }

    private int getCheckedIndex(NestRadioGroup group){
        int id=group.getCheckedRadioButtonId();
        for(int i=0;i<group.getChildCount();i++){
            if(id==group.getChildAt(i).getId()){
                return i;
            }
        }
        return -1;
    }
}
