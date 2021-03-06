package ftw.strategy.applicator;

import ftw.simulation.model.SimulationResult;
import ftw.stock.ExchangeRate;
import ftw.stock.data.reader.DataUnit;
import ftw.strategy.model.Strategy;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface IStrategyApplicator {

    void applyStrategies();

    SimulationResult getSimulationResult();
}
