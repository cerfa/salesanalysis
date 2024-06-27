package fenn.kaizen.salesanalysis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import univ.fenncim.ddd.ha.finance.dto.CurrentGoodsSaleStatus;
import univ.fenncim.ddd.ha.finance.dto.ElasticityResult;
import univ.fenncim.ddd.ha.finance.dto.MarketProperty;
import univ.fenncim.ddd.ha.finance.processor.FinancialDataProcessor;

import java.math.BigDecimal;
import java.util.List;

class SalesReportTest {

    @Test
    void givenSalesReport_whenNoItemSold_ThenRevenueAndProfitAreZero () {
        var salesReport = new CurrentGoodsSaleStatus();
        salesReport.setGoodsUnitCost(new BigDecimal("25.25"));
        salesReport.setGoodsTotalSales(0);
        salesReport.setGoodsUnitPrice(new BigDecimal("25"));

        FinancialDataProcessor.saleAnalysisProcessing(salesReport);

        Assertions.assertEquals(BigDecimal.ZERO, salesReport.getRevenue());
        Assertions.assertEquals(BigDecimal.ZERO, salesReport.getGrossProfit());
    }


    @Test
    void givenSalesReport_whenSomeItemSold_ThenRevenueAndMarginAreNotZero () {
        var salesReport = new CurrentGoodsSaleStatus();
        salesReport.setGoodsUnitCost(new BigDecimal("25.10"));
        salesReport.setGoodsTotalSales(16);
        salesReport.setGoodsUnitPrice(new BigDecimal("25"));

        FinancialDataProcessor.saleAnalysisProcessing(salesReport);

        Assertions.assertNotEquals(BigDecimal.ZERO, salesReport.getRevenue());
        Assertions.assertNotEquals(BigDecimal.ZERO, salesReport.getRevenue());
    }


    @Test
    void givenSalesReport_whenSomeItemSoldAtLowerPrice_ThenMarginAreNegative() {
        var salesReport = new CurrentGoodsSaleStatus();
        salesReport.setGoodsUnitCost(new BigDecimal("20.25"));
        salesReport.setGoodsTotalSales(16);
        salesReport.setGoodsUnitPrice(new BigDecimal("18.10"));

        FinancialDataProcessor.saleAnalysisProcessing(salesReport);

        Assertions.assertTrue(salesReport.getGrossMargin().compareTo(BigDecimal.ZERO) < 0);
        Assertions.assertTrue(salesReport.getGrossProfit().compareTo(BigDecimal.ZERO) < 0);
    }



    @Test
    void givenSalesReports_whenComputingElasticity_ThenInelasticityIsAchieved() {
        var salesReport = new CurrentGoodsSaleStatus();
        salesReport.setGoodsUnitCost(new BigDecimal("20.25"));
        salesReport.setGoodsTotalSales(16);
        salesReport.setGoodsUnitPrice(new BigDecimal("25.00"));


        var salesReport2 = new CurrentGoodsSaleStatus();
        salesReport2.setGoodsUnitCost(new BigDecimal("20.25"));
        salesReport2.setGoodsTotalSales(20);
        salesReport2.setGoodsUnitPrice(new BigDecimal("23.10"));


        ElasticityResult elasticityCheck = FinancialDataProcessor.elasticityCheckProcessing(List.of(salesReport, salesReport2));

        Assertions.assertTrue(elasticityCheck.getGoodSaleDifference() > 0);
        Assertions.assertEquals(MarketProperty.INELASTIC, elasticityCheck.getMarketProperty());
    }

    @Test
    void givenSalesReports_whenDifferenceLightInPriceAndLessSold_ThenInelasticIsAchieved() {
        var salesReport = new CurrentGoodsSaleStatus();
        salesReport.setGoodsUnitCost(new BigDecimal("20.25"));
        salesReport.setGoodsTotalSales(16);
        salesReport.setGoodsUnitPrice(new BigDecimal("25.99"));


        var salesReport2 = new CurrentGoodsSaleStatus();
        salesReport2.setGoodsUnitCost(new BigDecimal("20.25"));
        salesReport2.setGoodsTotalSales(16);
        salesReport2.setGoodsUnitPrice(new BigDecimal("19.15"));


        ElasticityResult elasticityCheck = FinancialDataProcessor.elasticityCheckProcessing(List.of(salesReport, salesReport2));

        Assertions.assertTrue(elasticityCheck.getGoodSaleDifference() <= 0);
        Assertions.assertEquals(MarketProperty.INELASTIC, elasticityCheck.getMarketProperty());
    }
}
