//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package univ.fenncim.ddd.ha.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import univ.fenncim.ddd.ha.finance.dto.CurrentGoodsSaleStatus;
import univ.fenncim.ddd.ha.finance.dto.ElasticityResult;
import univ.fenncim.ddd.ha.finance.service.SalesDataProcessingService;

import java.util.List;

@RestController
@RequestMapping({"/v1/api/sales"})
public class SaleAnalysisController {
    private SalesDataProcessingService salesDataProcessingService;

    @Autowired
    public SaleAnalysisController(SalesDataProcessingService salesDataProcessingService) {
        this.salesDataProcessingService = salesDataProcessingService;
    }

    @PostMapping(
            value = {"/status"},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<CurrentGoodsSaleStatus> analyseGoodsSaleDynamics(@RequestBody CurrentGoodsSaleStatus goodSalesDynamic) {
        this.salesDataProcessingService.saleAnalysis(goodSalesDynamic);
        return ResponseEntity.ok(goodSalesDynamic);
    }

    @PostMapping(
            value = {"/elasticity"},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<ElasticityResult> elasticityCheck(@RequestBody List<CurrentGoodsSaleStatus> goodSalesDynamics) {
        return ResponseEntity.ok(this.salesDataProcessingService.deepSaleAnalysisElasticityCheck(goodSalesDynamics));
    }
}
