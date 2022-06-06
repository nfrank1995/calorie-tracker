package nfrank1995.de.calorietrackerapi.report;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("reports")
public class ReportController {

    private ReportService reportService;

    ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping(value="/createtest", produces = "application/json")
    public Report test(){
        Report savedReport = reportService.createTest();
        System.out.println(savedReport);
       return savedReport;
    }

    
}
