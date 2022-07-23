package nfrank1995.de.calorietrackerapi.report;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;


// Use WebMvcTest with specific controller to initialize only the controller
@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @MockBean
    ReportService reportService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getByDateEndpoint_ValidDate_ReturnsReportFromReportService() throws Exception {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String testDateAsString = "1995-11-23";
        LocalDate testDate = LocalDate.parse(testDateAsString, dateFormatter);
        Report testReport = new Report();
        testReport.setDate(testDate);

        when(reportService.getReportForDate(testDate)).thenReturn(testReport);


        this.mockMvc.perform(get("/reports/{date}",testDateAsString))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date").value(testDateAsString));

        
        verify(reportService, times(1)).getReportForDate(testDate);
    }


    @Test
    void updateByIDEndpoint_ValidId_ReturnsUpdatedReport() throws Exception {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String testDateAsString = "1995-11-23";
        LocalDate testDate = LocalDate.parse(testDateAsString, dateFormatter);

        String id = UUID.randomUUID().toString();

        CalorieEntry cE1 =  new CalorieEntry();
        cE1.setAmount(3);
        cE1.setKcal(102);
        cE1.setUnit(Unit.PIECE);
        CalorieEntry cE2 =  new CalorieEntry();
        cE2.setAmount(500);
        cE2.setKcal(28);
        cE2.setUnit(Unit.GRAM);

        Meal meal = new Meal();
        meal.entries = new ArrayList<>();
        meal.entries.add(cE1);
        meal.entries.add(cE2);

        List<Meal> meals = new ArrayList<>();
        meals.add(meal);

        Report testReport = new Report();
        testReport.setDate(testDate);
        testReport.setId(id);
        testReport.setMeals(meals);


        UUID resultId = UUID.randomUUID();
        Report reportToReturn = new Report();
        reportToReturn.setId(resultId.toString());

        when(reportService.updateReport(eq(id), any(Report.class))).thenReturn(reportToReturn);

        ObjectMapper objectMapper = new ObjectMapper();

        String testReportJsonString = objectMapper.writeValueAsString(testReport);

        MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.put("/reports/{id}",id.toString())
                            .characterEncoding("UTF-8")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(testReportJsonString);


        this.mockMvc.perform(builder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(resultId.toString()));

        verify(reportService, times(1)).updateReport(eq(id), any(Report.class));
    }

    @Test
    void updateByIDEndpoint_NoReportWithId_Returns404() throws Exception {
        String id = UUID.randomUUID().toString();
        String errorMessage = "404";
        NoSuchElementException exceptionToThrow = new NoSuchElementException(errorMessage);

        Report testReport = new Report();

        when(reportService.updateReport(eq(id), any(Report.class))).thenThrow(exceptionToThrow);

        ObjectMapper objectMapper = new ObjectMapper();

        String testReportJsonString = objectMapper.writeValueAsString(testReport);

        MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.put("/reports/{id}",id)
                            .characterEncoding("UTF-8")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(testReportJsonString);


        this.mockMvc.perform(builder)
        .andDo(print())
        .andExpect(status().isNotFound());

        verify(reportService, times(1)).updateReport(eq(id), any(Report.class));
    }
}