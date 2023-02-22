package com.company.evgeniy.auto_shop.autos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class CreateAutoDto {

    @NotEmpty(message = "Brand cannot be empty")
    private String brand;

    @NotEmpty(message = "Model cannot be empty")
    private String model;

    @NotEmpty(message = "Color cannot be empty")
    private String color;

    @NotEmpty(message = "FuelType cannot be empty")
    private String fuelType;

    @Min(value = 1, message = "ProductionYear cannot be empty")
    private int productionYear;

    @Min(value = 1, message = "EngineVolume cannot be empty")
    private double engineVolume;

    @NotEmpty(message = "DriveSystem cannot be empty")
    private String driveSystem;

    @NotEmpty(message = "Transmission cannot be empty")
    private String transmission;

    private byte[] photo;

    @Min(value = 1, message = "Price cannot be empty")
    private int price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public String getDriveSystem() {
        return driveSystem;
    }

    public void setDriveSystem(String driveSystem) {
        this.driveSystem = driveSystem;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String toString() {
        return "Brand: " + this.brand + "\n" +
                "Model: " + this.model + "\n" +
                "Color: " + this.color + "\n" +
                "productionYear: " + this.productionYear + "\n" +
                "fuelType: " + this.fuelType + "\n" +
                "engineVolume: " + this.engineVolume + "\n" +
                "driveSystem: " + this.driveSystem + "\n" +
                "transmission: " + this.transmission + "\n" +
                "price: " + this.price;
    }
}
