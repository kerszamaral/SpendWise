package SpendWise.Bills;

import java.util.ArrayList;
import java.time.LocalDate;

import SpendWise.Utils.Pair;

public class Fixed extends Expense {
    private ArrayList<Pair<LocalDate, Double>> valueHistory;

    public Fixed(double value, boolean isEssential, LocalDate date, String description) {
        super(value, isEssential, date, description);
        this.valueHistory = new ArrayList<Pair<LocalDate, Double>>();
        this.valueHistory.add(new Pair<LocalDate, Double>(date, value));
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

    public double valueInMonth(LocalDate date) {
        long listLength = this.valueHistory.size();
        if (listLength == 0) {
            return -1;
        }
        // TODO Make this shit work

        for (int i = 0; i < listLength; i++) {
            if (i == listLength - 1) {
                return this.valueHistory.get(i).getValue();
            }

        }

        for (Pair<LocalDate, Double> pair : this.valueHistory) {
            if (pair.getKey().getMonth() == date.getMonth() && pair.getKey().getYear() == date.getYear()) {
                return pair.getValue();
            }
        }
        return -1;
    }

    public void updateValue(double newValue) {
        this.setValue(newValue);
        this.valueHistory.add(new Pair<LocalDate, Double>(LocalDate.now(), newValue));
        return;
    }

    @Override
    public double calculatePayment() {
        return this.getValue();
    }
}
