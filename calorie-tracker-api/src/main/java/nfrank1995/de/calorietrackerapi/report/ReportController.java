package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{date}")
    public ResponseEntity<Report> getReportByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Report report = reportService.getReportForDate(date);
        return ResponseEntity.ok(report);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReportWithId(@PathVariable String id, @RequestBody Report report) {
        try{
            Report updatedReport = reportService.updateReport(id, report);
            return ResponseEntity.ok(updatedReport);
        }catch(NoSuchElementException nSEE){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}