package ru.yandex.practicum.delivery;

import ru.yandex.practicum.delivery.interfaces.Trackable;
import ru.yandex.practicum.delivery.parcel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static final List<Trackable> trackableParcels = new ArrayList<>();

    // коробки для каждого типа
    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(100);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(100);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(100);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    showBox();
                    break;
                case 5:
                    reportAllStatuses();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 - Показать содержимое коробки");
        System.out.println("5 — Обновить статус трекаемых посылок");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Выберите тип посылки:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");

        int type = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите описание: ");
        String description = scanner.nextLine();

        System.out.println("Введите вес: ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите адрес доставки: ");
        String deliveryAddress = scanner.nextLine();

        System.out.println("Введите день отправки: ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1 -> {
                StandardParcel sp = new StandardParcel(description, weight, deliveryAddress, sendDay);
                allParcels.add(sp);
                standardBox.addParcel(sp);
                System.out.println("Стандартная посылка добавлена!");
            }
            case 2 -> {
                FragileParcel fp = new FragileParcel(description, weight, deliveryAddress, sendDay);
                allParcels.add(fp);
                fragileBox.addParcel(fp);
                trackableParcels.add(fp);
                System.out.println("Хрупкая посылка добавлена!");
            }
            case 3 -> {
                System.out.println("Введите срок хранения: ");
                int timeToLive = Integer.parseInt(scanner.nextLine());
                PerishableParcel pp = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
                allParcels.add(pp);
                perishableBox.addParcel(pp);
                System.out.println("Скоропортящаяся посылка добавлена!");
            }
        }
    }

    private static void sendParcels() {
        // Пройти по allParcels, вызвать packageItem() и deliver()
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки!");
            return;
        }
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    // Общая стоимость всех доставок
    private static void calculateCosts() {
        int totalCost = 0;
        for (Parcel parcel : allParcels) {
            totalCost += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость доставки: " + totalCost);
    }

    private static void showBox() {
        System.out.println("Выберите тип коробки для просмотра:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");

        int type = Integer.parseInt(scanner.nextLine());
        switch (type) {
            case 1 -> printBoxContents("Стандартная коробка", standardBox.getAllParcels());
            case 2 -> printBoxContents("Хрупкая коробка", fragileBox.getAllParcels());
            case 3 -> printBoxContents("Скоропортящаяся коробка", perishableBox.getAllParcels());
        }
    }

    private static <T extends Parcel> void printBoxContents(String title, List<T> parcels) {
        System.out.println(title + ":");
        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста!");
        } else {
            for (Parcel parcel : parcels) {
                System.out.println(" - " + parcel.getDescription() + " (" +parcel.getWeight() + " кг)");
            }
        }
    }

    private static void reportAllStatuses() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет посылок с поддержкой трекинга.");
            return;
        }

        System.out.print("Введите новое местоположение: ");
        String newLocation = scanner.nextLine();

        for (Trackable t : trackableParcels) {
            t.reportStatus(newLocation);
        }
    }

}

