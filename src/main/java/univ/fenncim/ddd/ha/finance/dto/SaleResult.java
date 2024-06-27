package univ.fenncim.ddd.ha.finance.dto;

import java.math.BigDecimal;

public class SaleResult {
    private BigDecimal revenue;
    private BigDecimal grossProfit;
    private BigDecimal grossMargin;

    public SaleResult() {
        //Sonar
    }

    public BigDecimal getRevenue() {
        return this.revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public BigDecimal getGrossProfit() {
        return this.grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public BigDecimal getGrossMargin() {
        return this.grossMargin;
    }

    public void setGrossMargin(BigDecimal grossMargin) {
        this.grossMargin = grossMargin;
    }
}
