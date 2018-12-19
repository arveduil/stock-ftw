import ftw.stock.ExchangeRate;
import ftw.strategy.DecisionType;
import ftw.strategy.applicator.StrategyApplicator;
import ftw.strategy.model.Strategy;
import ftw.strategy.model.StrategyResult;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class StrategyApplicatorTest {

    @Test
    public void singleStrategyApplicationTest() {
        List<ExchangeRate> exchangeRates = Arrays.asList(new ExchangeRate(new BigDecimal(20.0), new Date()),
                                                         new ExchangeRate(new BigDecimal(25.0), new Date()),
                                                         new ExchangeRate(new BigDecimal(50.0), new Date()));
        List<Strategy> strategies = Arrays.asList(
                new Strategy(1, new BigDecimal(0.5), DecisionType.SELL, new BigDecimal(0.2)));

        BigDecimal budget = new BigDecimal(1000000.0);
        StrategyApplicator applicator = new StrategyApplicator(exchangeRates, strategies, budget);
        applicator.applyStrategies();
        Map<Strategy, StrategyResult> results = applicator.getStrategyResults();
        StrategyResult strategyResult = results.get(strategies.get(0));
        BigDecimal end = new BigDecimal(50.0);
        BigDecimal start = new BigDecimal(25.0);
        BigDecimal expected = budget.add(budget.multiply(new BigDecimal(0.2).multiply(end)));
        Assert.assertEquals(strategyResult.getResult(), expected);
    }
}
