package net.javaguides.junittutorial;

public class Student {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int id;
    public String name;

    public Student(int id, String name ) {
        this.name = name;
        this.id = id;
    }
}
