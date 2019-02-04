import ftw.simulation.model.SimulationInitialValues;
import ftw.stock.ExchangeRate;
import ftw.strategy.applicator.StrategyApplicator;
import ftw.strategy.model.BuyStrategy;
import ftw.strategy.model.SellStrategy;
import ftw.strategy.model.Strategy;
import ftw.strategy.model.exception.InvalidSimulationInitialValuesException;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class StrategyApplicatorTest {

    @Test
    public void singleStrategyApplicationTest() throws InvalidStrategyValuesException, InvalidSimulationInitialValuesException {
        List<ExchangeRate> exchangeRates = Arrays.asList(new ExchangeRate(new BigDecimal(20.0), new Date(90, 0, 0)),
                                                         new ExchangeRate(new BigDecimal(25.0), new Date(90, 0, 1)),
                                                         new ExchangeRate(new BigDecimal(50.0), new Date(90, 0, 2)));
        List<Strategy> strategies = Arrays.asList(
                new SellStrategy(1, new BigDecimal(0.5), new BigDecimal(0.2)));

        BigDecimal budget = new BigDecimal(1.0);
        SimulationInitialValues simulationInitialValues = new SimulationInitialValues(0, 2);
        StrategyApplicator applicator = new StrategyApplicator(exchangeRates, strategies);
        applicator.applyStrategies();
        BigDecimal start = new BigDecimal(25.0);
        BigDecimal end = new BigDecimal(50.0);
        BigDecimal change = end.subtract(start).divide(start, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal expected = budget.add(budget.multiply(new BigDecimal(0.2).multiply(change)));
        Assert.assertEquals(applicator.getSimulationResult().getFinalResult(), expected);
    }

    @Test
    public void doubleStrategyApplicationTest() throws InvalidStrategyValuesException, InvalidSimulationInitialValuesException {
        List<ExchangeRate> exchangeRates = Arrays.asList(new ExchangeRate(new BigDecimal(20.0), new Date()),
                                                         new ExchangeRate(new BigDecimal(25.0), new Date()),
                                                         new ExchangeRate(new BigDecimal(50.0), new Date()),
                                                         new ExchangeRate(new BigDecimal(25.0), new Date()));
        List<Strategy> strategies = Arrays.asList(
                new SellStrategy(1, new BigDecimal(0.5), new BigDecimal(0.2)),
                new BuyStrategy(1, new BigDecimal(-0.2), new BigDecimal(0.1)));

        BigDecimal budget = new BigDecimal(1.0);
        SimulationInitialValues simulationInitialValues = new SimulationInitialValues(0, 3);
        StrategyApplicator applicator = new StrategyApplicator(exchangeRates, strategies);
        applicator.applyStrategies();

        BigDecimal start = new BigDecimal(25.0);
        BigDecimal end = new BigDecimal(50.0);
        BigDecimal change = end.subtract(start).divide(start, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal expected =
                new BigDecimal(1.0).add(new BigDecimal(1.0).multiply(new BigDecimal(0.2)).multiply(change));

        BigDecimal start2 = new BigDecimal(50.0);
        BigDecimal end2 = new BigDecimal(25.0);
        BigDecimal change2 = end2.subtract(start2).divide(start2, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal expected2 = expected.subtract(expected.multiply(new BigDecimal(0.1)).multiply(change2));

        Assert.assertEquals(expected.setScale(15, RoundingMode.HALF_UP),
                applicator.getSimulationResult().getResult(2).setScale(15, RoundingMode.HALF_UP));
        Assert.assertEquals(expected2.setScale(15, RoundingMode.HALF_UP),
                applicator.getSimulationResult().getResult(3).setScale(15, RoundingMode.HALF_UP));
        Assert.assertEquals(expected2.setScale(15, RoundingMode.HALF_UP),
                applicator.getSimulationResult().getFinalResult().setScale(15, RoundingMode.HALF_UP));
    }

    @Test(expected = InvalidStrategyValuesException.class)
    public void invalidChangeIntervalTest() throws InvalidStrategyValuesException {
        Strategy sellStrategy = new SellStrategy(-1, new BigDecimal(0.5), new BigDecimal(0.2));
        Strategy buyStrategy = new BuyStrategy(-1, new BigDecimal(0.5), new BigDecimal(0.2));
    }

    @Test(expected = InvalidStrategyValuesException.class)
    public void invalidChangeTest() throws InvalidStrategyValuesException {
        Strategy sellStrategy = new SellStrategy(1, new BigDecimal(1.5), new BigDecimal(0.2));
        Strategy buyStrategy = new BuyStrategy(1, new BigDecimal(1.5), new BigDecimal(0.2));
    }

    @Test(expected = InvalidStrategyValuesException.class)
    public void invalidInvestmentPercentageTest() throws InvalidStrategyValuesException {
        Strategy sellStrategy = new SellStrategy(1, new BigDecimal(0.5), new BigDecimal(2.2));
        Strategy buyStrategy = new BuyStrategy(1, new BigDecimal(0.5), new BigDecimal(2.2));
    }
}
