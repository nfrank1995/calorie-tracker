package nfrank1995.de.calorietrackerapi.report;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Meal {
    List<CalorieEntry> entries;
}
