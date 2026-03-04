package com.hotel.dto;

import com.hotel.model.Client;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

public class ClientRequestDTO {
        @NotNull(message = "Имя не может быть пустым")
        private String name;
        @NotNull(message = "Фамилия не может быть пустой")
        private String surname;
        @NotNull(message = "Дата рождения не может быть пустой")
        @Past(message = "Дата рождения не может быть в будущем")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date dateOfBirth;
        @NotNull(message = "Пол не может быть пустым")
        private Client.Gender gender;

    public Client.Gender getGender() {
        return gender;
    }

    public void setGender(Client.Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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






}
