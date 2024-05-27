package org.example.carrental.model;

import java.time.LocalDate;

public class Leasing_contract {
        private int contract_id;
        private LocalDate start_date;
        private LocalDate end_date;

        private double price;
        private int vehicle_number;
        private String username;
        private int customer_id;



        public Leasing_contract() {
        }

        public Leasing_contract(int contract_id, LocalDate start_date, LocalDate end_date, double price ,int vehicle_number, String username, int customer_id) {
                this.contract_id = contract_id;
                this.start_date = start_date;
                this.end_date = end_date;
                this.price = price;
                this.vehicle_number = vehicle_number;
                this.username = username;
                this.customer_id = customer_id;

        }

        public int getContract_id() {
                return contract_id;
        }

        public void setContract_id(int contract_id) {
                this.contract_id = contract_id;
        }

        public LocalDate getStart_date() {
                return start_date;
        }

        public void setStart_date(LocalDate start_date) {
                this.start_date = start_date;
        }

        public LocalDate getEnd_date() {
                return end_date;
        }

        public void setEnd_date(LocalDate end_date) {
                this.end_date = end_date;
        }

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }

        public int getVehicle_number() {
                return vehicle_number;
        }

        public void setVehicle_number(int vehicle_number) {
                this.vehicle_number = vehicle_number;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public int getCustomer_id() {
                return customer_id;
        }

        public void setCustomer_id(int customer_id) {
                this.customer_id = customer_id;
        }

        @Override
        public String toString() {
                return "Leasing_contract{" +
                        "contract_id=" + contract_id +
                        ", start_date=" + start_date +
                        ", end_date=" + end_date +
                        ", price=" + price +
                        ", vehicle_number=" + vehicle_number +
                        ", username='" + username + '\'' +
                        ", customer_id=" + customer_id +
                        '}';
        }
}