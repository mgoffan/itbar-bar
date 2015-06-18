package com.itbar.frontend.Models;

/**
 *
 * @deprecated
 */
public class PriceModel {

	double price = 0.0;

	public void Price() {
	}

	public double getPrice() {
		return price;
	}

	public void add(double p) {
		price += p;
	}

	public void delete(double p) {
		price -= p;
	}

	public String toString() {
		return "" + price;
	}


}
