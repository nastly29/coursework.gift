package service;

import domain.Gift;
import domain.Package;
import domain.sweets.Candy;
import domain.sweets.Sweets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GiftServiceTest {

    private GiftService giftService;

    @BeforeEach
    void setUp() {
        giftService = new GiftService(new Gift());
    }

    @Test
    void testCreateOrUpdateGift() {
        Package pack = new Package("Red", "Gold", "Happy Birthday!");
        giftService.createOrUpdateGift("My Gift", pack);

        assertTrue(giftService.exists());
        assertEquals("My Gift", giftService.getGift().getName());
        assertEquals(pack, giftService.getGift().getPack());
    }

    @Test
    void testAddAndListSweets() {
        Candy sweet = new Candy(1, "Caramel", 50, 20, 10, "Vanilla", "Soft", null);
        giftService.createOrUpdateGift("Gift", new Package("Blue", "Silver", "Мої вітання"));
        giftService.addSweetToGift(sweet);

        List<Sweets> sweets = giftService.listSweets();
        assertEquals(1, sweets.size());
        assertEquals("Caramel", sweets.get(0).getName());
    }

    @Test
    void testRemoveSweet() {
        Candy sweet = new Candy(2, "Caramel", 50, 20, 10, "Vanilla", "Soft", null);
        giftService.createOrUpdateGift("Gift", new Package("Blue", "Silver", "Мої вітання"));
        giftService.addSweetToGift(sweet);
        giftService.removeSweet(sweet);

        assertTrue(giftService.listSweets().isEmpty());
    }

    @Test
    void testTotalWeightAndPrice() {
        giftService.createOrUpdateGift("Gift", new Package("Blue", "Silver", "Мої вітання"));
        giftService.addSweetToGift(new Candy(3, "C1", 50, 10, 15, "F", "T", null));
        giftService.addSweetToGift(new Candy(4, "C2", 70, 20, 25, "F", "T", null));

        assertEquals(40.0, giftService.totalPrice(), 0.01);
        assertEquals(120.0, giftService.totalWeight(), 0.01);
    }

    @Test
    void testGetQuantity() {
        Candy sweet = new Candy(5, "C", 50, 10, 15, "F", "T", null);
        giftService.createOrUpdateGift("Gift", new Package("Blue", "Silver", "Мої вітання"));
        giftService.addSweetToGift(sweet);
        giftService.addSweetToGift(sweet);

        assertEquals(2, giftService.getQuantity(sweet));
    }

    @Test
    void testDecreaseSweet() {
        Candy sweet = new Candy(6, "C", 50, 10, 15, "F", "T", null);
        giftService.createOrUpdateGift("Gift", new Package("Blue", "Silver", "Мої вітання"));
        giftService.addSweetToGift(sweet);
        giftService.addSweetToGift(sweet);

        giftService.decreaseSweet(sweet);
        assertEquals(1, giftService.getQuantity(sweet));
    }

    @Test
    void testFindBySugar() {
        giftService.createOrUpdateGift("Gift", new Package("Blue", "Silver", "Мої вітання"));
        giftService.addSweetToGift(new Candy(7, "C1", 50, 10, 15, "F", "T", null));
        giftService.addSweetToGift(new Candy(8, "C2", 50, 40, 15, "F", "T", null));

        List<Sweets> result = giftService.findBySugar(5, 30);
        assertEquals(1, result.size());
        assertEquals("C1", result.get(0).getName());
    }

    @Test
    void testGetSweetsSortedByWeight() {
        Candy c1 = new Candy(9, "C1", 20, 10, 15, "F", "T", null);
        Candy c2 = new Candy(10, "C2", 40, 10, 15, "F", "T", null);
        giftService.createOrUpdateGift("Gift", new Package("Blue", "Silver", "Мої вітання"));
        giftService.addSweetToGift(c2);
        giftService.addSweetToGift(c1);

        List<Sweets> sorted = giftService.getSweetsSortedByWeight(true);
        assertEquals("C1", sorted.get(0).getName());

        sorted = giftService.getSweetsSortedByWeight(false);
        assertEquals("C2", sorted.get(0).getName());
    }

    @Test
    void testResetGift() {
        Package pack = new Package("Red", "Gold", "Text");
        giftService.createOrUpdateGift("ResetGift", pack);
        giftService.addSweetToGift(new Candy(11, "ResetSweet", 30, 15, 10, "F", "T", null));
        giftService.reset();
        assertFalse(giftService.exists());
        assertThrows(IllegalStateException.class, () -> giftService.getGift());
    }


    @Test
    void testExistsBehavior() {
        assertFalse(giftService.exists());
        giftService.createOrUpdateGift("G", new Package("a", "b", "c"));
        assertTrue(giftService.exists());
    }

    @Test
    void testGetGiftThrowsIfNotExists() {
        giftService.reset();
        assertThrows(IllegalStateException.class, () -> giftService.getGift());
    }

    @Test
    void testGetGiftWithoutCreation() {
        GiftService emptyGiftService = new GiftService(new Gift());
        assertThrows(IllegalStateException.class, emptyGiftService::getGift);
    }

    @Test
    void testRemoveNonExistingSweet() {
        giftService.createOrUpdateGift("TestGift", new Package("Red", "Blue", "Test"));
        Candy sweet = new Candy(12, "MissingSweet", 30, 15, 10, "F", "T", null);
        giftService.removeSweet(sweet);
        assertEquals(0, giftService.listSweets().size());
    }
}