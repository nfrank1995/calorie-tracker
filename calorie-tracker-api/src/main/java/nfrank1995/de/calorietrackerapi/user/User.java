package nfrank1995.de.calorietrackerapi.user;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("users")
public class User {
    String id;
    String name;
    String email;
    Map<LocalDate, Integer> calorieGoals;
}
