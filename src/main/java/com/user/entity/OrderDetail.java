package com.user.entity;

public class OrderDetail {
    private String id;

    private String ordersId;

    private String itemsId;

    private Integer itemsNum;
    /**
     * one to one 
     */
    private Items items;

    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getItemsId() {
		return itemsId;
	}

	public void setItemsId(String itemsId) {
		this.itemsId = itemsId;
	}

	public Integer getItemsNum() {
        return itemsNum;
    }

    public void setItemsNum(Integer itemsNum) {
        this.itemsNum = itemsNum;
    }

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}
    
    
}