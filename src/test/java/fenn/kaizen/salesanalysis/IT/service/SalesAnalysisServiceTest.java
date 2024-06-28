package fenn.kaizen.salesanalysis.IT.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import fenn.kaizen.salesanalysis.fixture.SalesReportData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import univ.fenncim.ddd.ha.finance.FinancialAnalysisApplication;
import univ.fenncim.ddd.ha.finance.dto.CurrentGoodsSaleStatus;
import univ.fenncim.ddd.ha.finance.dto.ElasticityResult;
import univ.fenncim.ddd.ha.finance.service.SalesDataProcessingService;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;


@SpringBootTest(classes = FinancialAnalysisApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SalesAnalysisServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SalesDataProcessingService salesDataProcessingService;
    private MockMvc mockMvc;
    private final ObjectMapper MAPPER = JsonMapper.builder().findAndAddModules().build();
    private final ObjectWriter objectWriter = MAPPER.writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void givenSalesData_whenProcessed_ThenResultNotNull() throws Exception {
        var currentGoodsSaleStatus = new CurrentGoodsSaleStatus();
        currentGoodsSaleStatus.setGoodsTotalSales(37);
        currentGoodsSaleStatus.setGoodsUnitCost(new BigDecimal("10"));
        currentGoodsSaleStatus.setGoodsUnitPrice(new BigDecimal("17.05"));

        Predicate<CurrentGoodsSaleStatus> salesReportRespPredicate = Objects::nonNull;
        salesDataProcessingService.saleAnalysis(currentGoodsSaleStatus);

        Assertions.assertThat(salesReportRespPredicate).accepts(currentGoodsSaleStatus);
    }

    @Test
    void givenSalesDataElasticityAnalysis_whenProcessed_ThenResultNotNull() throws Exception {

        Predicate<ElasticityResult> elasticityResultPredicate = Objects::nonNull;
        var elasticityResult = salesDataProcessingService.deepSaleAnalysisElasticityCheck(SalesReportData.getElasticityComputingData());

        Assertions.assertThat(elasticityResultPredicate).accepts(elasticityResult);
    }


}
