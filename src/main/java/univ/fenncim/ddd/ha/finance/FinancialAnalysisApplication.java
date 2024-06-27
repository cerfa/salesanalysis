package univ.fenncim.ddd.ha.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"univ.fenncim"})
public class FinancialAnalysisApplication {
    public FinancialAnalysisApplication() {
        //Just for sonar happiness.
    }

    public static void main(String[] args) {
        SpringApplication.run(FinancialAnalysisApplication.class, args);
    }
}
