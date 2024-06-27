package fenn.kaizen.salesanalysis.IT.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import fenn.kaizen.salesanalysis.fixture.SalesReportData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import univ.fenncim.ddd.ha.finance.FinancialAnalysisApplication;
import univ.fenncim.ddd.ha.finance.dto.CurrentGoodsSaleStatus;
import univ.fenncim.ddd.ha.finance.dto.ElasticityResult;
import univ.fenncim.ddd.ha.finance.service.SalesDataProcessingService;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = FinancialAnalysisApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SalesAnalysisControllersITTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SalesDataProcessingService salesDataProcessingService;
    private MockMvc mockMvc;
    private final ObjectMapper MAPPER = JsonMapper.builder().findAndAddModules().build();
    private final ObjectWriter objectWriter = MAPPER.writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void setup() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void givenSalesData_whenProcessed_ThenResult200() throws Exception {
        var currentGoodsSaleStatus = new CurrentGoodsSaleStatus();
        currentGoodsSaleStatus.setGoodsTotalSales(37);
        currentGoodsSaleStatus.setGoodsUnitCost(new BigDecimal("10.15"));
        currentGoodsSaleStatus.setGoodsUnitPrice(new BigDecimal("17.25"));

        Predicate<CurrentGoodsSaleStatus> salesReportRespPredicate = Objects::nonNull;

        final String requestBodyJson = objectWriter.writeValueAsString(currentGoodsSaleStatus);

        doAnswer(invocation -> null).when(salesDataProcessingService).saleAnalysis(any(CurrentGoodsSaleStatus.class));

        final String processTrxResponse = mockMvc.perform(post("/v1/api/sales/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson))

                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        Assertions.assertThat(processTrxResponse).isNotNull();
        CurrentGoodsSaleStatus  response = MAPPER.readValue(processTrxResponse,CurrentGoodsSaleStatus.class);
        Assertions.assertThat(salesReportRespPredicate).accepts(response);
    }


    @Test
    void givenSalesWithNoData_whenProcessed_ThenResultBadRequest() throws Exception {
        var currentGoodsSaleStatus = new CurrentGoodsSaleStatus();
        currentGoodsSaleStatus.setGoodsTotalSales(40);
        currentGoodsSaleStatus.setGoodsUnitCost(new BigDecimal("20.25"));
        currentGoodsSaleStatus.setGoodsUnitPrice(new BigDecimal("21.75"));

        doAnswer(invocation -> null).when(salesDataProcessingService).saleAnalysis(any(CurrentGoodsSaleStatus.class));
        mockMvc.perform(post("/v1/api/sales/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    void givenSalesDataForElasticity_whenProcessed_ThenResult200() throws Exception {

        var elasticitiesCheckSalesData = SalesReportData.getElasticityComputingData();

        final String requestBodyJson = objectWriter.writeValueAsString(elasticitiesCheckSalesData);

        when(salesDataProcessingService.deepSaleAnalysisElasticityCheck(elasticitiesCheckSalesData)).thenReturn(new ElasticityResult());

        mockMvc.perform(post("/v1/api/sales/elasticity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    void givenNoDataProvided_whenProcessedElasticity_ThenResult400() throws Exception {

        when(salesDataProcessingService.deepSaleAnalysisElasticityCheck(any())).thenReturn(new ElasticityResult());

       MvcResult resultResponse = mockMvc.perform(post("/v1/api/sales/elasticity")
                        .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
                .andReturn();

       Assertions.assertThat(resultResponse.getResponse()).isNotNull();
       Assertions.assertThat(resultResponse.getResponse().getStatus()).isEqualTo(400);
    }


}
