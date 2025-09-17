package ClassAttendance;
import java.util.Scanner;
public class Attendance {
	public static void main(String[] args) {

		Scanner cat = new Scanner(System.in);

		// Step 1: Total students
		System.out.print("Enter total number of students in the class: ");
		int totalStudents = cat.nextInt();

		int[] attendance = new int[30]; // Max 30 days
		int day = 0;
		String more;

		do {
			System.out.print("Enter number of students present on day " + (day + 1) + ": ");
			attendance[day] = cat.nextInt();
			day++;

			cat.nextLine(); // consume newline
			System.out.print("Do you want to enter attendance for another day? (yes/no): ");
			more = cat.nextLine();
		} while (more.equalsIgnoreCase("yes") && day < 30);

		// Step 3: Analyze attendance
		int totalAttendance = 0;
		int lowAttendanceDays = 0;
		System.out.println("\n--- Attendance List ---");
		for (int i = 0; i < day; i++) {
			System.out.println("Day " + (i + 1) + " -> Present: " + attendance[i]);
			totalAttendance += attendance[i];
			if (attendance[i] < (totalStudents / 2.0)) {
				lowAttendanceDays++;
			}
		}

		double avgAttendance = totalAttendance * 1.0 / day;
		double lowAttendancePercent = (lowAttendanceDays * 100.0) / day;

		System.out.printf("\nAverage Attendance: %.2f%n", avgAttendance);
		System.out.printf("Percentage of days with low attendance: %.2f%%\n", lowAttendancePercent);

		cat.close();
	}


}

