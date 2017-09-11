package com.xmxnkj.voip.customer.web.models;

import java.util.List;

import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.customer.entity.CallPlan;
import com.xmxnkj.voip.customer.entity.Customer;

public class CustomerList {
	private List<Customer> items;
	
	private ClientUser clientUser; 
	
	private CallPlan callPlan;
	
	
	public CallPlan getCallPlan() {
		return callPlan;
	}

	public void setCallPlan(CallPlan callPlan) {
		this.callPlan = callPlan;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public List<Customer> getItems() {
		return items;
	}

	public void setItems(List<Customer> items) {
		this.items = items;
	}
	
}
