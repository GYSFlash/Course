package Hotel.Model;

import java.util.Date;

public class Client {
    private Long id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private Gender gender;

    public Client(Long id,Date dateOfBirth, String surname, String name, Gender gender) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        System.out.println("Новый клиент");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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
    public enum Gender {
        MALE, FEMALE;
    }
}

