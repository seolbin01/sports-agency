package com.synergy.sports_agency.aggregate;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    private int no;  // 선수 번호
    private String name;
    private double height;
    private double weight;
    private int age;
    private String category;
    private String injury;
    private int salary;   // 연봉
    private Grade grade;   // 실력

    public Player() {
    }

    public Player(int no, String name, double height, double weight, int age, String category, String injury, int salary, Grade grade) {
        this.no = no;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.category = category;
        this.injury = injury;
        this.salary = salary;
        this.grade = grade;
    }

    public Player(String name, double height, double weight, int age, String category, String injury, int salary, Grade grade) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.category = category;
        this.injury = injury;
        this.salary = salary;
        this.grade = grade;
    }


    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInjury() {
        return injury;
    }

    public void setInjury(String injury) {
        this.injury = injury;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Player{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", age=" + age +
                ", category='" + category + '\'' +
                ", injury='" + injury + '\'' +
                ", salary=" + salary +
                ", grade=" + grade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return no == player.no && Double.compare(height, player.height) == 0 && Double.compare(weight, player.weight) == 0 && age == player.age && salary == player.salary && Objects.equals(name, player.name) && Objects.equals(category, player.category) && Objects.equals(injury, player.injury) && grade == player.grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(no, name, height, weight, age, category, injury, salary, grade);
    }
}
