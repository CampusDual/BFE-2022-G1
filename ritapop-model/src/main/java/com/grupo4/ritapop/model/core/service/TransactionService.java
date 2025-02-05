package com.grupo4.ritapop.model.core.service;

import java.util.*;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.grupo4.ritapop.api.core.service.ITransactionService;
import com.grupo4.ritapop.model.core.dao.ClientDao;
import com.grupo4.ritapop.model.core.dao.TransactionDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;

@Service("TransactionService")
@Lazy
public class TransactionService implements ITransactionService {

	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private DefaultOntimizeDaoHelper daoHelper;
	@Autowired
	private ClientService clientService;

	@Override
	public EntityResult transactionQuery(Map<String, Object> keyMap, List<String> attrList)
			throws OntimizeJEERuntimeException {
		return this.daoHelper.query(this.transactionDao, keyMap, attrList);
	}


	
	@Override
	public EntityResult transactionInsert(Map<String, Object> attrMap) throws OntimizeJEERuntimeException {

		int id_seller = getIdFromNIF(attrMap.get("NIF_SELLER"));
		attrMap.put("SELLER_CLI",id_seller);
		int id_buyer = getIdFromNIF(attrMap.get("NIF_BUYER"));
		attrMap.put("BUYER_CLI",id_buyer);
		return this.daoHelper.insert(this.transactionDao, attrMap);
	}

	public int getIdFromNIF(Object nif){
		List<String> attrList = new ArrayList<>();
		attrList.add("id");
		Map<String,Object> keyMap = new HashMap<>();
		keyMap.put("nif",nif);
		EntityResult id_entity_result = this.daoHelper.query(this.clientDao, keyMap,attrList);
		Object id_object = id_entity_result.get("id");
		String id_string = id_object.toString();
		String id_string_final = id_string.replace("[","");
		id_string_final = id_string_final.replace("]","");
		int id = Integer.parseInt(id_string_final);
		return id;
	}

	@Override
	public EntityResult transactionUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap)
			throws OntimizeJEERuntimeException {
		return this.daoHelper.update(this.transactionDao, attrMap, keyMap);
	}

	@Override
	public EntityResult transactionDelete(Map<String, Object> keyMap) throws OntimizeJEERuntimeException {
		return this.daoHelper.delete(this.transactionDao, keyMap);
	}

	@Override
	 public EntityResult transactionDetailsQuery(Map<String, Object> keyMap, List<String> attrList)
	   throws OntimizeJEERuntimeException {
		keyMap.put(TransactionDao.ATTR_SALE_STATUS,3); // Cambiar al valor entero 3
	  return this.daoHelper.query(this.transactionDao, keyMap, attrList, TransactionDao.QUERY_TRANSACTION_DETAILS);
	 }
	

	@Override
	public EntityResult transactionDetailsInsert(Map<String, Object> attrMap) throws OntimizeJEERuntimeException {

		int id_seller = getIdFromNIF(attrMap.get("NIF_SELLER"));
		attrMap.put("SELLER_CLI",id_seller);
		int id_buyer = getIdFromNIF(attrMap.get("NIF_BUYER"));
		attrMap.put("BUYER_CLI",id_buyer);
		return this.daoHelper.insert(this.transactionDao, attrMap);
	}
	
	
	@Override
	public EntityResult transactionDetailsDelete(Map<String, Object> keyMap) throws OntimizeJEERuntimeException {
		keyMap.put("id", keyMap.get("ID_TRANSACTION"));
		keyMap.remove("ID_TRANSACTION");
		return this.daoHelper.delete(this.transactionDao, keyMap);
	}
	
	
	@Override
	public EntityResult transactionDetailsUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap)
			throws OntimizeJEERuntimeException {
		keyMap.put("id", keyMap.get("ID_TRANSACTION"));
		keyMap.remove("ID_TRANSACTION");
		return this.daoHelper.update(this.transactionDao,attrMap, keyMap);
	}
	
}
