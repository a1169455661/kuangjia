package com.easaa;

import com.easaa.response.bean.BasePageEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class QuNisDmasd extends BasePageEntity {

    /**
     * cart_id : 26
     * goods_id : 2
     * goods_name : 笔记本
     * goods_number : 3
     * goods_sn : 002
     * goods_stock : 10
     * goods_tags : 游戏
     * list_img : /fileUploadTest/mallframe/product/2016/8/1/1470044946915.jpg
     * shop_price : 5000
     * user_id : 11
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int cart_id;
        private int goods_id;
        private String goods_name;
        private int goods_number;
        private String goods_sn;
        private int goods_stock;
        private String goods_tags;
        private String list_img;
        private int shop_price;
        private int user_id;

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }

        public String getGoods_sn() {
            return goods_sn;
        }

        public void setGoods_sn(String goods_sn) {
            this.goods_sn = goods_sn;
        }

        public int getGoods_stock() {
            return goods_stock;
        }

        public void setGoods_stock(int goods_stock) {
            this.goods_stock = goods_stock;
        }

        public String getGoods_tags() {
            return goods_tags;
        }

        public void setGoods_tags(String goods_tags) {
            this.goods_tags = goods_tags;
        }

        public String getList_img() {
            return list_img;
        }

        public void setList_img(String list_img) {
            this.list_img = list_img;
        }

        public int getShop_price() {
            return shop_price;
        }

        public void setShop_price(int shop_price) {
            this.shop_price = shop_price;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
