package nfrank1995.de.calorietrackerapi.report;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@EnableAutoConfiguration
@DataMongoTest
public class ReportRepositoryTest {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ReportRepository testRepository;

    @Test
    public void testMongoRepoTest(){
        UUID reportId = UUID.randomUUID();
        LocalDate testDate = LocalDate.of(1995, 11, 23);

        Report testReport = new Report(reportId, testDate, null);

        mongoTemplate.insert(testReport);

        assertTrue(testRepository.findByDate(testDate).isPresent());
    }
    
    @AfterEach
    void cleanUpDatabase() {
        mongoTemplate.getDb().drop();
    }
}
