package com.capgemini.collections.addressbook;

import java.util.Comparator;

public class FlexibleSort implements Comparator<ContactsUC1> {
	public enum Order{NAME,CITY,STATE,ZIP}
	
	public Order sortingBy;
	
	public FlexibleSort(Order sortingBy) {
		this.sortingBy = sortingBy;
	}
	
	@Override
	public int compare(ContactsUC1 o1,ContactsUC1 o2) {
		switch(sortingBy) {
		case NAME: 
			return (o1.getFirstName()+o1.getLastName()).compareTo(o2.getFirstName()+o2.getLastName());
		case CITY: 
			return (o1.getCity().compareTo(o2.getCity()));
		case STATE: 
			return (o1.getState().compareTo(o2.getState()));
		case ZIP: 
			return (o1.getZip().compareTo(o2.getZip()));
		default: System.out.println("Invalid choice");
		}
		return 0;
	}
}
