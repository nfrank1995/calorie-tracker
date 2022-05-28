package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Report {
    @Id
    UUID id;

    LocalDate date;

    List<Meal> meals;
}
