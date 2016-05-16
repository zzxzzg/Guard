package com.guard.skutest;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yxwang on 16/5/3.
 */
public class ProductDetail {

    /**
     * product_name : 洗车
     * max_price : 1200
     * min_price : 1200
     * avg_price : 1200
     * max_store_price :
     * min_store_price :
     * avg_store_price : 0
     * sales : 0
     * pic_num : 2
     * pics_org : https://cte2-upload.car-me.com/product/101/1.jpg,https://cte2-upload.car-me.com/product/101/2.jpg
     * pics_thum : https://cte2-upload.car-me.com/product/101/1_1.jpg,https://cte2-upload.car-me.com/product/101/2_1.jpg
     * telephone : 15869014741
     * store_num : 1
     * is_collect : 0
     * specs : [{"spec_name":"机油","values":[{"spec_value_name":"机油2号","spec_value_id":"149"}]},{"spec_name":"机滤","values":[{"spec_value_name":"机滤1号","spec_value_id":"150"},{"spec_value_name":"机滤3号","spec_value_id":"260"}]},{"spec_name":"车型","values":[{"spec_value_name":"5座","spec_value_id":"177"}]}]
     * options : [{"option_name":"1","product_options":[{"product_option_id":"137","product_option_name":"保养","product_option_price":"1200"}]},{"option_name":"2","product_options":[{"product_option_id":"138","product_option_name":"精洗","product_option_price":"100"}]}]
     */

    @JSONField(name = "product_name")
    private String mProductName;
    @JSONField(name = "max_price")
    private String mMaxPrice;
    @JSONField(name = "min_price")
    private String mMinPrice;
    @JSONField(name = "avg_price")
    private String mAvgPrice;
    @JSONField(name = "max_store_price")
    private String mMaxStorePrice;
    @JSONField(name = "min_store_price")
    private String mMinStorePrice;
    @JSONField(name = "avg_store_price")
    private String mAvgStorePrice;
    @JSONField(name = "sales")
    private String mSales;
    @JSONField(name = "pic_num")
    private int mPicNum;
    @JSONField(name = "pics_org")
    private String mPicsOrg;
    @JSONField(name = "pics_thum")
    private String mPicsThum;
    @JSONField(name = "telephone")
    private String mTelephone;
    @JSONField(name = "store_num")
    private int mStoreNum;
    @JSONField(name = "is_collect")
    private String mIsCollect;
    /**
     * spec_name : 机油
     * values : [{"spec_value_name":"机油2号","spec_value_id":"149"}]
     */

    @JSONField(name = "specs")
    private List<SpecsBean> mSpecs;
    /**
     * option_name : 1
     * product_options : [{"product_option_id":"137","product_option_name":"保养","product_option_price":"1200"}]
     */

    @JSONField(name = "options")
    private List<OptionsBean> mOptions;
    /**
     * product_id : 81
     * price : 8000
     * store_price : 10000
     * description :
     */

    @JSONField(name = "product_id")
    private long mProductId;
    @JSONField(name = "price")
    private String mPrice;
    @JSONField(name = "store_price")
    private String mStorePrice;
    @JSONField(name = "description")
    private String mDescription;
    /**
     * sku_id : 359
     * sku_spec_code : 149,260,176
     * price : 1100
     * storage : 30
     */

    @JSONField(name = "skus_onsell")
    private List<SkusOnsellBean> mSkusOnsell;

    public SkusOnsellBean getCurrentSkusOnsellBean() {
        return mCurrentSkusOnsellBean;
    }

    private int quantity;

    public void setCurrentSkusOnsellBean(SkusOnsellBean currentSkusOnsellBean) {
        mCurrentSkusOnsellBean = currentSkusOnsellBean;
    }

    private SkusOnsellBean mCurrentSkusOnsellBean;


    public int getQuantity() {
        return 1;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private float mCurrentPrice = -1f;

    public float getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        mTotalPrice = totalPrice;
    }

    private float mTotalPrice = -1f;
    private Map<String, SpecsBean.ValuesBean> mCurrentSpecBeans = new HashMap<>();
    private Map<String, OptionsBean.ProductOptionsBean> mCurrentOptions = new HashMap<>();


    public float getCurrentPrice() {
        return mCurrentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        mCurrentPrice = currentPrice;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getMaxPrice() {
        return mMaxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        mMaxPrice = maxPrice;
    }

    public String getMinPrice() {
        return mMinPrice;
    }

    public void setMinPrice(String minPrice) {
        mMinPrice = minPrice;
    }

    public String getAvgPrice() {
        return mAvgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        mAvgPrice = avgPrice;
    }

    public String getMaxStorePrice() {
        return mMaxStorePrice;
    }

    public void setMaxStorePrice(String maxStorePrice) {
        mMaxStorePrice = maxStorePrice;
    }

    public String getMinStorePrice() {
        return mMinStorePrice;
    }

    public void setMinStorePrice(String minStorePrice) {
        mMinStorePrice = minStorePrice;
    }

    public String getAvgStorePrice() {
        return mAvgStorePrice;
    }

    public void setAvgStorePrice(String avgStorePrice) {
        mAvgStorePrice = avgStorePrice;
    }

    public String getSales() {
        return mSales;
    }

    public void setSales(String sales) {
        mSales = sales;
    }

    public int getPicNum() {
        return mPicNum;
    }

    public void setPicNum(int picNum) {
        mPicNum = picNum;
    }

    public String getPicsOrg() {
        return mPicsOrg;
    }

    public void setPicsOrg(String picsOrg) {
        mPicsOrg = picsOrg;
    }

    public String getPicsThum() {
        return mPicsThum;
    }

    public void setPicsThum(String picsThum) {
        mPicsThum = picsThum;
    }

    public String getTelephone() {
        return mTelephone;
    }

    public void setTelephone(String telephone) {
        mTelephone = telephone;
    }

    public int getStoreNum() {
        return mStoreNum;
    }

    public void setStoreNum(int storeNum) {
        mStoreNum = storeNum;
    }

    public String getIsCollect() {
        return mIsCollect;
    }

    public void setIsCollect(String isCollect) {
        mIsCollect = isCollect;
    }

    public List<SpecsBean> getSpecs() {
        return mSpecs;
    }

    public void setSpecs(List<SpecsBean> specs) {
        mSpecs = specs;
        mCurrentSpecBeans.clear();

        if (mSpecs != null) {
            for (int i = 0; i < mSpecs.size(); i++) {
                SpecsBean bean = mSpecs.get(i);
                if (bean.getValues() != null && bean.getValues().size() == 1) {
                    mCurrentSpecBeans.put(bean.getSpecName(), bean.getValues().get(0));
                }
            }
        }
    }

    public Map<String, SpecsBean.ValuesBean> getCurrentSpecBeans() {
        return mCurrentSpecBeans;
    }

    public void setCurrentSpecBeans(Map<String, SpecsBean.ValuesBean> currentSpecBeans) {
        mCurrentSpecBeans = currentSpecBeans;
    }

    public Map<String, OptionsBean.ProductOptionsBean> getCurrentOptions() {
        return mCurrentOptions;
    }

    public void setCurrentOptions(Map<String, OptionsBean.ProductOptionsBean> currentOptions) {
        mCurrentOptions = currentOptions;
    }

    public List<OptionsBean> getOptions() {
        return mOptions;
    }

    public void setOptions(List<OptionsBean> options) {
        mOptions = options;
        mCurrentOptions.clear();
    }

    public long getProductId() {
        return mProductId;
    }

    public void setProductId(long productId) {
        mProductId = productId;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getStorePrice() {
        return mStorePrice;
    }

    public void setStorePrice(String storePrice) {
        mStorePrice = storePrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<SkusOnsellBean> getSkusOnsell() {
        return mSkusOnsell;
    }

    public void setSkusOnsell(List<SkusOnsellBean> skusOnsell) {
        mSkusOnsell = skusOnsell;
    }

    public static class SpecsBean{
        @JSONField(name = "spec_name")
        private String mSpecName;
        /**
         * spec_value_name : 机油2号
         * spec_value_id : 149
         */

        @JSONField(name = "values")
        private List<ValuesBean> mValues;

        public String getSpecName() {
            return mSpecName;
        }

        public void setSpecName(String specName) {
            mSpecName = specName;
        }

        public List<ValuesBean> getValues() {
            return mValues;
        }

        public void setValues(List<ValuesBean> values) {
            mValues = values;
        }

        public static class ValuesBean {
            @JSONField(name = "spec_value_name")
            private String mSpecValueName;
            @JSONField(name = "spec_value_id")
            private long mSpecValueId;

            public String getSpecValueName() {
                return mSpecValueName;
            }

            public void setSpecValueName(String specValueName) {
                mSpecValueName = specValueName;
            }

            public long getSpecValueId() {
                return mSpecValueId;
            }

            public void setSpecValueId(long specValueId) {
                mSpecValueId = specValueId;
            }
        }
    }

    public static class OptionsBean{
        @JSONField(name = "option_name")
        private String mOptionName;
        /**
         * product_option_id : 137
         * product_option_name : 保养
         * product_option_price : 1200
         */

        @JSONField(name = "product_options")
        private List<ProductOptionsBean> mProductOptions;

        public String getOptionName() {
            return mOptionName;
        }

        public void setOptionName(String optionName) {
            mOptionName = optionName;
        }

        public List<ProductOptionsBean> getProductOptions() {
            return mProductOptions;
        }

        public void setProductOptions(List<ProductOptionsBean> productOptions) {
            mProductOptions = productOptions;
        }

        public static class ProductOptionsBean {
            @JSONField(name = "product_option_id")
            private long mProductOptionId;
            @JSONField(name = "product_option_name")
            private String mProductOptionName;
            @JSONField(name = "product_option_price")
            private String mProductOptionPrice;

            public long getProductOptionId() {
                return mProductOptionId;
            }

            public void setProductOptionId(long productOptionId) {
                mProductOptionId = productOptionId;
            }

            public String getProductOptionName() {
                return mProductOptionName;
            }

            public void setProductOptionName(String productOptionName) {
                mProductOptionName = productOptionName;
            }

            public String getProductOptionPrice() {
                return mProductOptionPrice;
            }

            public void setProductOptionPrice(String productOptionPrice) {
                mProductOptionPrice = productOptionPrice;
            }
        }
    }

    public static class SkusOnsellBean {
        @JSONField(name = "sku_id")
        private int mSkuId;
        @JSONField(name = "sku_spec_code")
        private String mSkuSpecCode;
        @JSONField(name = "price")
        private float mPrice;
        @JSONField(name = "storage")
        private int mStorage;

        public int getSkuId() {
            return mSkuId;
        }

        public void setSkuId(int skuId) {
            mSkuId = skuId;
        }

        public String getSkuSpecCode() {
            return mSkuSpecCode;
        }

        public void setSkuSpecCode(String skuSpecCode) {
            mSkuSpecCode = skuSpecCode;
        }

        public float getPrice() {
            return mPrice;
        }

        public void setPrice(float price) {
            mPrice = price;
        }

        public int getStorage() {
            return mStorage;
        }

        public void setStorage(int storage) {
            mStorage = storage;
        }
    }
}
