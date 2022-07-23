package nfrank1995.de.calorietrackerapi.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.*;

import javax.xml.stream.XMLOutputFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    ReportRepository repository;

    @Captor
    ArgumentCaptor<Report> reportCaptor;

    ReportService reportService;
    
    @BeforeEach
    void setup(){
        reportService = new ReportServiceImpl(repository);
    }

    @Test
    @DisplayName("Test getReportForDate creates report with date if no report with date is present")
    void getReportForDate_NoReportWithDate_CreatesNewReportWithDate() {
        // arrange
        LocalDate date = LocalDate.now();
        Report report = new Report();
        report.setDate(date);

        when(repository.findByDate(date)).thenReturn(Optional.empty());
        when(repository.save(any(Report.class))).thenReturn(report);

        // act
        Report result = reportService.getReportForDate(date);

        // assert
        verify(repository, times(1)).findByDate(date);
        verify(repository, times(1)).save(reportCaptor.capture());

        assertEquals(date, result.getDate());
        assertEquals(date,reportCaptor.getValue().getDate());
    }

    @Test
    @DisplayName("Test getReportForDate return Report with Date is report with date is present")
    void  getReportForDate_ReportWithDateExists_ReturnReport(){
        // arrange
        LocalDate date = LocalDate.now();
        Report report = new Report();
        report.setDate(date);

        when(repository.findByDate(date)).thenReturn(Optional.of(report));

        // act
        Report result = reportService.getReportForDate(date);
        
        // assert
        assertEquals(report, result);
        verify(repository, times(1)).findByDate(date);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Test updateReport Success")
    void updateReport_ReportWithIdExists_ReturnsUpdatedReport(){
        // arrange
        String id = UUID.randomUUID().toString();
        Report reportToUpdate = new Report();
        Report requestedReport = new Report();
        requestedReport.setWeight(69800);
        List<Meal> meals = new ArrayList<>();
        requestedReport.setMeals(meals);

        when(repository.findById(id)).thenReturn(Optional.of(reportToUpdate));
        when(repository.save(reportToUpdate)).thenAnswer(i -> i.getArguments()[0]);

        // act
        Report result = reportService.updateReport(id, requestedReport);

        // assert
        assertEquals(reportToUpdate, result);

        assertEquals(requestedReport.getWeight(), result.getWeight());
        assertEquals(requestedReport.getMeals(), result.getMeals());

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(reportToUpdate);
    }

    @Test
    @DisplayName("Test updateReport throws NoSuchElementException if no report with specified id is present")
    void updateReport_NoReportWithId_ThrowsNoSuchElementException(){
        // arrange
        String id = UUID.randomUUID().toString();
        String expectedErrorMessage = "Report with Id " + id.toString() + " doesn't exist.";
        Report requestedReport = new Report();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // act
        Exception ex = assertThrows(NoSuchElementException.class, () -> {
            reportService.updateReport(id, requestedReport);
        });

        // assert
        assertEquals(ex.getMessage(), expectedErrorMessage);
    }
}
