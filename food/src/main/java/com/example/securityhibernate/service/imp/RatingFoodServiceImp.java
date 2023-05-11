package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.RatingFoodDTO;
import com.example.securityhibernate.entity.Food;
import com.example.securityhibernate.entity.RatingFood;
import com.example.securityhibernate.repository.FoodRepository;
import com.example.securityhibernate.repository.RatingFoodRepository;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.service.RatingFoodService;
import com.example.securityhibernate.utils.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RatingFoodServiceImp implements RatingFoodService {

    @Autowired
    private RatingFoodRepository ratingFoodRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<RatingFoodDTO> getAllRatingFoodByIdFood(int id) {
        List<RatingFood> list = ratingFoodRepository.findAllByFood_Id(id);
        List<RatingFoodDTO> dtoList = new ArrayList<>();
        if (list.size() > 0) {
            for (RatingFood ratingFood : list) {
                RatingFoodDTO ratingFoodDTO = new RatingFoodDTO();
                ratingFoodDTO.setId(ratingFood.getId());
                ratingFoodDTO.setComment(ratingFood.getComment());
                ratingFoodDTO.setStar(ratingFood.getStar());

                FormatDate formatDate = new FormatDate();
                long millis=System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                ratingFoodDTO.setDate(formatDate.formatDate(date));
                ratingFoodDTO.setFullName(ratingFood.getUsers().getFullname());

                dtoList.add(ratingFoodDTO);
            }
        }

        return dtoList;
    }

    @Override
    public boolean addRatingFood(String username, int idFood, int star, String comment) {
        try {
            RatingFood ratingFood = new RatingFood();
            ratingFood.setFood(foodRepository.findById(idFood));
            ratingFood.setComment(comment);
            ratingFood.setStar(star);

            Date date = new Date();
            long currentDateMilis = date.getTime();
            Date expiredDate = new Date(currentDateMilis);
            ratingFood.setDate(expiredDate);
            ratingFood.setUsers(userRepository.findByUsername(username));

            ratingFoodRepository.save(ratingFood);
            return true;
        } catch (Exception e) {
            System.out.println("Error addRatingFood "+e.getMessage());
            return false;
        }
    }
}
