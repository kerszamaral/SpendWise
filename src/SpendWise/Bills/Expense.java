package SpendWise.Bills;

import java.time.LocalDate;

public abstract class Expense {
    private double value;

    private boolean isEssential;

    private LocalDate date;

    private String description;

    public Expense(double value, boolean isEssential, LocalDate date, String description) {
        this.value = value;
        this.isEssential = isEssential;
        this.date = date;
        this.description = description;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @param isEssential the isEssential to set
     */
    public void setEssential(boolean isEssential) {
        this.isEssential = isEssential;
    }

    /**
     * @return the isEssential
     */
    public boolean isEssential() {
        return isEssential;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public abstract double calculatePayment();
}
