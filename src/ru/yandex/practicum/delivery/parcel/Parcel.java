package ru.yandex.practicum.delivery.parcel;

public abstract class Parcel {
    protected String description;
    protected int weight;
    protected String deliveryAddress;
    protected int sendDay;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    public String getDescription() {
        return description;
    }
    public int getWeight() {
        return weight;
    }

    protected void printPackagedMessage() {
        System.out.println("Посылка <<" + description + ">> упакована");
    }

    public abstract void packageItem();


    public void deliver() {
        System.out.println("Посылка <<" + description + ">> доставлена по адресу " + deliveryAddress);
    }

    public int calculateDeliveryCost() {
        return weight * getBaseCost();
    }
    // В каждом типе указываем базовую стоимость
    protected abstract int getBaseCost();

}
