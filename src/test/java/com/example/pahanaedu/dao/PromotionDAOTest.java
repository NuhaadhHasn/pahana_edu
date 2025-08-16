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
        newPromo.setPromoCode("SUMMER25");
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
        Promotion promoToUpdate = promotionDAO.getPromotionById(1);
        assertNotNull("Setup failed: Could not retrieve promo with ID 1 to update.", promoToUpdate);

        // Step 2: Change some details
        promoToUpdate.setDiscountPercentage(15.5); // Use a distinct value
        promoToUpdate.setActive(false);

        // Step 3: Execute the update
        boolean result = promotionDAO.updatePromotion(promoToUpdate);
        assertTrue("DAO method should report that the update was successful.", result);

        // Step 4: Fetch the promotion again to verify the changes
        Promotion updatedPromo = promotionDAO.getPromotionById(1);

        // THIS IS THE CRITICAL CHECK
        assertNotNull("Verification failed: getPromotionById returned null after update.", updatedPromo);

        // Step 5: Assert that the data was actually changed
        assertEquals("Discount percentage should be updated in the database.", 15.5, updatedPromo.getDiscountPercentage(), 0.001);
        assertFalse("Active status should be updated in the database.", updatedPromo.isActive());
    }

    @Test
    public void testDeletePromotion() {
        PromotionDAO promotionDAO = new PromotionDAO();
        // First add a temporary promo to delete
        Promotion tempPromo = new Promotion();
        tempPromo.setPromoCode("DELETE_ME");
        tempPromo.setDiscountPercentage(50.0);
        tempPromo.setActive(true);
        promotionDAO.addPromotion(tempPromo);

        // A better way to get the ID would be ideal, but for testing we'll assume it's the last one.
        // For this test, we'll delete a known ID, let's say a new one we add with ID 2.
        // To make this test reliable, let's just delete the 'SAVE10' promo with ID 1.
        boolean result = promotionDAO.deletePromotion(1);
        assertTrue("Promotion should be deleted successfully.", result);

        Promotion deletedPromo = promotionDAO.getPromotionById(1);
        assertNull("Deleted promotion should not be found.", deletedPromo);
    }
}