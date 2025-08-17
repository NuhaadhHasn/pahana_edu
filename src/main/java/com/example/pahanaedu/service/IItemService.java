package com.example.pahanaedu.service;

import com.example.pahanaedu.model.Item;

import java.util.List;

public interface IItemService {
    String addItem(Item item);

    List<Item> getAllItems();

    Item getItemById(int id);

    boolean updateItem(Item item);

    boolean deleteItem(int id);

    List<Item> searchItemsByName(String name);
}