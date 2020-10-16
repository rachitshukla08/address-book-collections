package com.capgemini.collections.addressbook;

public interface ReadWriteService {
	public void readDataFromAddressBook();
	public boolean addAddressBook(String bookName);
	public void writeContactToAddressBook(ContactsUC1 contactObj, String addressBookName);
	public void print();
}
