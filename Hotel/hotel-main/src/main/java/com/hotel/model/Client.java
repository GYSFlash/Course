package com.hotel.model;

import java.util.Date;
import java.util.Objects;

public class Client implements Comparable<Client> {

    private static Long counter = 0L;
    private Long id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private Gender gender;
    public Client(){}
    public Client(Date dateOfBirth, String surname, String name, Gender gender) {
        this.id = ++counter;
        this.dateOfBirth = dateOfBirth;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
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
    @Override
    public int compareTo(Client o) {
        return this.surname.compareTo(o.surname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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

