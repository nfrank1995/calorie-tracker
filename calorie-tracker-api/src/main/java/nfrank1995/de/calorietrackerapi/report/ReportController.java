package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }


    @GetMapping("/{date}")
    public ResponseEntity<Report> getReportByDate(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy")LocalDate date){
        Report report = reportService.getReportForDate(date);
        return ResponseEntity.ok(report);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReportWithId(@PathVariable UUID id, @RequestBody Report report){
        return ResponseEntity.ok(report);
    }

}
