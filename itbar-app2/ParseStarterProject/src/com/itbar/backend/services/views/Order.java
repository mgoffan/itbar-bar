package com.itbar.backend.services.views;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Esta es la vista de un pedido que es en esta app DUMMY o POJO como es a veces denominadao. Se usa
 * para que el front vea como es la representacion de este objeto. Sin embargo, los cambios no son
 * registrados pues si el usuario cambiara alguna informacion y ofreciecemos la posibilidad de
 * guardarla entonces estariamos abriendo el programa a que el front gaurde cualquier cosa que se le
 * ocurra. Por esta razon, implementamos los Form.
 * </p>
 * Created by martin on 5/23/15.
 */
public class Order {

	private List<OrderProduct> items = null;

	private User buyer = null;

	private String comment = "";

	private String horario = "";

	private String paymentType = ""; //Crear clase?

	private String status = null;

	private double total = 0;

	private String objectId = "";

	public Order(){
		items = new ArrayList<>();
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getBuyer() {
		return buyer;
	}

	public List<OrderProduct> getItems() {
		return items;
	}

	public void setItems(List<OrderProduct> items) {
		this.items = items;
	}

	public void addItem(OrderProduct item) {
		item.setOrder(this);
		this.items.add(item);
		total += item.getQuantity()*item.getMenuItem().getPrice();
	}

	public void removeItem(OrderProduct item) {
		this.items.remove(item);
		total -= item.getQuantity()*item.getMenuItem().getPrice();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public boolean containsMenuItem(MenuItem item){
		for (OrderProduct p : items){
			if ( p.getMenuItem().equals(item) ){
				return true;
			}
		}
		return false;

	}


}
