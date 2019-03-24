package com.online.farmer.model;

import com.google.gson.JsonObject;
import com.online.model.json.JsonHandler;

public class StorageModel {
	private int storageKey;
	private String storage;
	private int maxCapacity;
	private ProductModel product;
	private int productAmount;
	
	public static StorageModel parse(JsonObject jsonPart) {
		JsonHandler json = new JsonHandler(jsonPart);
		if (json.getObj() == null) return null;
		StorageModel storage = new StorageModel();
		storage.setStorage(json.extractString("storage"));
		storage.setProduct(new ProductModel(json.extractString("product")));
		storage.setProductAmount(json.extractInteger("product_amount"));
		storage.setMaxCapacity(json.extractInteger("max_capacity"));
		return storage;
	}
	
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public ProductModel getProduct() {
		return product;
	}
	public void setProduct(ProductModel product) {
		this.product = product;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}

	public int getStorageKey() {
		return storageKey;
	}

	public void setStorageKey(int storageKey) {
		this.storageKey = storageKey;
	}
	
	
	
	
}