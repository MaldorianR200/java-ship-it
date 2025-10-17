package ru.yandex.practicum.delivery.parcel;

import ru.yandex.practicum.delivery.interfaces.Trackable;

public class FragileParcel extends Parcel implements Trackable {
    private static int COST_FRAGILE_PARCEL = 4;
    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println("Посылка <<" + description + ">> обёрнута в защитную плёнку");
        printPackagedMessage();
    }

    @Override
    protected int getBaseCost() {
        return COST_FRAGILE_PARCEL;
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.println("Хрупкая посылка <<" + description + ">> изменила местоположение на " + newLocation);
    }
}
