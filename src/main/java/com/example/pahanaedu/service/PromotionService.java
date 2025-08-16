package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.PromotionDAO;
import com.example.pahanaedu.model.Promotion;

import java.util.List;

public class PromotionService {

    private final PromotionDAO promotionDAO;

    public PromotionService() {
        this.promotionDAO = new PromotionDAO();
    }

    /**
     * Handles the business logic for adding a new promotion.
     *
     * @param promotion The Promotion to add.
     * @return true if added successfully, false otherwise.
     */
    public boolean addPromotion(Promotion promotion) {
        // Business logic could be added here.
        // For example, validate that the discount percentage is between 0 and 100.
        if (promotion.getDiscountPercentage() < 0 || promotion.getDiscountPercentage() > 100) {
            return false;
        }
        return promotionDAO.addPromotion(promotion);
    }

    /**
     * Handles the business logic for retrieving all promotions.
     *
     * @return A List of all Promotion objects.
     */
    public List<Promotion> getAllPromotions() {
        // Business logic could be added here, like filtering for only active promotions.
        return promotionDAO.getAllPromotions();
    }

    /**
     * Handles the business logic for retrieving a single promotion by its ID.
     *
     * @param id The ID of the promotion to retrieve.
     * @return The Promotion object if found, otherwise null.
     */
    public Promotion getPromotionById(int id) {
        // Business logic could be added here.
        return promotionDAO.getPromotionById(id);
    }

    /**
     * Handles the business logic for updating a promotion.
     *
     * @param promotion The Promotion with updated details.
     * @return true if updated successfully, false otherwise.
     */
    public boolean updatePromotion(Promotion promotion) {
        // We can reuse the same validation from the add method.
        if (promotion.getDiscountPercentage() < 0 || promotion.getDiscountPercentage() > 100) {
            return false;
        }
        return promotionDAO.updatePromotion(promotion);
    }

    /**
     * Handles the business logic for deleting a promotion.
     *
     * @param id The ID of the promotion to delete.
     * @return true if deleted successfully, false otherwise.
     */
    public boolean deletePromotion(int id) {
        // Business logic could be added here, like checking if a promotion is
        // currently attached to any unpaid bills before allowing deletion.
        return promotionDAO.deletePromotion(id);
    }
}