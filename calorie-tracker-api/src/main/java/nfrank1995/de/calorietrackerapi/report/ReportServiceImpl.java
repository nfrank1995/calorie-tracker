package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

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
    public Report getReportForDate(@NonNull LocalDate date){
        Optional<Report> reportOptional = reportRepository.findByDate(date);


        if(reportOptional.isEmpty()){
           Report reportToCreate = new Report();
           reportToCreate.setDate(date);
           return reportRepository.save(reportToCreate);
        }

        return reportOptional.get();
    }

    @Override
    public Report updateReport(@NonNull String id, @NonNull Report report) throws NoSuchElementException{
        Optional<Report> repoResponse = reportRepository.findById(id);

        if(repoResponse.isEmpty()){
            throw new NoSuchElementException("Report with Id " + id + " doesn't exist.");
        }

        Report reportToUpdate = repoResponse.get();

        reportToUpdate.setMeals(report.getMeals());
        reportToUpdate.setWeight(report.getWeight());

        return reportRepository.save(reportToUpdate);
    }
}
