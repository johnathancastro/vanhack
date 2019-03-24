package com.online.farmer.api.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.online.dao.DatabaseConnectionListener;
import com.online.farmer.model.StorageModel;

public class StorageService extends SimpleService
{
    public StorageService(DatabaseConnectionListener listener) throws Exception
    {
        super(listener);
        
    }

    public String getStorageDashboard() throws Exception
    {
        String sql = "select array_to_json(array_agg(data.*)) from " + 
        		"(" + 
        		"	select s.storage_key, s.name as storage, s.max_capacity, p.name as product, s.product_amount " + 
        		"	from farmer.storage s " + 
        		"	left join farmer.product p on p.product_key = s.product_key " + 
        		") data" + 
        		"";
              
        try (PreparedStatement ps = conn.prepareCall(sql)) {
        	try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next() || rs.getString(1) == null)
		            throw new RuntimeException("Did not find any storage.");
		        return rs.getString(1);
			}
        }
    }
    
    public Boolean saveStorage(StorageModel storage) throws Exception
    {
    	return storage.getStorageKey() > 0 ? updateStorage(storage) : addStorage(storage);
    }
    
    public Boolean addStorage(StorageModel storage) throws Exception
    {
    	int idx = 1;
    	try (PreparedStatement pst = conn.prepareStatement("select farmer.add_storage(?, ?, ?, ?);")) {
    		pst.setString(idx++, storage.getProduct().getName());
			pst.setString(idx++, storage.getStorage());
			pst.setInt(idx++, storage.getProductAmount());
			pst.setInt(idx++, storage.getMaxCapacity());
	        pst.execute();
        }
    	
    	return true;
    }
    
    public Boolean updateStorage(StorageModel storage) throws Exception
    {
    	int idx = 1;
    	try (PreparedStatement pst = conn.prepareStatement("select farmer.update_storage(?, ?, ?, ?, ?);")) {
    		pst.setInt(idx++, storage.getStorageKey());
    		pst.setString(idx++, storage.getProduct().getName());
			pst.setString(idx++, storage.getStorage());
			pst.setInt(idx++, storage.getProductAmount());
			pst.setInt(idx++, storage.getMaxCapacity());
	        pst.execute();
        }
    	
    	return true;
    }
    
    public Boolean deleteStorage(int storageKey) throws Exception
    {
    	int idx = 1;
    	try (PreparedStatement pst = conn.prepareStatement("select farmer.delete_storage(?);")) {
    		pst.setInt(idx++, storageKey);
	        pst.execute();
        }
    	
    	return true;
    }
}