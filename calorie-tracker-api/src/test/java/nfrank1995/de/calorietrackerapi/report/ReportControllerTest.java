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

import nfrank1995.de.calorietrackerapi.food.Category;
import nfrank1995.de.calorietrackerapi.food.Food;
import nfrank1995.de.calorietrackerapi.food.Unit;
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
        String userId = UUID.randomUUID().toString();

        Food f1 =  new Food();
        f1.setName("bread");
        f1 .setAmount(3);
        f1 .setKcal(102);
        f1.setUnit(Unit.PIECE);
        f1.setCategory(Category.CARBOHYDRATE);
        Food f2 =  new Food();
        f2.setName("beans");
        f2.setAmount(500);
        f2.setKcal(28);
        f2.setUnit(Unit.GRAM);
        f2.setCategory(Category.VEGETABLE);

        Meal meal = new Meal();
        meal.foods = new ArrayList<>();
        meal.foods.add(f1);
        meal.foods.add(f2);

        List<Meal> meals = new ArrayList<>();
        meals.add(meal);

        Report testReport = new Report();
        testReport.setDate(testDate);
        testReport.setId(id);
        testReport.setUserId(userId);
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
        String requestedId = UUID.randomUUID().toString();
        String reportId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String errorMessage = "404";
        NoSuchElementException exceptionToThrow = new NoSuchElementException(errorMessage);

        Report testReport = new Report();
        testReport.setId(reportId);
        testReport.setUserId(userId);

        when(reportService.updateReport(eq(requestedId), any(Report.class))).thenThrow(exceptionToThrow);

        ObjectMapper objectMapper = new ObjectMapper();

        String testReportJsonString = objectMapper.writeValueAsString(testReport);

        MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.put("/reports/{id}",requestedId)
                            .characterEncoding("UTF-8")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(testReportJsonString);


        this.mockMvc.perform(builder)
        .andDo(print())
        .andExpect(status().isNotFound());

        verify(reportService, times(1)).updateReport(eq(requestedId), any(Report.class));
    }
}