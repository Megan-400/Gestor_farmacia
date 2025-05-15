package dao;

import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Cassandra
 */
public class GraficosDAO
{

    public ChartPanel graficoVentasPorVendedor(Map<String, Double> datos)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : datos.entrySet())
        {
            dataset.addValue(entry.getValue(), "Ventas", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ventas por Vendedor", "Vendedor", "Total Ventas", dataset
        );

        return new ChartPanel(chart);
    }

    public ChartPanel graficoVentasPorMes(Map<String, Double> datos, int año)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : datos.entrySet())
        {
            dataset.addValue(entry.getValue(), "Mes " + entry.getKey(), entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ventas por Mes - " + año, "Mes", "Total Ventas", dataset
        );

        return new ChartPanel(chart);
    }

    public ChartPanel graficoVentasAnuales(Map<Integer, Double> datos)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Double> entry : datos.entrySet())
        {
            dataset.addValue(entry.getValue(), "Año", entry.getKey().toString());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ventas Anuales", "Año", "Total Ventas", dataset
        );

        return new ChartPanel(chart);
    }

    public ChartPanel graficoVentasPorCliente(Map<String, Double> datos)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : datos.entrySet())
        {
            dataset.setValue("Cliente " + entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Ventas por Cliente", dataset, true, true, false
        );

        return new ChartPanel(chart);
    }
}
