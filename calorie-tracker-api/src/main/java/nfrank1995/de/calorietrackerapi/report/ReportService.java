package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

public interface ReportService {
    Report getReportForDate(@NonNull LocalDate date);

    Report updateReport(@NonNull Report report);
}
