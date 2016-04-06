package com.wex.poc.form;

import org.springframework.stereotype.Component;

import com.wex.poc.configuration.JsonSpringView;

@Component
public class VPayable {

	@JsonSpringView(springRoles="1006")
	private Integer amount;

	private String from;

	private String to;

	
	public Integer getAmount() {
		return amount;
	}

	@JsonSpringView(springRoles="1006")
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
