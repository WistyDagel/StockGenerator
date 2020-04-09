import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Account {
    private Long account_number;
    private String ssn;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private Double beginning_balance;
    private ArrayList<StockTrade> stockTradeList;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public Account(Long account_number, String ssn, String first_name, String last_name, String email, String phone, Double beginning_balance) {
        this.account_number = account_number;
        this.ssn = ssn;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.beginning_balance = beginning_balance;
    }

    public Long getAccount_number() {
        return account_number;
    }

    public void setAccount_number(Long account_number) {
        this.account_number = account_number;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getBeginning_balance() {
        return beginning_balance;
    }

    public void setBeginning_balance(Double beginning_balance) {
        this.beginning_balance = beginning_balance;
    }

    public ArrayList<StockTrade> getStockTradeList() {
        return stockTradeList;
    }

    public void setStockTradeList(ArrayList<StockTrade> stockTradeList) {
        this.stockTradeList = stockTradeList;
    }

    @Override
    public String toString() {
        return "Account{" + '\n' +
                "Date Printed='" + dateTimeFormatter.format(LocalDateTime.now()) + '\'' + '\n' +
                "account_number='" + account_number + '\'' + '\n' +
                "first_name='" + first_name + '\'' + '\n' +
                "last_name='" + last_name + '\'' + '\n' +
                "email='" + email + '\'' + '\n' +
                "phone='" + phone + '\'' + '\n' +
                "beginning_balance='" + beginning_balance + '\'' + '\n' +
                '}';
    }
}
