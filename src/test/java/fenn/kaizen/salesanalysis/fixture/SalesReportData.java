package fenn.kaizen.salesanalysis.fixture;

import univ.fenncim.ddd.ha.finance.dto.CurrentGoodsSaleStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SalesReportData {

    public static List<CurrentGoodsSaleStatus> getElasticityComputingData() {
        List<CurrentGoodsSaleStatus>  currentGoodsSaleStatuses = new ArrayList<>();

        CurrentGoodsSaleStatus currentGoodsSaleStatus = new CurrentGoodsSaleStatus();
        currentGoodsSaleStatus.setGoodsTotalSales(37);
        currentGoodsSaleStatus.setGoodsUnitCost(new BigDecimal("10.20"));
        currentGoodsSaleStatus.setGoodsUnitPrice(new BigDecimal("17.05"));

        CurrentGoodsSaleStatus currentGoodsSaleStatus1 = new CurrentGoodsSaleStatus();
        currentGoodsSaleStatus1.setGoodsTotalSales(37);
        currentGoodsSaleStatus1.setGoodsUnitCost(new BigDecimal("10.20"));
        currentGoodsSaleStatus1.setGoodsUnitPrice(new BigDecimal("17.30"));

        currentGoodsSaleStatuses.add(currentGoodsSaleStatus);
        currentGoodsSaleStatuses.add(currentGoodsSaleStatus1);

        return currentGoodsSaleStatuses;
    }
}
