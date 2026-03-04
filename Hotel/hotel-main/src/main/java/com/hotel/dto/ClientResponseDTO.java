    package com.hotel.dto;

    import java.util.Objects;

    public class ClientResponseDTO {
        private Long id;
        private String name;
        private String surname;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClientResponseDTO that = (ClientResponseDTO) o;
            return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, surname);
        }
    }
