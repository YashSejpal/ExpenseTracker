import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.*;

import java.io.*;
import java.util.*;
import org.jfree.chart.*;

import javax.swing.*;

public class ExpenseService {
    private String filename;

    public ExpenseService(String username) {
        this.filename = username + "_expenses.json";
    }

    public void addExpense(Expense e) throws IOException, JSONException {
        JSONArray expenses = loadExpensesJSONArray();
        JSONObject obj = new JSONObject();
        obj.put("category", e.getCategory());
        obj.put("amount", e.getAmount());
        obj.put("date", e.getDate());
        expenses.put(obj);
        saveExpenses(expenses);
    }

    public List<Expense> loadExpenses() throws IOException, JSONException {
        JSONArray array = loadExpensesJSONArray();
        List<Expense> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            list.add(new Expense(
                    obj.getString("category"),
                    obj.getDouble("amount"),
                    obj.getString("date")
            ));
        }
        return list;
    }

    private JSONArray loadExpensesJSONArray() throws IOException, JSONException {
        File file = new File(filename);
        if (!file.exists()) return new JSONArray();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();
        return new JSONArray(sb.toString());
    }

    private void saveExpenses(JSONArray expenses) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(expenses.toString(4));
        writer.close();
    }

    public double getTotalExpense() throws IOException, JSONException {
        return loadExpenses().stream().mapToDouble(Expense::getAmount).sum();
    }

    public Map<String, Double> getExpenseByCategory() throws IOException, JSONException {
        List<Expense> expenses = loadExpenses();
        Map<String, Double> map = new HashMap<>();
        for (Expense e : expenses) {
            map.put(e.getCategory(), map.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
        }
        return map;
    }

    public void getExpenseTrend() throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Expense> expenses = loadExpenses();
        Map<String, Double> map = new HashMap<>();
        for (Expense e : expenses) {
            map.put(e.getDate(), map.getOrDefault(e.getDate(), 0.0) + e.getAmount());
        }
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            dataset.addValue(entry.getValue(), "Expense", entry.getKey());
        }
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Expense Trend",
                "Date",
                "Amount (₹)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        ChartPanel panel = new ChartPanel(lineChart);
        JFrame frame = new JFrame("Expense Trend");
        frame.setContentPane(panel);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }

    public void getHighLowCategory() throws IOException, JSONException {
        Map<String, Double> byCategory = getExpenseByCategory();
        String high = "", low = "";
        double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        for (Map.Entry<String, Double> entry : byCategory.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                high = entry.getKey();
            }
            if (entry.getValue() < min) {
                min = entry.getValue();
                low = entry.getKey();
            }
        }
        System.out.println("Highest: " + high +"\t"+max);
        System.out.println("Lowest: " + low+"\t"+min);
    }
}
