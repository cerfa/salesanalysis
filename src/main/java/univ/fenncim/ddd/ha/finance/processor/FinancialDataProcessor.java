package univ.fenncim.ddd.ha.finance.processor;


import org.springframework.beans.BeanUtils;
import univ.fenncim.ddd.ha.finance.dto.CurrentGoodsSaleStatus;
import univ.fenncim.ddd.ha.finance.dto.ElasticityResult;
import univ.fenncim.ddd.ha.finance.dto.MarketProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class FinancialDataProcessor {
    private FinancialDataProcessor() {
    }

    public static void saleAnalysisProcessing(CurrentGoodsSaleStatus currentSaleStatus) {
        var revenue = computeRevenue(currentSaleStatus);
        currentSaleStatus.setRevenue(revenue);

        if( 0 != revenue.compareTo(BigDecimal.ZERO)) {
            var grossProfit = computeGrossProfit(revenue, currentSaleStatus);
            currentSaleStatus.setGrossProfit(grossProfit);
            currentSaleStatus.setGrossMargin(computeGrossProfitMargin(revenue, grossProfit));
        } else {
            currentSaleStatus.setGrossProfit(BigDecimal.ZERO);
            currentSaleStatus.setGrossMargin(BigDecimal.ZERO);
        }
    }

    public static ElasticityResult elasticityCheckProcessing(List<CurrentGoodsSaleStatus> currentSaleStatuses) {
        CurrentGoodsSaleStatus saleDataAnalyzer1 = new CurrentGoodsSaleStatus();
        CurrentGoodsSaleStatus saleDataAnalyzer2 = new CurrentGoodsSaleStatus();
        BeanUtils.copyProperties(currentSaleStatuses.get(0), saleDataAnalyzer1);
        BeanUtils.copyProperties(currentSaleStatuses.get(1), saleDataAnalyzer2);
        saleAnalysisProcessing(saleDataAnalyzer1);
        saleAnalysisProcessing(saleDataAnalyzer2);
        ElasticityResult elasticityResult = new ElasticityResult();
        elasticityResult.getCurrentGoodsStatuses().add(saleDataAnalyzer1);
        elasticityResult.getCurrentGoodsStatuses().add(saleDataAnalyzer2);
        elasticityResult.setGoodSaleDifference(saleDataAnalyzer2.getGoodsTotalSales() - saleDataAnalyzer1.getGoodsTotalSales());
        elasticityComputation(elasticityResult);
        elasticityResult.setGrossMarginDiff(saleDataAnalyzer2.getGrossMargin().subtract(saleDataAnalyzer1.getGrossMargin()));
        elasticityResult.setRevenueDiff(saleDataAnalyzer2.getRevenue().subtract(saleDataAnalyzer1.getRevenue()));
        elasticityResult.setGrossProfitDiff(saleDataAnalyzer2.getGrossProfit().subtract(saleDataAnalyzer1.getGrossProfit()));
        return elasticityResult;
    }

    private static BigDecimal computeRevenue(CurrentGoodsSaleStatus saleDataAnalyzer) {
        return saleDataAnalyzer.getGoodsUnitPrice().multiply(new BigDecimal(saleDataAnalyzer.getGoodsTotalSales()+""));
    }

    private static BigDecimal computeGrossProfit(BigDecimal revenue, CurrentGoodsSaleStatus saleDataAnalyzer) {
        return revenue.subtract(saleDataAnalyzer.getGoodsUnitCost()
                .multiply(new BigDecimal(saleDataAnalyzer.getGoodsTotalSales()+"")));
    }

    private static BigDecimal computeGrossProfitMargin(BigDecimal revenue, BigDecimal grossProfit) {
        return grossProfit.setScale(2, RoundingMode.HALF_UP)
                .divide(revenue, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Elasticity is verified when given two sales reports with same item unit cost
     * UnitCost, Totalsold1(Q1), UnitPrice1(P1), Revenue1
     * UnitCost, Totalsold2(Q2), UnitPrice2(P2), Revenue2
     *  UnitPrice1 > UnitPrice2
     *  Totalsold2 > Totalsold1
     *  Revenue1 > Revenue2
     *           (( Q2 -  Q1)/Q1) * 100
     *  PED  = _________________________
     *           ((P2 - P1)/P1) * 100
     *
     *           PED >= 1  => Elastic price
     *           PED < 1  =>  Inelastic price
     * */
    private static void elasticityComputation(ElasticityResult elasticityResult) {
        int quantity2 = elasticityResult.getCurrentGoodsStatuses().get(1).getGoodsTotalSales();
        int quantity1 = elasticityResult.getCurrentGoodsStatuses().get(0).getGoodsTotalSales();

        BigDecimal quantityShift = BigDecimal.valueOf(((quantity2 - quantity1)/quantity1));
        BigDecimal priceShift = elasticityResult.getCurrentGoodsStatuses().get(1).getGoodsUnitPrice()
                .subtract(elasticityResult.getCurrentGoodsStatuses().get(0).getGoodsUnitPrice())
                .divide(elasticityResult.getCurrentGoodsStatuses().get(0).getGoodsUnitPrice(), RoundingMode.HALF_UP);

        BigDecimal ped = quantityShift.divide(priceShift, RoundingMode.HALF_UP);

        if (ped.compareTo(BigDecimal.ONE) >= 0) {
            elasticityResult.setMarketProperty(MarketProperty.ELASTIC);
        } else {
            elasticityResult.setMarketProperty(MarketProperty.INELASTIC);
        }
    }
}

