package assignment.entities;
public class Triangle {
    private final double height;
    private final double base;
    private final double hyp;
    public Triangle(double height, double base) {
        this.height = height;
        this.base = base;
        this.hyp = Math.sqrt(Math.pow(height, 2) + Math.pow(base, 2));
    }
    public double getHeight() {
        return height;
    }

    public double getBase() {
        return base;
    }

    public double getHyp() {
        return hyp;
    }
    public double calculatePerimeter() {
        return hyp + base + height;
    }
    public double calculateArea() {
        return base * height / 2;
    }

}
