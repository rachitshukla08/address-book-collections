package com.capgemini.collections.addressbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressBookIOService {
	private static String HOME;
	private HashMap<String, List<ContactsUC1>> addressBookMap;
	public AddressBookIOService() {
		HOME = "D:\\eclipse_workspace\\Address Book Collections\\src\\com\\capgemini\\collections\\addressbook\\Address Books";
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
					Files.lines(file.toAbsolutePath()).forEach(lines -> {
						String data = lines.toString();
						String[] dataArr = data.split(",");
						for (int position = 0; position < dataArr.length; position++) {
							String firstName = dataArr[position].replaceAll("First name=", "");
							position++;
							String lastName = dataArr[position].replaceAll(" Last name=", "");
							position++;
							String address = dataArr[position].replaceAll(" Address=", "");
							position++;
							String city = dataArr[position].replaceAll(" City=", "");
							position++;
							String state = dataArr[position].replaceAll(" State=", "");
							position++;
							String zip = dataArr[position].replaceAll(" Zip=", "");
							position++;
							String phone = dataArr[position].replaceAll(" Phone No=", "");
							position++;
							String email = dataArr[position].replaceAll(" Email=", "");
							ContactsUC1 contact = new ContactsUC1(firstName, lastName, address, city, state, zip, phone,
									email);
							contactList.add(contact);
						}
					});
					String fileName = file.toAbsolutePath().toString().replace(HOME + "\\", "");
					addressBookMap.put(fileName.substring(0, fileName.length() - 4), contactList);
				} catch (IOException e) {
					e.printStackTrace();
				}

			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Creates an Address Book.txt file
	 * 
	 * @param bookName
	 * @return true if address book is created
	 */
	public boolean addAddressBook(String bookName) {
		Path addressBooks = Paths.get(HOME + "/" + bookName + ".txt");
		if (Files.notExists(Paths.get(HOME + "/" + bookName + ".txt"))) {
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
		StringBuffer contactsBuffer = new StringBuffer();
		String contactData = contactObj.toString();
		try {
			Files.lines(Paths.get(HOME + "/" + addressBookName + ".txt")).forEach(lines -> {
				String data = lines.toString().concat("\n");
				contactsBuffer.append(data);
			});
			contactsBuffer.append(contactData);
			Files.write(Paths.get(HOME + "/" + addressBookName + ".txt"), contactsBuffer.toString().getBytes());
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

	public static String getHOME() {
		return HOME;
	}

	public static void setHOME(String hOME) {
		HOME = hOME;
	}

	public HashMap<String, List<ContactsUC1>> getAddressBookMap() {
		return addressBookMap;
	}

	public void setAddressBookMap(HashMap<String, List<ContactsUC1>> addressBookMap) {
		this.addressBookMap = addressBookMap;
	}
}
