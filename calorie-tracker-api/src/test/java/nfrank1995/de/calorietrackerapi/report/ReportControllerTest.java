package nfrank1995.de.calorietrackerapi.report;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String testDateAsString = "23-11-1995";
        LocalDate testDate = LocalDate.parse(testDateAsString, dateFormatter);
        Report testReport = new Report();
        testReport.setDate(testDate);

        when(reportService.getReportForDate(testDate)).thenReturn(testReport);


        this.mockMvc.perform(get("/reports/{date}",testDateAsString))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date").value("23.11.1995"));

        
        verify(reportService, times(1)).getReportForDate(testDate);
    }


    @Test
    void updateByIDEndpoint_ValidId_ReturnsUpdatedReport() throws Exception {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String testDateAsString = "23-11-1995";
        LocalDate testDate = LocalDate.parse(testDateAsString, dateFormatter);

        UUID id = UUID.randomUUID();

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

        when(reportService.getReportForDate(testDate)).thenReturn(testReport);

        ObjectMapper objectMapper = new ObjectMapper();

        String testReportJsonString = objectMapper.writeValueAsString(testReport);

        MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.put("/reports/{id}",id.toString())
                              .characterEncoding("UTF-8")
                              .content(testReportJsonString);


        this.mockMvc.perform(builder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date").value("23.11.1995"));
    }
}