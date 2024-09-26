package com.example.securityhibernate.mapper;

import com.example.securityhibernate.dto.response.RatingFoodDTO;
import com.example.securityhibernate.entity.RatingFood;
import com.example.securityhibernate.repository.FoodRepository;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.utils.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RatingFoodMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    public RatingFoodDTO convertEntityToDTO(RatingFood ratingFood) {
        RatingFoodDTO ratingFoodDTO = new RatingFoodDTO();
        ratingFoodDTO.setId(ratingFood.getId());
        ratingFoodDTO.setStar(ratingFood.getStar());
        ratingFoodDTO.setComment(ratingFood.getComment());
        ratingFoodDTO.setDate(new FormatDate().formatDate(ratingFood.getDate()));

        return ratingFoodDTO;
    }

    public RatingFood convertDTOToEntity(RatingFoodDTO ratingFoodDTO) {
        RatingFood ratingFood = new RatingFood();
        ratingFood.setId(ratingFoodDTO.getId());
        ratingFood.setStar(ratingFoodDTO.getStar());
        ratingFood.setComment(ratingFoodDTO.getComment());
        Date date = new Date();
        long currentDateMilis = date.getTime();
        Date expiredDate = new Date(currentDateMilis);
        ratingFood.setDate(expiredDate);
        ratingFood.setUsers(userRepository.findByUsername(ratingFoodDTO.getUserDTO().getUsername()));
        ratingFood.setFood(foodRepository.findById(ratingFood.getFood().getId()));

        return ratingFood;
    }

}
