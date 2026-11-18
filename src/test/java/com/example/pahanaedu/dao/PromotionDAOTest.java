package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Promotion;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class PromotionDAOTest {

    @Test
    public void testAddPromotion() {
        PromotionDAO promotionDAO = new PromotionDAO();
        Promotion newPromo = new Promotion();
        newPromo.setPromoCode(TestData.unique("S"));
        newPromo.setDiscountPercentage(25.0);
        newPromo.setActive(true);

        boolean result = promotionDAO.addPromotion(newPromo);

        assertTrue("Promotion should be added successfully.", result);
    }

    @Test
    public void testGetAllPromotions() {
        PromotionDAO promotionDAO = new PromotionDAO();
        List<Promotion> promotions = promotionDAO.getAllPromotions();
        assertNotNull("Promotion list should not be null.", promotions);
        assertFalse("Promotion list should not be empty.", promotions.isEmpty());
    }

    @Test
    public void testGetPromotionById() {
        PromotionDAO promotionDAO = new PromotionDAO();
        // Assuming the sample promo with ID 1 exists
        Promotion promo = promotionDAO.getPromotionById(1);
        assertNotNull("Promotion should not be null for a valid ID.", promo);
        assertEquals("Promo code should be 'SAVE10'.", "SAVE10", promo.getPromoCode());
    }

    @Test
    public void testUpdatePromotion() {
        PromotionDAO promotionDAO = new PromotionDAO();

        // Step 1: Fetch the original promotion
        int promoId = TestData.newPromotion().getPromoId(); // a promotion made for this test
        Promotion promoToUpdate = promotionDAO.getPromotionById(promoId);
        assertNotNull("Setup failed: Could not retrieve the promotion to update.", promoToUpdate);

        // Step 2: Change some details
        promoToUpdate.setDiscountPercentage(15.5); // Use a distinct value
        promoToUpdate.setActive(false);

        // Step 3: Execute the update
        boolean result = promotionDAO.updatePromotion(promoToUpdate);
        assertTrue("DAO method should report that the update was successful.", result);

        // Step 4: Fetch the promotion again to verify the changes
        Promotion updatedPromo = promotionDAO.getPromotionById(promoId);

        // THIS IS THE CRITICAL CHECK
        assertNotNull("Verification failed: getPromotionById returned null after update.", updatedPromo);

        // Step 5: Assert that the data was actually changed
        assertEquals("Discount percentage should be updated in the database.", 15.5, updatedPromo.getDiscountPercentage(), 0.001);
        assertFalse("Active status should be updated in the database.", updatedPromo.isActive());
    }

    @Test
    public void testDeletePromotion() {
        PromotionDAO promotionDAO = new PromotionDAO();
        // Add a temporary promo of our own and delete that one (not the sample promo 'SAVE10')
        int tempId = TestData.newPromotion().getPromoId();

        boolean result = promotionDAO.deletePromotion(tempId);
        assertTrue("Promotion should be deleted successfully.", result);

        Promotion deletedPromo = promotionDAO.getPromotionById(tempId);
        assertNull("Deleted promotion should not be found.", deletedPromo);
    }
}