package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.lang.NonNull;

public interface ReportService {

    Report getReportForDate(@NonNull LocalDate date);

    Report updateReport(@NonNull String id, @NonNull Report report) throws NoSuchElementException;
}
