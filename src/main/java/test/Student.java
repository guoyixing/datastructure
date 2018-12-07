package test;

import java.util.Objects;

public class Student implements Comparable {
    public int code;
    public String name;

    public Student(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        if (this.code > ((Student) o).code) {
            return 1;
        } else if (this.code < ((Student) o).code) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return code == student.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}