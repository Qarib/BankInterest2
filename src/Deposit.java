import java.math.BigDecimal;

public class Deposit implements Comparable <Deposit> {
    private BigDecimal depositBalance;
    private int duratioInDays;
    private BigDecimal payedIntererst;
    private int customerNumber;
    private DepositType depositType;
    private BigDecimal interestRate;

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }

    public BigDecimal getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
    }

    public int getDuratioInDays() {
        return duratioInDays;
    }

    public void setDuratioInDays(int duratioInDays) {
        this.duratioInDays = duratioInDays;
    }

    public BigDecimal getPayedIntererst() {
        return payedIntererst;
    }

    public void setPayedIntererst(BigDecimal payedIntererst) {
        this.payedIntererst = payedIntererst;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public int compareTo(Deposit o) {
        return 0;
    }
}
