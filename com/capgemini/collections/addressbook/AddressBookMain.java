package com.capgemini.collections.addressbook;

import java.util.*;

/**
 * Ability to get number of contact persons i.e.count by City or State
 */

/**
 * @author Rachit
 *
 */
public class AddressBookMain {
	Scanner sc = new Scanner(System.in);
	private List<ContactsUC1> addressList = new LinkedList<ContactsUC1>();
	HashMap<String, List<ContactsUC1>> addressBookMap = new HashMap<String, List<ContactsUC1>>();
	// Map to store multiple address books to satisfy condition of unique name
	HashMap<ContactsUC1,String> personCityMap = new HashMap<ContactsUC1, String>();
	HashMap<ContactsUC1,String> personStateMap = new HashMap<ContactsUC1, String>();
	//Dictionary of city and person as well as state and person for UC9
	
	/**
	 * @param contactIsAdded
	 * @param contactObj
	 */
	private void addToDictionary(boolean contactIsAdded,ContactsUC1 contactObj) {
		if(contactIsAdded==true) {
			personCityMap.put(contactObj, contactObj.getCity());
			personStateMap.put(contactObj, contactObj.getState());
		}
	}
	/**
	 * @param contactObj
	 * @return True if contact is added.
	 * (Add contact to address book UC 2 and ensure there is no duplicate contact UC7)
	 */
	private boolean addContact(ContactsUC1 contactObj) {
		boolean isPresent = addressList.stream().anyMatch(obj -> obj.equals(contactObj));
		if (isPresent == false) {
			addressList.add(contactObj);
			return true;
		}
		else {
			System.out.println("Contact already present. Duplication not allowed");
			return false;
		}
	}
	/**
	 * @param firstName
	 * @param lastName
	 * @return True if details successfully edited(UC3)
	 */
	private boolean editDetails(String firstName, String lastName) {
		ContactsUC1 editObj;
		boolean contactFound = false;
		for (int i = 0; i < addressList.size(); i++) {
			editObj = (ContactsUC1) addressList.get(i);
			if ((editObj.getFirstName().equals(firstName)) && (editObj.getLastName().equals(lastName))) {
				System.out.println("Enter new Address:");
				editObj.setAddress(sc.nextLine());
				System.out.println("Enter new City");
				editObj.setCity(sc.nextLine());
				System.out.println("Enter new State");
				editObj.setState(sc.nextLine());
				System.out.println("Enter new Zip");
				editObj.setZip(sc.nextLine());
				System.out.println("Enter new Phone no");
				editObj.setPhoneNo(sc.nextLine());
				System.out.println("Enter new Email");
				editObj.setEmail(sc.nextLine());
				contactFound = true;
				break;
			}
		}
		return contactFound;
	}
	/**
	 * @param firstName
	 * @param lastName
	 * @return True if contact successfully removed(UC4)
	 */
	private boolean removeDetails(String firstName, String lastName) {
		ContactsUC1 removeObj;
		boolean contactFound = false;
		for (int i = 0; i < addressList.size(); i++) {
			removeObj = (ContactsUC1) addressList.get(i);
			if ((removeObj.getFirstName().equals(firstName)) && (removeObj.getLastName().equals(lastName))) {
				addressList.remove(i);
				contactFound = true;
				break;
			}
		}
		return contactFound;
	}
	/**
	 * @param listName
	 */
	private void addAddressList(String listName) {
		List<ContactsUC1> newAddressList = new LinkedList<ContactsUC1>();
		addressBookMap.put(listName, newAddressList);
		System.out.println("Address Book added");
	}
	/**
	 * @param searchPerson
	 * @param searchChoice
	 * @param cityOrState 
	 */
	private void searchPersonAcrossCityState(String searchPerson,int searchChoice, String cityOrState) {
		for (Map.Entry<String, List<ContactsUC1>> entry : addressBookMap.entrySet()) {
			List<ContactsUC1> list = entry.getValue();
			if (searchChoice == 1)
				list.stream().filter(obj -> ((obj.getCity().equals(cityOrState))&&(obj.getFirstName().equals(searchPerson)))).forEach(System.out::println);
			else if(searchChoice == 2)
				list.stream().filter(obj -> ((obj.getState().equals(cityOrState))&&(obj.getFirstName().equals(searchPerson)))).forEach(System.out::println);
		}
	}
	/**
	 * @param cityOrState
	 * @param searchChoice
	 */
	private void viewPersonsByCityState(String cityOrState, int searchChoice) {
		for (Map.Entry<String, List<ContactsUC1>> entry : addressBookMap.entrySet()) {
			List<ContactsUC1> list = entry.getValue();
			if (searchChoice == 1)
				list.stream().filter(obj -> obj.getCity().equals(cityOrState)).forEach(System.out::println);
			else if(searchChoice == 2)
				list.stream().filter(obj -> obj.getState().equals(cityOrState)).forEach(System.out::println);
		}
	}
	/**
	 * @param cityOrState
	 * @param searchChoice
	 * @return Count of people in a city or state(UC10)
	 */
	private long getCountByCityState(String cityOrState, int searchChoice) {
		long count=0;
		for (Map.Entry<String, List<ContactsUC1>> entry : addressBookMap.entrySet()) {
			List<ContactsUC1> list = entry.getValue();
			if (searchChoice == 1)
				count+= list.stream().filter(obj -> obj.getCity().equals(cityOrState)).count();
			else if(searchChoice == 2)
				count+= list.stream().filter(obj -> obj.getState().equals(cityOrState)).count();
		}
		return count;
	}
	
	private List<ContactsUC1> sortAddressBookByName(List<ContactsUC1> sortList) {
		Collections.sort(sortList,new ContactsUC1());
		return sortList;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AddressBookMain addressObj = new AddressBookMain();
		int choice = 0;
		System.out.println("Welcome to address book program");

		while (choice != 10) {
			if (addressObj.addressBookMap.isEmpty()) {
				System.out.println("Please add an address book to begin");
				System.out.println("Enter the name of address book that u want to add:");
				String listName = sc.nextLine();
				addressObj.addAddressList(listName);
			}
			System.out.println("Enter the name of the address book you want to access");
			String listName = sc.nextLine();
			if (addressObj.addressBookMap.containsKey(listName)) {
				addressObj.addressList = addressObj.addressBookMap.get(listName);
			}

			else {
				System.out.println("Address list with name" + listName + " not present. Please add it first.");
			}
			System.out.println(
					"Enter a choice: \n 1)Add a new contact \n 2)Edit a contact \n 3)Delete Contact \n 4)Add Address Book \n 5)View current Address Book Contacts"
							+ " \n 6)Search person in a city or state across the multiple Address Books \n 7)View persons by city or state \n "
							+ "8)Get count of contact persons by city or state \n 9)Sort entries by name in current address book \n 10)Exit");
			choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1: {
				System.out.println("Add Person Details:");
				System.out.println("First Name:");
				String firstName = sc.nextLine();
				System.out.println("Last Name:");
				String lastName = sc.nextLine();
				System.out.println("Address:");
				String address = sc.nextLine();
				System.out.println("City:");
				String city = sc.nextLine();
				System.out.println("State:");
				String state = sc.nextLine();
				System.out.println("Zip:");
				String zip = sc.nextLine();
				System.out.println("Phone no:");
				String phoneNo = sc.nextLine();
				System.out.println("Email");
				String email = sc.nextLine();
				// Input
				ContactsUC1 contactObj = new ContactsUC1(firstName, lastName, address, city, state, zip, phoneNo,
						email);
				boolean contactIsAdded = addressObj.addContact(contactObj);
				//UC1
				addressObj.addToDictionary(contactIsAdded,contactObj);
				//UC9
				break;
			}
			case 2: {
				System.out.println(
						"Enter first name, press Enter key, and then enter last name of person to edit details:");
				String firstName = sc.nextLine();
				String lastName = sc.nextLine();
				boolean contactFound = addressObj.editDetails(firstName, lastName);
				if (contactFound == true)
					System.out.println("Details successfully edit");
				else
					System.out.println("Contact not found");
				break;
			}
			case 3: {
				System.out.println(
						"Enter first name, press Enter key, and then enter last name of person to delete data");
				String firstName = sc.nextLine();
				String lastName = sc.nextLine();
				boolean contactFound = addressObj.removeDetails(firstName, lastName);
				if (contactFound == true)
					System.out.println("Details successfully deleted");
				else
					System.out.println("Contact not found");
				break;
			}
			case 4: {
				System.out.println("Enter the name of address book that u want to add:");
				listName = sc.nextLine();
				addressObj.addAddressList(listName);
				break;
			}
			case 5: {
				System.out.println(" " + addressObj.addressList);
				break;
			}
			case 6: {
				System.out.println("Enter first name of person to search");
				String searchPerson = sc.nextLine();
				System.out.println("Enter the name of city or state");
				String cityOrState = sc.nextLine();
				System.out.println("Enter 1 if you entered name of a city \nEnter 2 if you entered name of a state");
				int searchChoice = Integer.parseInt(sc.nextLine());
				addressObj.searchPersonAcrossCityState(searchPerson,searchChoice, cityOrState);
				break;
			}
			case 7: {
				System.out.println("Enter the name of city or state");
				String cityOrState = sc.nextLine();
				System.out.println("Enter 1 if you entered name of a city \nEnter 2 if you entered name of a state");
				int searchChoice = Integer.parseInt(sc.nextLine());
				addressObj.viewPersonsByCityState(cityOrState,searchChoice);
				break;
			}
			case 8: {
				System.out.println("Enter the name of city or state");
				String cityOrState = sc.nextLine();
				System.out.println("Enter 1 if you entered name of a city \nEnter 2 if you entered name of a state");
				int searchChoice = Integer.parseInt(sc.nextLine());
				System.out.println("Total persons in "+cityOrState+" = "+addressObj.getCountByCityState(cityOrState,searchChoice));
				break;
			}
			case 9: {
				List<ContactsUC1> sortedEntriesList = addressObj.sortAddressBookByName(addressObj.addressList);
				System.out.println("Entries sorted in current address book. Sorted Address Book Entries:");
				System.out.println(sortedEntriesList);
				break;
			}
			case 10: {
				System.out.println("Thank you for using the application");
			}
			}
		}
	}
	
}
