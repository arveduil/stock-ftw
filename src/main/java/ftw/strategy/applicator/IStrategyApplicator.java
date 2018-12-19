package ftw.strategy.applicator;

import ftw.stock.ExchangeRate;
import ftw.stock.data.reader.DataUnit;
import ftw.strategy.model.Strategy;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface IStrategyApplicator {

    public void setData(List<ExchangeRate> data);

    public void setStrategies(List<Strategy> strategies);

    public void setInitialBudget(BigDecimal initialBudget);

    public void applyStrategies();
}
