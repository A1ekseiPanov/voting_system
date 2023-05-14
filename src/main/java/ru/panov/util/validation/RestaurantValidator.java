package ru.panov.util.validation;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.panov.model.Restaurant;
import ru.panov.repository.RestaurantRepository;

@Component
@AllArgsConstructor
public class RestaurantValidator implements Validator {
    public static final String EXCEPTION_DUPLICATE_NAME = "Restaurant with this name already exists";

    private final RestaurantRepository restaurantRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Restaurant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Restaurant restaurant = (Restaurant) target;
        if (restaurantRepository.findByNameIgnoreCase(restaurant.getName()).isPresent()) {
            errors.rejectValue("name", "", EXCEPTION_DUPLICATE_NAME);
        }
    }
}
