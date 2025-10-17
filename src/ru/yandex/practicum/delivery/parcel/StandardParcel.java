package ru.yandex.practicum.delivery.parcel;

public class StandardParcel extends Parcel {
    private static int COST_STANDARD_PARCEL = 2;

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        printPackagedMessage();
    }

    @Override
    protected int getBaseCost() {
        return COST_STANDARD_PARCEL;
    }

}
