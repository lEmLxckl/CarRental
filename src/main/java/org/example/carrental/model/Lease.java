package org.example.carrental.model;

import java.time.LocalDate;

public class Lease {
        private int leaseID;
        private LeaseType typeOfLease;
        private LocalDate startDate;
        private LocalDate endDate;
        private int customerID;
        private int vehicleID;
        private boolean status;
        private double price;

        public double getPrice() {
                return price;
        }

        public void setPrice(int price) {
                this.price = price;
        }

        public int getLeaseID() {
                return leaseID;
        }

        public void setLeaseID(int leaseID) {
                this.leaseID = leaseID;
        }

        public LeaseType getTypeOfLease() {
                return typeOfLease;
        }

        public void setTypeOfLease(LeaseType typeOfLease) {
                this.typeOfLease = typeOfLease;
        }

        public LocalDate getStartDate() {
                return startDate;
        }

        public void setStartDate(LocalDate startDate) {
                this.startDate = startDate;
        }

        public LocalDate getEndDate() {
                return endDate;
        }

        public void setEndDate(LocalDate endDate) {
                this.endDate = endDate;
        }

        public int getCustomerID() {
                return customerID;
        }

        public void setCustomerID(int customerID) {
                this.customerID = customerID;
        }

        public int getVehicleID() {
                return vehicleID;
        }

        public void setVehicleID(int vehicleID) {
                this.vehicleID = vehicleID;
        }

        public boolean isStatus() {
                return status;
        }

        public void setStatus(boolean status) {
                this.status = status;
        }

    }
