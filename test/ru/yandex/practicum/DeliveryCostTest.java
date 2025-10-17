package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.interfaces.Trackable;
import ru.yandex.practicum.delivery.parcel.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryCostTest {

    private static StandardParcel standardParcel;
    private static FragileParcel fragileParcel;
    private static PerishableParcel perishableParcel;

    @BeforeAll
    static void beforeAll() {
        standardParcel = new StandardParcel("Одежда", 5, "Москва", 10);
        fragileParcel = new FragileParcel("Ноутбук", 3, "Санкт-Петербург", 12);
        perishableParcel = new PerishableParcel("Фрукты", 4, "Нижний Новгород", 5, 3);
    }

    // --- ТЕСТ 1: calculateDeliveryCost() для разных типов посылок ---

    @Test
    void testStandardParcelCost() {
        double cost = standardParcel.calculateDeliveryCost();
        assertEquals(10, cost, "Стоимость стандартной посылки должна быть вес * 2");
    }

    @Test
    void testFragileParcelCost() {
        double cost = fragileParcel.calculateDeliveryCost();
        assertEquals(12, cost, "Стоимость хрупкой посылки должна быть вес * 4");
    }

    @Test
    void testPerishableParcelCost() {
        double cost = perishableParcel.calculateDeliveryCost();
        assertEquals(12, cost, "Стоимость скоропортящейся посылки должна быть вес * 3");
    }

    @Test
    void testZeroWeightParcelCostBoundary() {
        StandardParcel empty = new StandardParcel("Пустая", 0, "Тверь", 1);
        assertEquals(0, empty.calculateDeliveryCost(), "Посылка с весом 0 должна иметь стоимость 0");
    }

    // --- ТЕСТ 2: isExpired() для PerishableParcel ---

    @Test
    void testPerishableParcelNotExpired() {
        // sendDay = 5, timeToLive = 3 → не испортилась до 7 числа
        assertFalse(perishableParcel.isExpired(7), "Посылка не должна быть испорчена, если currentDay <= sendDay + timeToLive");
    }

    @Test
    void testPerishableParcelExpired() {
        // currentDay = 10 → 5 + 3 < 10 → испорчена
        assertTrue(perishableParcel.isExpired(10), "Посылка должна быть испорчена, если currentDay > sendDay + timeToLive");
    }

    @Test
    void testPerishableParcelBoundary() {
        // currentDay = 8 → ровно на границе срока
        assertFalse(perishableParcel.isExpired(8), "Посылка не должна быть испорчена, если currentDay == sendDay + timeToLive");
    }

    // --- ТЕСТ 3: ParcelBox.addParcel() ---

    @Test
    void testAddParcelWithinLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        box.addParcel(standardParcel);
        List<StandardParcel> all = box.getAllParcels();
        assertEquals(9, all.size(), "Посылка должна добавляться, если вес в пределах лимита");
    }

    @Test
    void testAddParcelExceedingLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(4); // лимит 4 кг
        box.addParcel(standardParcel); // вес 5 кг
        List<StandardParcel> all = box.getAllParcels();
        assertTrue(all.isEmpty(), "Посылка не должна добавляться, если превышен лимит веса");
    }

    @Test
    void testAddParcelAtExactLimitBoundary() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5); // лимит = вес
        box.addParcel(standardParcel);
        List<StandardParcel> all = box.getAllParcels();
        assertEquals(1, all.size(), "Посылка должна добавляться, если вес равен лимиту");
    }
}
