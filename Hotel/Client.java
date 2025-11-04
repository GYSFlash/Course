package Hotel;

import java.util.Date;

public class Client {
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String gender;

    public Client(Date dateOfBirth, String surname, String name, String gender) {
        this.dateOfBirth = dateOfBirth;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        System.out.println("Новый клиент");
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    @Override
    public String toString() {
        return "Клиент: " + name + " " + surname;
    }
}
