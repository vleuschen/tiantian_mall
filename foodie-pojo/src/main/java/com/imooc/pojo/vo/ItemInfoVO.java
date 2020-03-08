package com.imooc.pojo.vo;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;

import java.util.List;

/**
 * 最新商品VO
 */
public class ItemInfoVO {


    private Items item;
    private ItemsParam itemsParam;
    private List<ItemsImg> itemsImgList;
    private List<ItemsSpec> itemsSpecList;

    public ItemInfoVO() {
    }

    public ItemInfoVO(Items items, ItemsParam itemsParam, List<ItemsImg> itemsImgList, List<ItemsSpec> itemsSpecList) {
        this.item = items;
        this.itemsParam = itemsParam;
        this.itemsImgList = itemsImgList;
        this.itemsSpecList = itemsSpecList;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items items) {
        this.item = items;
    }

    public ItemsParam getItemsParam() {
        return itemsParam;
    }

    public void setItemsParam(ItemsParam itemsParam) {
        this.itemsParam = itemsParam;
    }

    public List<ItemsImg> getItemsImgList() {
        return itemsImgList;
    }

    public void setItemsImgList(List<ItemsImg> itemsImgList) {
        this.itemsImgList = itemsImgList;
    }

    public List<ItemsSpec> getItemsSpecList() {
        return itemsSpecList;
    }

    public void setItemsSpecList(List<ItemsSpec> itemsSpecList) {
        this.itemsSpecList = itemsSpecList;
    }
}
