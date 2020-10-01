package com.capgemini.collections.addressbook;

import java.util.*;

/**
 * Added ability to delete a contact
 */

/**
 * @author Rachit
 *
 */
public class AddressBookMain {
	Scanner sc = new Scanner(System.in);
	private List<ContactsUC1> addressList = new ArrayList<ContactsUC1>();

	public void addContact(ContactsUC1 contactObj) {
		addressList.add(contactObj);
	}
	//Method to add a new contact
	public boolean editDetails(String firstName, String lastName) {
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
	//Method to edit a contact
	public boolean removeDetails(String firstName, String lastName) {
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
	//Method to delete a contact
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AddressBookMain addressObj = new AddressBookMain();
		int choice = 0;

		while (choice != 3) {
			System.out.println(
					"Enter a choice: \n 1)Add a new contact \n 2)Edit a contact \n 3)Delete Contact \n 4)Exit");
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
				addressObj.addContact(contactObj);
				break;
			}
			case 2: {
				System.out.println("Enter first name, press Enter key, and then enter last name of person to edit details:");
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
				System.out.println("Enter first name, press Enter key, and then enter last name of person to delete data");
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
				System.exit(0);
			}
			}
		}
	}
}
