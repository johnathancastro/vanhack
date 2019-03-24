package com.online.farmer.api.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.online.farmer.api.ApiConnectionBroker;
import com.online.farmer.api.service.StorageService;
import com.online.farmer.api.utils.ApiResult;
import com.online.farmer.model.StorageModel;

@Path("storage")
public class StorageResource {
	
	@GET
	@Produces("application/json")
	public String getStorageDashboard () throws Exception {
		try (StorageService service = new StorageService(ApiConnectionBroker.defaultConnectionListener())) {
			return ApiResult.decode(service.getStorageDashboard()).toJson();
		}
	}
	
	@POST
	@Produces("application/json")
	public String addStorage (String data) throws Exception {		
		return ApiResult.from(saveStorage(data)).toJson();
	}
	
	@PUT
	@Path("/{storageKey}")
	@Produces("application/json")
	public String updateStorage (@PathParam("storageKey") int storageKey, String data) throws Exception {		
		return ApiResult.from(saveStorage(storageKey, data)).toJson();
	}
	
	@DELETE
	@Path("/{storageKey}")
	@Produces("application/json")
	public String deleteStorage (@PathParam("storageKey") int storageKey) throws Exception {		
		try (StorageService service = new StorageService(ApiConnectionBroker.defaultConnectionListener())) {
			return ApiResult.from(service.deleteStorage(storageKey)).toJson();
		}
	}
	
	private StorageModel translate(int storageKey, String data) throws Exception {
		JsonElement element = new JsonParser().parse(data);

		if (element == null || !element.isJsonObject())
			throw new RuntimeException("Invalid message format.");
		
		StorageModel storage = StorageModel.parse(element.getAsJsonObject());
		if (storageKey > 0)
			storage.setStorageKey(storageKey);
		return storage;
	}
	private Boolean saveStorage(String data) throws Exception {
		return saveStorage(0, data);
	}
	
	private Boolean saveStorage(int storageKey, String data) throws Exception {
		try (StorageService service = new StorageService(ApiConnectionBroker.defaultConnectionListener())) {
			return service.saveStorage(translate(storageKey, data));
		}
	}
}