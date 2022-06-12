package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, UUID> {
    
    public Optional<Report> findByDate(LocalDate date);

}