package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportServiceImpl implements ReportService{

    private ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    @Override
    @Transactional
    public Report getReportForDate(@NonNull LocalDate date) {
        

        Optional<Report> reportOptional = reportRepository.findByDate(date);

        if(reportOptional.isPresent()){
            return reportOptional.get();
        }

        Report reportToSave = new Report();
        reportToSave.setDate(date);
        
        return reportRepository.save(reportToSave);
    }

    @Override
    public Report updateReport(@NonNull Report report) {
        return reportRepository.save(report);
    }
}
