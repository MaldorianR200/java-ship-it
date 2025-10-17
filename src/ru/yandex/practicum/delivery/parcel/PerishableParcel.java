package ru.yandex.practicum.delivery.parcel;

public class PerishableParcel extends Parcel {
    private static int COST_PERISHABLE_PARCEL = 3;
    private int timeToLive;
    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    public void packageItem() {
        printPackagedMessage();
    }

    @Override
    protected int getBaseCost() {
        return COST_PERISHABLE_PARCEL;
    }

    public boolean isExpired(int currentDay) {
        return sendDay + timeToLive < currentDay;
    }
}
