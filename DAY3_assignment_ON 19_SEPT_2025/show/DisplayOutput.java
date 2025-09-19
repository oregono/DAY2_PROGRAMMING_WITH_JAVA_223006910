package assignment.show;

import assignment.entities.Rectangle;
import assignment.entities.Triangle;
import individual_assisgnment.entities.Circle;
import individual_assisgnment.entities.Square;

import java.util.Scanner;

public class DisplayOutput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char selected;
        do {
            System.out.println("Select shape Below");
            System.out.println("1. Rectangle");
            System.out.println("2. Triangle");
            System.out.println("3. Circle");
            System.out.println("4. Square");
            System.out.println("Choose your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the length of a rectangle");
                    double length = sc.nextDouble();
                    System.out.println("Enter the width of a rectangle");
                    double width = sc.nextDouble();
                    Rectangle rectangle = new Rectangle(length, width);
                    System.out.println("The length: " + rectangle.getLength());
                    System.out.println("The width: " + rectangle.getWidth());
                    System.out.println("Do you want area or perimeter of rectangle");
                    System.out.println("1. Area");
                    System.out.println("2. Perimeter");
                    System.out.println("Choose your choice: ");
                    int chosen = sc.nextInt();
                    if (chosen == 1) {
                        System.out.println("The are of Rectangle: " + rectangle.calculateArea());
                    }else if (chosen == 2) {
                        System.out.println("The perimeter of Rectangle: " + rectangle.calculatePerimeter());
                    }
                    break;
                case 2:
                    System.out.println("Enter the base of a triangle");
                    double base = sc.nextDouble();
                    System.out.println("Enter the height of a triangle");
                    double height = sc.nextDouble();
                    Triangle triangle = new Triangle(base, height);
                    System.out.println("The base: " + triangle.getBase());
                    System.out.println("The height: " + triangle.getHeight());
                    System.out.println("Do you want area or perimeter of rectangle");
                    System.out.println("1. Area");
                    System.out.println("2. Perimeter");
                    System.out.println("Choose your choice: ");
                    int choose  = sc.nextInt();
                    if (choose == 1) {
                        System.out.println("The area of Triangle: " + triangle.calculateArea());
                    } else if (choose == 2) {
                        System.out.println("The perimeter of Triangle: " + triangle.calculatePerimeter());
                    }else  {
                        System.out.println("Invalid choice");
                    }
                    break;
                case 3:
                    System.out.println("Enter the radius of a circle");
                    double radius = sc.nextDouble();
                    Circle circle = new Circle(radius);
                    System.out.println("Do you want area or circumference? ");
                    System.out.println("1, Area");
                    System.out.println("2, Circumference");
                    System.out.println("Choose your choice: ");
                    int choose2 = sc.nextInt();
                    if (choose2 == 1) {
                        System.out.println("The area of Circle: " + circle.calculateArea());
                    }else if (choose2 == 2) {
                        System.out.println("The circumference is:  " + circle.calculateCircumference());
                    } else {
                        System.out.println("Invalid choice");
                    }
                    break;
                case 4:
                    System.out.println("Enter the side of a square");
                    double side = sc.nextDouble();
                    Square square = new Square(side);
                    System.out.println("Do you want area or Perimeter of square? ");
                    System.out.println("1, Area");
                    System.out.println("2, Perimeter");
                    System.out.println("Choose your choice: ");
                    int choose3 = sc.nextInt();
                    if (choose3 == 1) {
                        System.out.println("The area of Square: " + square.calculateArea());
                    }else if (choose3 == 2) {
                        System.out.println("The perimeter of Square: " + square.calculatePerimeter());
                    } else {
                        System.out.println("Invalid choice");

                    }
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            System.out.println("Do you want to continue (y/n): ?");
           selected = sc.next().charAt(0);
        } while (selected == 'y');
    }
}
