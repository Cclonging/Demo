package com.example.demo.pojo;

public class Student {

    private String name;

    private int math;

    private int english;

    private int computer;

    public Student() {
    }

    public Student(String name, int math, int english, int computer) {
        this.name = name;
        this.math = math;
        this.english = english;
        this.computer = computer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getComputer() {
        return computer;
    }

    public void setComputer(int computer) {
        this.computer = computer;
    }
}
