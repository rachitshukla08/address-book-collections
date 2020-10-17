package com.capgemini.collections.addressbook.service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.capgemini.collections.addressbook.ContactsUC1;
import com.capgemini.collections.addressbook.ReadWriteService;
import com.google.gson.Gson;

public class AddressBookJSONService implements ReadWriteService {
	private static String HOME = "D:\\eclipse_workspace\\Address Book Collections\\src\\com\\capgemini\\collections\\addressbook\\Address Books JSON";
	private HashMap<String, List<ContactsUC1>> addressBookMap;

	public AddressBookJSONService() {
		addressBookMap = new HashMap<String, List<ContactsUC1>>();
		readDataFromAddressBook();
	}

	/**
	 * Get data from Address Book.txt file from directory and store it to address
	 * book map
	 */
	public void readDataFromAddressBook() {
		try {
			Files.walk(Paths.get(HOME)).filter(Files::isRegularFile).forEach(file -> {
				List<ContactsUC1> contactList = new ArrayList<ContactsUC1>();
				try {
					Reader reader = Files.newBufferedReader(file.toAbsolutePath());
					contactList.addAll(Arrays.asList(new Gson().fromJson(reader, ContactsUC1[].class)));
					String fileName = file.toAbsolutePath().toString().replace(HOME + "\\", "");
					addressBookMap.put(fileName.substring(0, fileName.length() - 5), contactList);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Creates an Address Book.json file
	 * 
	 * @param bookName
	 * @return true if address book is created
	 */
	public boolean addAddressBook(String bookName) {
		Path addressBooks = Paths.get(HOME + "/" + bookName + ".json");
		if (Files.notExists(Paths.get(HOME + "/" + bookName + ".json"))) {
			try {
				Files.createFile(addressBooks);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * Adds a contact to the particular address book
	 * 
	 * @param contactObj
	 * @param addressBookName
	 */
	public void writeContactToAddressBook(ContactsUC1 contactObj, String addressBookName) {
		try {
			Writer writer = Files.newBufferedWriter(Paths.get(HOME + "\\" + addressBookName + ".json"));
			List<ContactsUC1> contactList = addressBookMap.get(addressBookName);
			if (contactList == null) {
				contactList = new ArrayList<ContactsUC1>();
			}
			contactList.add(contactObj);
			new Gson().toJson(contactList, writer);
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Print contact details from address books
	 */
	public void print() {
		addressBookMap.entrySet().stream().map(entry -> entry.getValue()).forEach(System.out::println);
	}

	public HashMap<String, List<ContactsUC1>> getAddressBookMap() {
		return addressBookMap;
	}

	public void setAddressBookMap(HashMap<String, List<ContactsUC1>> addressBookMap) {
		this.addressBookMap = addressBookMap;
	}
}
