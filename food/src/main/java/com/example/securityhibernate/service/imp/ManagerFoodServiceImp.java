package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CategoryDTO;
import com.example.securityhibernate.dto.FoodDTO;
import com.example.securityhibernate.dto.ManagerFoodDTO;
import com.example.securityhibernate.entity.Category;
import com.example.securityhibernate.entity.CategoryRestaurant;
import com.example.securityhibernate.entity.Food;
import com.example.securityhibernate.entity.Restaurant;
import com.example.securityhibernate.listenum.FolderType;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.FileStorageService;
import com.example.securityhibernate.service.ManagerFoodService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerFoodServiceImp implements ManagerFoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryRestaurantRepository categoryRestaurantRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ManagerFoodDTO getFoodAdd(int idUser) {
        ManagerFoodDTO managerFoodDTO = new ManagerFoodDTO();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<CategoryRestaurant> list = categoryRestaurantRepository
                .findAllByRestaurant_Id(restaurantRepository.findByUsers_Id(idUser).getId());

        // Set List Cate Of Res
        for (CategoryRestaurant categoryRestaurant: list) {
            Category category = categoryRepository.findById(categoryRestaurant.getCategory().getId());

            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTOList.add(categoryDTO);
        }
        managerFoodDTO.setCategoryDTOList(categoryDTOList);

        return managerFoodDTO;
    }

    @Override
    public ManagerFoodDTO getFoodDetailById(int id, int idUser) {
        Food food = foodRepository.findById(id);
        ManagerFoodDTO managerFoodDTO = new ManagerFoodDTO();
        managerFoodDTO.setId(food.getId());
        managerFoodDTO.setName(food.getName());
        managerFoodDTO.setPrice(food.getPrice());
        managerFoodDTO.setImage(food.getImage());

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<CategoryRestaurant> list = categoryRestaurantRepository
                .findAllByRestaurant_Id(restaurantRepository.findByUsers_Id(idUser).getId());
        for (CategoryRestaurant categoryRestaurant: list) {
            Category category = categoryRepository.findById(categoryRestaurant.getCategory().getId());

            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTOList.add(categoryDTO);

            // Set Cate cho ManagerFood
            if (food.getCategoryRestaurant().getCategory().getId() == category.getId()) {
                managerFoodDTO.setCategoryDTO(categoryDTO);
            }
        }
        managerFoodDTO.setCategoryDTOList(categoryDTOList);

        return managerFoodDTO;
    }

    @Override
    public boolean deleteFoodById(int id) {
        try {
            fileStorageService.removeFile(foodRepository.findById(id).getImage(), FolderType.food);
            foodRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            System.out.println("Error deleteFoodById "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateFood(int id, String name, int idCate, double price, MultipartFile file, String token) {
        try {
            Food food = foodRepository.findById(id);
            food.setName(name);
            food.setPrice(price);
            if (file != null) {

                // Delete Old Image
                fileStorageService.removeFile(food.getImage(), FolderType.food);

                // Save New Image
                food.setImage(file.getOriginalFilename());
                fileStorageService.saveFiles(file, file.getOriginalFilename(), FolderType.food);
            }

            // Find id Res
            Restaurant restaurant = restaurantRepository.findByUsers_Id(jwtUtilsHelpers.getIdUserByToken(token));
            // Find id CateRes by id Cate, id Res
            CategoryRestaurant categoryRestaurant = categoryRestaurantRepository
                    .findByRestaurant_IdAndCategory_Id(restaurant.getId(), idCate);

            // Save Food
            food.setCategoryRestaurant(categoryRestaurant);
            foodRepository.save(food);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addFood(String name, int idCate, double price, MultipartFile file, String token) {
        try {
            CategoryRestaurant categoryRestaurant = categoryRestaurantRepository
                    .findByRestaurant_IdAndCategory_Id(restaurantRepository.findByUsers_Id(userRepository
                            .findByUsername(jwtUtilsHelpers.getUsernameByToken(token)).getId()).getId(), idCate);

            Food food = new Food();
            food.setName(name);
            food.setPrice(price);
            food.setImage(file.getOriginalFilename());
            food.setCategoryRestaurant(categoryRestaurant);

            fileStorageService.saveFiles(file, file.getOriginalFilename(), FolderType.food);
            foodRepository.save(food);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
