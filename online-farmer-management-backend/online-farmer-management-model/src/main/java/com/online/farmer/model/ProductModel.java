package com.online.farmer.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.online.model.json.JsonHandler;

public class ProductModel {
	private String name;
	private String description;
	private Date ts;
	private List<StorageModel> storages;
	
	public static ProductModel parse(JsonObject jsonProduct) {
		JsonHandler json = new JsonHandler(jsonProduct);
		if (json.getObj() == null) return null;
		ProductModel product = new ProductModel(json.extractString("product"));
		return product;
	}
	
	public ProductModel() {
		
	}
	
	public ProductModel(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	public void addStorage(StorageModel storage) {
		if (storage != null) {
			storage.setProduct(this);
			this.storages.add(storage);
		}
	}

	public void addStorages(Collection<? extends StorageModel> storages) {
		if (storages != null) {
			storages.forEach(s -> addStorage(s));
		}
	}
	
	public List<StorageModel> getStorages() {
		return this.storages;
	}

	
	
}