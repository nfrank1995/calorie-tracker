package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("reports")
public class ReportController {

    private ReportService reportService;

    ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/createtest")
    Report test(){
        System.out.println("Test");
       return reportService.createTest();
    }

    // @GetMapping("/{date}")
    // Report byDate(@PathVariable LocalDate date){
    //     return reportService.getByDate(date);
    // }

    @PostMapping
    Report createOrUpdate(@RequestBody Report report){
        return reportService.createOrUpdate(report);
    }
    
}
