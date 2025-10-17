package ru.yandex.practicum.delivery.parcel;

import java.util.ArrayList;

public class ParcelBox<T extends Parcel> {
    private final ArrayList<T> parcels;
    private int maxWeight;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
        parcels = new ArrayList<>();
    }

    public void addParcel(T parcel) {
        int currentWeight = getCurrentWeight();
        if (currentWeight + parcel.weight > maxWeight) {
            System.out.println("Превышен максимальный вес!!!");
        } else {
            parcels.add(parcel);
        }
    }

    private int getCurrentWeight() {
        int currentWeight = 0;
        for (Parcel parcel : parcels) {
            currentWeight += parcel.weight;
        }
        return currentWeight;
    }

    public ArrayList<T> getAllParcels() {
        return parcels;
    }


}
