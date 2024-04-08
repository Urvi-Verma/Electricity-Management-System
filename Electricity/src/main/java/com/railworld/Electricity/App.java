package com.railworld.Electricity;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class App {

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("***********************************************|");
		System.out.print("---------Please Select Your User Type :--------|\n");
		System.out.print("--------------1. Admin login ------------------|\n");
		System.out.print("--------------2. Customer login ---------------|\n");
		System.out.println("***********************************************|");
		System.out.println(" ");
		System.out.print("Enter your choice: ");
		boolean loginexit = false;
		while (!loginexit) {
			int userchoice = scanner.nextInt();
			scanner.nextLine();

			switch (userchoice) {
			case 1:
				if (Adminlogin()) {
					adminMenu();
				}
				break;
			case 2:
				if (Userlogin()) {
					customerMenu();
				}
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	private static boolean Adminlogin() {
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Users user = (Users) session
					.createQuery("FROM Users WHERE name = :username AND password = :password AND role = 'admin'", Users.class)
					.setParameter("username", username).setParameter("password", password).uniqueResult();
			session.close();
			if (user != null) {
				return true;
			} else {
				System.out.println("Incorrect username or password.");
				System.exit(0);
				return false;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	
	}


	private static boolean Userlogin() {
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Users user = (Users) session
					.createQuery("FROM Users WHERE name = :username AND password = :password AND role = 'customer'", Users.class)
					.setParameter("username", username).setParameter("password", password).uniqueResult();
			session.close();
			if (user != null) {
				return true;
			} else {
				System.out.println("Incorrect username or password.");
				System.exit(0);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void adminMenu() {
		boolean exit = false;
		while (!exit) {
			System.out.println("\n|*********************************************************************|");
			System.out.println("|**********  Electricity Bill  Management System (Admin Panel) *******|");
			System.out.println("|*********************************************************************|");
			System.out.println("|---------------------------------------------------------------------|");
			System.out.println("|--------------1. Add Customer Details -------------------------------|");
			System.out.println("|--------------2. Update Customer Details ----------------------------|");
			System.out.println("|--------------3. Delete Customer Provider----------------------------|");
			System.out.println("|--------------4. View All Customers ---------------------------------|");
			System.out.println("|--------------5. View Raised Request --------------------------------|");
			System.out.println("|--------------6. Logout----------------------------------------------|");
			System.out.println("|---------------------------------------------------------------------|");
			System.out.println(
					"/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				addElectricityBill();
				break;
			case 2:
				updateElectricityBill();
				break;
			case 3:
				deleteElectricityBill();
				break;
			case 4:
				viewAllElectricityBills();
				break;
			case 5:
				viewRaisedRequests();
				break;
			case 6:
				System.out.print("Thank You !!! \n");
				exit = true;
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
		HibernateUtil.shutdown();
		System.exit(0);
	}

	private static void customerMenu() {

		boolean exit = false;
		while (!exit) {
			System.out.println("\n|*********************************************************************|");
			System.out.println("|*******  Electricity Bill  Management System (Customer Panel)  ******|");
			System.out.println("|*********************************************************************|");
			System.out.println("|---------------------------------------------------------------------|");
			System.out.println("|--------------1. View Your Bill -------------------------------------|");
			System.out.println("|--------------2. Calculate Your Bill --------------------------------|");
			System.out.println("|--------------3. Raise A Request ------------------------------------|");
			System.out.println("|--------------4. Logout----------------------------------------------|");
			System.out.println("|---------------------------------------------------------------------|");
			System.out.println(
					"/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				fetchElectricityBillByUsername();
				break;
			case 2:
				calculateElectricityBill();
				break;
			case 3:
				raiseRequest();
				break;
			case 4:
				System.out.print("Thank You..Please Visit Again !!! \n");
				exit = true;
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
		HibernateUtil.shutdown();
		System.exit(0);
	}

	private static void addElectricityBill() {
		System.out.print("Enter Customer name: ");
		String name = scanner.nextLine();
		System.out.print("Enter The Consume Unit (in watt) :");
		double unit = scanner.nextInt();
		double charges = unit * 3;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Electricity electricity = new Electricity();
		electricity.setName(name);
		electricity.setUnit(unit);
		electricity.setCharges(charges);
		session.persist(electricity);
		transaction.commit();
		session.close();
		System.out.println("Customer's Electricity bill added successfully!");
	}

	private static void updateElectricityBill() {
		System.out.print("Enter Bill No. to update: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Electricity electricity = session.get(Electricity.class, id);
		if (electricity != null)

		{
			System.out.print("Enter new customer name: ");

			String name = scanner.nextLine();
			System.out.print("Enter the updated Unit (in watt): ");
			double unit = scanner.nextDouble();
			double charges = unit * 3;

			electricity.setName(name);
			electricity.setUnit(unit);
			electricity.setCharges(charges);
			session.merge(electricity);
			transaction.commit();
			System.out.println("Customer's Electricity Bill updated successfully!");
		} else {
			System.out.println("Customer's Electricity Bill not found!");
		}
		session.close();
	}

	private static void deleteElectricityBill() {
		System.out.print("Enter Electricity Bill No. to delete: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Electricity electricity = session.get(Electricity.class, id);
		if (electricity != null) {
			session.remove(electricity);
			transaction.commit();
			System.out.println("Customer's Electricity Bill deleted successfully!");
		} else {
			System.out.println("Customer's Electricity Bill not found!");
		}
		session.close();
	}

	private static void viewAllElectricityBills() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Electricity> electricityBill = session.createQuery("from Electricity", Electricity.class).list();
		session.close();
		System.out.println("\nAll Elecitricity Bill :");
		System.out.println("BillNo.	Name    Unit 	Charges");
		for (Electricity electric : electricityBill) {
			System.out.println(electric.getId() + "\t" + electric.getName() + "\t" + electric.getUnit() + "\t"
					+ electric.getCharges());
		}
	}

	private static void viewRaisedRequests() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Electricity_Request> electricityRequest = session
				.createQuery("from Electricity_Request", Electricity_Request.class).list();
		session.close();
		System.out.println("\nAll Raised Requests :");
		System.out.println("S.No.  BillNo.	 Name    Request");
		for (Electricity_Request electric_req : electricityRequest) {
			System.out.println(electric_req.getEid() + "\t" + electric_req.getCust_billno() + "\t"
					+ electric_req.getCust_name() + "\t" + electric_req.getCust_request());
		}
	}

	private static void fetchElectricityBillByUsername() {
		System.out.print("Enter Customer Name to fetch details: ");
		String customerName = scanner.nextLine();

		Session session = HibernateUtil.getSessionFactory().openSession();

		Query<Electricity> query = session.createQuery("FROM Electricity WHERE name = :customerName",
				Electricity.class);
		query.setParameter("customerName", customerName);

		List<Electricity> electricityList = query.getResultList();

		if (!electricityList.isEmpty()) {
			for (Electricity electricity : electricityList) {
				System.out.println("-----------------------------------------");
				System.out.println("-----------------------------------------");
				System.out.println("Bill No.: " + electricity.getId());
				System.out.println("Customer Name: " + electricity.getName());
				System.out.println("Unit: " + electricity.getUnit());
				System.out.println("Charges: " + electricity.getCharges());
				System.out.println("-----------------------------------------");
			}
		} else {
			System.out.println("No electricity bills found for the given customer name.");
		}

		session.close();
	}

	private static void raiseRequest() {
		System.out.println("Please Enter Your Bill No. : ");
		int cust_billno = scanner.nextInt();
		scanner.nextLine();

		System.out.println("Please Enter Your Name : ");
		String cust_name = scanner.nextLine();

		System.out.println("Please Enter Your Request : ");
		String cust_request = scanner.nextLine();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Electricity_Request electricity_request = new Electricity_Request();
		electricity_request.setCust_billno(cust_billno);
		electricity_request.setCust_name(cust_name);
		electricity_request.setCust_request(cust_request);
		session.merge(electricity_request);
		transaction.commit();
		session.close();
		System.out.println("Your request Sent Successfully!");
	}

	private static void calculateElectricityBill() {
		System.out.println("Please Enter Your Name : ");
		String name = scanner.nextLine();

		System.out.println("Please Enter Your BP No. : ");
		int bpno = scanner.nextInt();

		System.out.println("Please Enter No. Of Units (in watt) For Electricity Bill : ");
		int unitsbill = scanner.nextInt();
		scanner.nextLine();

		double billAmount = calculateBill(unitsbill);

		System.out.println("\n|*********************************************************************|");
		System.out.println("|********************  Electricity Bill Invoice **********************|");
		System.out.println("|*********************************************************************|");
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("|                    Customer Name :  " + name);
		System.out.println("|                    Customer BP No.: " + bpno);
		System.out.println("|                    Units Used (in Watts) : " + unitsbill);
		System.out.println("|                    Bill Amount : Rs." + billAmount);
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("|---------------------------------------------------------------------|");
	}

	private static double calculateBill(int unitsbill) {
		double billToPay = 0;

		if (unitsbill < 100) {
			billToPay = unitsbill * 1.20;
		} else if (unitsbill < 300) {
			billToPay = unitsbill * 3.45;
		} else {
			billToPay = unitsbill * 5.75;
		}

		return billToPay;
	}
}
