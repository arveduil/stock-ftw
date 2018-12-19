import ftw.stock.ExchangeRate;
import ftw.strategy.DecisionType;
import ftw.strategy.applicator.StrategyApplicator;
import ftw.strategy.model.Strategy;
import ftw.strategy.model.StrategyResult;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.math.BigDecimal;
import java.util.*;

public class StrategyApplicatorTest {

    @Test
    public void singleStrategyApplicationTest() throws InvalidStrategyValuesException {
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

    @Test
    public void doubleStrategyApplicationTest() throws InvalidStrategyValuesException {
        List<ExchangeRate> exchangeRates = Arrays.asList(new ExchangeRate(new BigDecimal(20.0), new Date()),
                                                         new ExchangeRate(new BigDecimal(25.0), new Date()),
                                                         new ExchangeRate(new BigDecimal(50.0), new Date()),
                                                         new ExchangeRate(new BigDecimal(25.0), new Date()));
        List<Strategy> strategies = Arrays.asList(
                new Strategy(1, new BigDecimal(0.5), DecisionType.SELL, new BigDecimal(0.2)),
                new Strategy(1, new BigDecimal(0.2), DecisionType.BUY, new BigDecimal(0.1)));

        BigDecimal budget = new BigDecimal(1000000.0);
        StrategyApplicator applicator = new StrategyApplicator(exchangeRates, strategies, budget);
        applicator.applyStrategies();
        Map<Strategy, StrategyResult> results = applicator.getStrategyResults();

        StrategyResult strategyResult = results.get(strategies.get(0));
        BigDecimal end = new BigDecimal(50.0);
        BigDecimal expected = budget.add(budget.multiply(new BigDecimal(0.2).multiply(end)));

        budget = expected;
        StrategyResult strategyResult2 = results.get(strategies.get(1));
        BigDecimal end2 = new BigDecimal(25.0);
        BigDecimal expected2 = budget.subtract(budget.multiply(new BigDecimal(0.1).multiply(end)));

        Assert.assertEquals(strategyResult.getResult(), expected);
        Assert.assertEquals(strategyResult2.getResult(), expected2);
    }

    @Test(expected = InvalidStrategyValuesException.class)
    public void invalidChangeIntervalTest() throws InvalidStrategyValuesException {
        Strategy strategy = new Strategy(-1, new BigDecimal(0.5), DecisionType.SELL, new BigDecimal(0.2));
    }

    @Test(expected = InvalidStrategyValuesException.class)
    public void invalidChangeTest() throws InvalidStrategyValuesException {
        Strategy strategy = new Strategy(1, new BigDecimal(1.5), DecisionType.SELL, new BigDecimal(0.2));
    }

    @Test(expected = InvalidStrategyValuesException.class)
    public void invalidInvestmentPercentageTest() throws InvalidStrategyValuesException {
        Strategy strategy = new Strategy(1, new BigDecimal(0.5), DecisionType.SELL, new BigDecimal(2.2));
    }
}
