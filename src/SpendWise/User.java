package SpendWise;

import SpendWise.Managers.ExpensesManager;
import SpendWise.Utils.Enums.AccountFields;

public class User {
    // Atributos
    private String username;
    private String name;
    private String email;
    private String password;
    private double income;
    private double monthlyLimit;
    protected ExpensesManager expensesManager;

    /**
     * @return the expensesManager
     */
    public ExpensesManager getExpensesManager() {
        return expensesManager;
    }

    // Construtores
    public User(String username, String name, String email, String password, double income, double monthlyLimit) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.income = income;
        this.monthlyLimit = monthlyLimit;
        this.expensesManager = new ExpensesManager();
    }

    public User() {
        this.username = "";
        this.name = "";
        this.email = "";
        this.password = "";
        this.income = 0;
        this.monthlyLimit = 0;
        this.expensesManager = new ExpensesManager();
    }

    // Metodos
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (!checkPassword(oldPassword)) {
            return false;
        }
        this.password = newPassword;
        return true;
    }

    public String getField(AccountFields field) {
        switch (field) {
            case NAME:
                return this.name;
            case USERNAME:
                return this.username;
            case EMAIL:
                return this.email;
            case PASSWORD:
                return this.password;
            default:
                return "*".repeat(this.getPasswordSize());
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the income
     */
    public double getIncome() {
        return income;
    }

    /**
     * @param income the income to set
     */
    public void setIncome(double income) {
        this.income = income;
    }

    /**
     * @return the monthlyLimit
     */
    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    /**
     * @param monthlyLimit the monthlyLimit to set
     */
    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public int getPasswordSize() {
        return this.password.length();
    }

}
