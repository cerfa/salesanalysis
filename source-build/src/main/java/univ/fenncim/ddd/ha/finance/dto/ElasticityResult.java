//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package univ.fenncim.ddd.ha.finance.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ElasticityResult {
    private List<CurrentGoodsSaleStatus> currentGoodsStatuses;
    private MarketProperty marketProperty;
    private int goodSaleDifference;
    private BigDecimal revenueDiff;
    private BigDecimal grossProfitDiff;
    private BigDecimal grossMarginDiff;

    public ElasticityResult() {
        this.currentGoodsStatuses = new ArrayList<>();
        //Sonar
    }

    public List<CurrentGoodsSaleStatus> getCurrentGoodsStatuses() {
        return this.currentGoodsStatuses;
    }

    public void setCurrentGoodsStatuses(List<CurrentGoodsSaleStatus> currentGoodsStatuses) {
        this.currentGoodsStatuses = currentGoodsStatuses;
    }

    public MarketProperty getMarketProperty() {
        return this.marketProperty;
    }

    public void setMarketProperty(MarketProperty marketProperty) {
        this.marketProperty = marketProperty;
    }

    public int getGoodSaleDifference() {
        return this.goodSaleDifference;
    }

    public void setGoodSaleDifference(int goodSaleDifference) {
        this.goodSaleDifference = goodSaleDifference;
    }

    public BigDecimal getRevenueDiff() {
        return this.revenueDiff;
    }

    public void setRevenueDiff(BigDecimal revenueDiff) {
        this.revenueDiff = revenueDiff;
    }

    public BigDecimal getGrossProfitDiff() {
        return this.grossProfitDiff;
    }

    public void setGrossProfitDiff(BigDecimal grossProfitDiff) {
        this.grossProfitDiff = grossProfitDiff;
    }

    public BigDecimal getGrossMarginDiff() {
        return this.grossMarginDiff;
    }

    public void setGrossMarginDiff(BigDecimal grossMarginDiff) {
        this.grossMarginDiff = grossMarginDiff;
    }
}
