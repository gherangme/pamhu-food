package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CategoryDTO;
import com.example.securityhibernate.dto.FoodDTO;
import com.example.securityhibernate.dto.RestaurantDTO;
import com.example.securityhibernate.entity.Food;
import com.example.securityhibernate.entity.RatingFood;
import com.example.securityhibernate.entity.Restaurant;
import com.example.securityhibernate.repository.CategoryRepository;
import com.example.securityhibernate.repository.FoodRepository;
import com.example.securityhibernate.repository.RatingFoodRepository;
import com.example.securityhibernate.repository.RestaurantRepository;
import com.example.securityhibernate.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RatingFoodRepository ratingFoodRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<FoodDTO> getAllFoodsByIdCategory(int idCategory) {
        List<Food> list = foodRepository.getAllByIdCategory(idCategory);
        List<FoodDTO> dtoList = getAllFoodsCommon(list, 0, 0);

        return dtoList;
    }

    @Override
    public List<FoodDTO> getAllFoodsPageHome() {
        List<Food> list = foodRepository.getAllPageHome();
        List<FoodDTO> dtoList = getAllFoodsCommon(list, 0, 0);

        return dtoList;
    }

    @Override
    public List<FoodDTO> getAllFoods(int pageNumber) {
        List<Food> list = foodRepository.findAll();
        int totalPage = (int) Math.ceil(list.size() / 12);
        List<Food> listByPageNumber = new ArrayList<>();
        for (int i = (pageNumber - 1) * 12; i < pageNumber * 12; i++) {
            if (list.get(0) != null) {
                listByPageNumber.add(list.get(i));
            }
        }
        List<FoodDTO> dtoList = getAllFoodsCommon(listByPageNumber, pageNumber, totalPage);
        return dtoList;
    }

    // Common All Foods
    private List<FoodDTO> getAllFoodsCommon(List<Food> list, int pageNumber, int totalPage) {
        List<FoodDTO> dtoList = new ArrayList<>();
        for (Food food: list) {
            FoodDTO foodDTO = new FoodDTO();
            foodDTO.setId(food.getId());
            foodDTO.setName(food.getName());
            foodDTO.setImage(food.getImage());
            foodDTO.setPrice(food.getPrice());
            foodDTO.setPageNumber(pageNumber);
            foodDTO.setTotalPage(totalPage);

            // Set cate DTO
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(categoryRepository.findById(food
                    .getCategoryRestaurant().getCategory().getId()).getName());
            foodDTO.setCategoryDTO(categoryDTO);

            // Set res DTO
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            Restaurant restaurant = restaurantRepository.findById(food.getCategoryRestaurant().getRestaurant().getId());
            restaurantDTO.setId(restaurant.getId());
            restaurantDTO.setName(restaurant.getName());
            foodDTO.setRestaurantDTO(restaurantDTO);

            // Set rating food
            List<RatingFood> ratingFoodList = ratingFoodRepository.findAllByFood_Id(food.getId());
            if (ratingFoodList.size() > 0) {
                foodDTO.setRatingNumber(ratingFoodList.size());
                float star = 0;
                for (RatingFood ratingFood : ratingFoodList) {
                    star += ratingFood.getStar();
                }
                foodDTO.setStar(star / foodDTO.getRatingNumber());
            }

            dtoList.add(foodDTO);
        }

        return dtoList;
    }
}
