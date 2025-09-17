package Billings;

import java.util.Scanner;

public class billingsystem {
	public static void main(String[] args) {
		
		Scanner smt = new Scanner(System.in);

		        // Step 1: Ask how many different items
		        System.out.print("How many different items did the customer buy? ");
		        int numItems = smt.nextInt();
		        smt.nextLine(); // consume newline

		        double totalBill = 0;
		        String[] itemNames = new String[numItems];
		        int[] quantities = new int[numItems];
		        double[] prices = new double[numItems];
		        double[] subtotals = new double[numItems];

		        // Step 2 & 3: Process each item
		        for (int i = 0; i < numItems; i++) {
		            System.out.print("Enter name of item " + (i + 1) + ": ");
		            itemNames[i] = smt.nextLine();

		            System.out.print("Enter price per unit of " + itemNames[i] + ": ");
		            prices[i] = smt.nextDouble();

		            System.out.print("Enter quantity of " + itemNames[i] + ": ");
		            quantities[i] = smt.nextInt();
		            smt.nextLine(); 

		            subtotals[i] = prices[i] * quantities[i];
		            totalBill += subtotals[i];
		        }

		        // Step 5: Apply discount
		        double discount = 0;
		        if (totalBill > 50000) {
		            discount = totalBill * 0.05;
		        }
		        double finalAmount = totalBill - discount;

		        // Step 6: Print receipt
		        System.out.println("\n--- Receipt ---");
		        System.out.printf("%-15s %-10s %-10s %-10s%n", "Item", "Qty", "Price", "Subtotal");
		        for (int i = 0; i < numItems; i++) {
		            System.out.printf("%-15s %-10d %-10.2f %-10.2f%n", itemNames[i], quantities[i], prices[i], subtotals[i]);
		        }

		        System.out.printf("%nGrand Total: %.2f%n", totalBill);
		        System.out.printf("Discount: %.2f%n", discount);
		        System.out.printf("Final Amount Payable: %.2f%n", finalAmount);

		        smt.close();
		    }
		}
