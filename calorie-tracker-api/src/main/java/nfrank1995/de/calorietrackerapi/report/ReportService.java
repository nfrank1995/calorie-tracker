package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    public Report createOrUpdate(Report report){
        return this.reportRepository.save(report);
    }

    public Report getByDate(LocalDate date){
        return this.reportRepository.findByDate(date);
    }

    @Transactional
    public Report createTest() {
        CalorieEntry e1 = new CalorieEntry();
        e1.amount = 100;
        e1.unit = Unit.GRAM;
        e1.kcal = 123;

        CalorieEntry e2 = new CalorieEntry();
        e2.amount = 6;
        e2.unit = Unit.PIECE;
        e2.kcal = 96;

        Meal meal  = new Meal();
        List<CalorieEntry> entries = new ArrayList<>();
        entries.add(e1);
        entries.add(e2);

        meal.entries = entries;

        Report report = new Report();
        report.date = LocalDate.now();
        report.id = UUID.randomUUID();
        List<Meal> meals = new ArrayList<>();
        meals.add(meal);
        report.meals = meals;

        System.out.println(report);

        Report savedReport= reportRepository.save(report);

        return savedReport;
    }
}
