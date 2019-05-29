package com.nano.shoppingsite.exceptions;

public class ElementNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElementNotFoundException(Object item){
		super("could not find element "+ item.toString());
	}
}
