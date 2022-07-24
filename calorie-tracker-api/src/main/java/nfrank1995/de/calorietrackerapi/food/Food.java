package nfrank1995.de.calorietrackerapi.food;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    String name;
    int kcal;
    Unit unit;
    int amount;
    Category category;
}
