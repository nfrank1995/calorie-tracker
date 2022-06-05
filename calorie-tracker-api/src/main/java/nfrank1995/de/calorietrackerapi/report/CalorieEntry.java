package nfrank1995.de.calorietrackerapi.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalorieEntry {
    int amount;
    Unit unit;
    int kcal;
}
