package nfrank1995.de.calorietrackerapi.report;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ReportRepository extends MongoRepository<Report, String> {
    
    Optional<Report> findByDate(LocalDate date);

}