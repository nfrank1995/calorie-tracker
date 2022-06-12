package nfrank1995.de.calorietrackerapi.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.micrometer.core.instrument.internal.TimedExecutor;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    ReportRepository repository;

    ReportService reportService;

    @BeforeEach
    void setup(){
        reportService = new ReportServiceImpl(repository);
    }


    @Test
    @DisplayName("Test getReportForDate no report with date present")
    void getReportForDate_NoReportWithDate_CreatesNewReport(){
        // arrange
        when(repository.findByDate(any(LocalDate.class))).thenReturn(Optional.empty());
        when(repository.save(any(Report.class))).thenAnswer(i -> i.getArguments()[0]);

        LocalDate date = LocalDate.now();
        Report report = new Report();
        report.setDate(date);

        // act
        Report result = reportService.getReportForDate(date);

        // assert
        verify(repository, times(1)).findByDate(date);
        verify(repository, times(1)).save(any());
        assertEquals(date, result.getDate());
    }

    @Test
    @DisplayName("Test getReportForDate return Report with Date")
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
    void updateReport(){
        // arrange
        Report report = new Report();
        when(repository.save(any(Report.class))).thenAnswer(i -> i.getArguments()[0]);

        // act
        Report result = reportService.updateReport(report);

        // assert
        assertEquals(report, result);
        verify(repository, times(1)).save(report);
    }
}
