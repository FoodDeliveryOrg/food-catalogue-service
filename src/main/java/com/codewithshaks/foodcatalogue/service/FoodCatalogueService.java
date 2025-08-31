package com.codewithshaks.foodcatalogue.service;

import com.codewithshaks.foodcatalogue.dto.FoodCataloguePage;
import com.codewithshaks.foodcatalogue.dto.FoodItemDTO;
import com.codewithshaks.foodcatalogue.dto.Restaurant;
import com.codewithshaks.foodcatalogue.entity.FoodItem;
import com.codewithshaks.foodcatalogue.mapper.FoodItemMapper;
import com.codewithshaks.foodcatalogue.repo.FoodItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCatalogueService {

    private final FoodItemRepo foodItemRepo;

    private final RestTemplate restTemplate;

    public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {
        FoodItem foodITemToBeAdded = FoodItemMapper.INSTANCE.mapFoodItemDTOToFoodItem(foodItemDTO);
        FoodItem savedFoodItem = foodItemRepo.save(foodITemToBeAdded);
        return FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDTO(savedFoodItem);
    }

    public FoodCataloguePage fetchFoodCataloguePageDetails(Integer restaurantId) {
    // food ITem List

        List<FoodItem> foodItemList = fetchFoodItemList(restaurantId);
        Restaurant restaurant = fetchRestaurantDetailsfromRestaurantMS(restaurantId);
        return createFoodCataloguePage(foodItemList,restaurant);
    }

    private Restaurant fetchRestaurantDetailsfromRestaurantMS(Integer restaurantId) {
        return restTemplate.getForObject("http://RESTAURANT-SERVICE/restaurant/fetchRestaurantById/"+restaurantId, Restaurant.class);
    }

    private List<FoodItem> fetchFoodItemList(Integer restaurantId) {
        return foodItemRepo.findByRestaurantId(restaurantId);
    }

    private FoodCataloguePage createFoodCataloguePage(List<FoodItem> foodItemList, Restaurant restaurant) {
        FoodCataloguePage foodCataloguePage = new FoodCataloguePage();
        foodCataloguePage.setFoodItemList(foodItemList);
        foodCataloguePage.setRestaurant(restaurant);
        return foodCataloguePage;
    }
}
