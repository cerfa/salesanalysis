//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package univ.fenncim.ddd.ha.finance.dto;

import java.math.BigDecimal;

public class CurrentGoodsSaleStatus extends SaleResult {
    private int id;
    private BigDecimal goodsUnitCost;
    private BigDecimal goodsUnitPrice;
    private int goodsTotalSales;

    public CurrentGoodsSaleStatus() {
        //Sonar
    }

    public BigDecimal getGoodsUnitCost() {
        return this.goodsUnitCost;
    }

    public void setGoodsUnitCost(BigDecimal goodsUnitCost) {
        this.goodsUnitCost = goodsUnitCost;
    }

    public BigDecimal getGoodsUnitPrice() {
        return this.goodsUnitPrice;
    }

    public void setGoodsUnitPrice(BigDecimal goodsUnitPrice) {
        this.goodsUnitPrice = goodsUnitPrice;
    }

    public int getGoodsTotalSales() {
        return this.goodsTotalSales;
    }

    public void setGoodsTotalSales(int goodsTotalSales) {
        this.goodsTotalSales = goodsTotalSales;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
