package univ.fenncim.ddd.ha.finance.service;

import java.util.List;
import org.springframework.stereotype.Service;
import univ.fenncim.ddd.ha.finance.dto.CurrentGoodsSaleStatus;
import univ.fenncim.ddd.ha.finance.dto.ElasticityResult;
import univ.fenncim.ddd.ha.finance.processor.FinancialDataProcessor;

@Service
public class SalesDataProcessingService {
    public SalesDataProcessingService() {
    }

    public void saleAnalysis(CurrentGoodsSaleStatus currentSaleStatus) {
        FinancialDataProcessor.saleAnalysisProcessing(currentSaleStatus);
    }

    public ElasticityResult deepSaleAnalysisElasticityCheck(List<CurrentGoodsSaleStatus> currentSaleStatuses) {
        return FinancialDataProcessor.elasticityCheckProcessing(currentSaleStatuses);
    }
}

