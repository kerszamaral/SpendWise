package SpendWise.Logic.Bills;

import java.util.ArrayList;
import java.time.LocalDate;

import SpendWise.Utils.Triple;

public class Fixed extends Expense {
    private ArrayList<Triple<LocalDate, LocalDate, Double>> valueHistory;

    public Fixed(double value, boolean isEssential, LocalDate date, String description) {
        super(value, isEssential, date, description);
        this.valueHistory = new ArrayList<Triple<LocalDate, LocalDate, Double>>();
        this.valueHistory.add(new Triple<LocalDate, LocalDate, Double>(date, LocalDate.MAX.minusDays(1), value));
    }

    public double totalValue() {
        double sum = 0;
        LocalDate today = LocalDate.now();
        long totalMonths = this.getDate().until(today).toTotalMonths();
        for (long i = 0; i < totalMonths; i++) {
            sum += this.valueInMonth(this.getDate().plusMonths(i));
        }
        return sum;
    }

    public double totalValue(LocalDate date) {
        double sum = 0;
        long totalMonths = this.getDate().until(date).toTotalMonths();
        if (totalMonths < 0) {
            return 0;
        }

        for (long i = 0; i < totalMonths; i++) {
            sum += this.valueInMonth(this.getDate().plusMonths(i));
        }
        return sum;
    }

    public double valueInMonth(LocalDate date) {
        for (Triple<LocalDate, LocalDate, Double> triple : this.valueHistory) {
            LocalDate rangeStart = triple.getFirst().minusDays(1);
            LocalDate rangeEnd = triple.getSecond().plusDays(1);
            if (rangeStart.isBefore(date) && rangeEnd.isAfter(date)) {
                return triple.getThird();
            }
        }
        return 0; // No value found, dont add anything
    }

    public void updateValue(double newValue) {
        this.setValue(newValue);
        LocalDate today = LocalDate.now();
        Triple<LocalDate, LocalDate, Double> lastUpdate = this.valueHistory.get(this.valueHistory.size() - 1);
        lastUpdate.setSecond(today);
        this.valueHistory.add(new Triple<LocalDate, LocalDate, Double>(today, LocalDate.MAX.minusDays(1), newValue));
    }

    @Override
    public double calculatePayment() {
        return this.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Fixed) {
            Fixed other = (Fixed) obj;
            return super.equals(other) && this.valueHistory.equals(other.valueHistory);
        }
        return false;
    }
}
