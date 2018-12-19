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
                                                         new ExchangeRate(new BigDecimal(30.0), new Date()),
                                                         new ExchangeRate(new BigDecimal(50.0), new Date()));
        List<Strategy> strategies = Arrays.asList(
                new Strategy(1, new BigDecimal(0.5), DecisionType.SELL, new BigDecimal(0.2)));

        StrategyApplicator applicator = new StrategyApplicator(exchangeRates, strategies);
        applicator.applyStrategies();
        Map<Strategy, StrategyResult> results = applicator.getStrategyResults();
        StrategyResult strategyResult = results.get(strategies.get(0));
        BigDecimal end = new BigDecimal(50.0);
        BigDecimal start = new BigDecimal(30.0);
        String expected = end.subtract(start).divide(start, BigDecimal.ROUND_HALF_EVEN).toString();
        Assert.assertEquals(strategyResult.getResult(), expected);
    }
}
