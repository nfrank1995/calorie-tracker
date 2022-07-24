package nfrank1995.de.calorietrackerapi.report;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nfrank1995.de.calorietrackerapi.food.Food;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Meal {
    List<Food> foods;
}
