package Grading.system;
import java.util.Scanner;

public class StudentGrading {
    public static void main(String[] args) {
        Scanner sgs = new Scanner(System.in);

        int totalStudents = 0;
        int passed = 0;
        int failed = 0;

        while (true) {
            System.out.print("Enter student marks (0-100) or -1 to stop: ");
            int marks = sgs.nextInt();

            if (marks == -1) {
                break; // exit loop
            }

            totalStudents++;

            // Assign grade
            char grade;
            if (marks >= 80) {
                grade = 'A';
            } else if (marks >= 70) {
                grade = 'B';
            } else if (marks >= 60) {
                grade = 'C';
            } else if (marks >= 50) {
                grade = 'D';
            } else {
                grade = 'F';
            }

            System.out.println("Student grade: " + grade);

            // Count pass/fail
            if (marks >= 50) {
                passed++;
            } else {
                failed++;
            }
        }

        // Summary
        System.out.println("\n--- Class Summary ---");
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (totalStudents > 0) {
            double passRate = (passed * 100.0) / totalStudents;
            System.out.printf("Class Pass Rate: %.2f%%\n", passRate);
        }

        sgs.close();
    }
}
