package pro.mrpc.mt4.network.parts;

import pro.mrpc.mt4.network.enums.AccountType;

/**
 * Account information received from the MT4 server during login.
 * Parsed from the account response buffer.
 */
public class AccountInfo {
    /** Account type (Real, Contest, Demo). */
    public AccountType accountType;
    /** Account holder name / currency name. */
    public String name;
    /** Deposit currency (e.g. "USD", "EUR"). Parsed from the account group (ConGroup). */
    public String currency;
    /** Broker company name. Parsed from the account group (ConGroup). */
    public String company;
    /** Account group name. */
    public String group;
    /** Account leverage. */
    public int leverage;
    /** Account balance. */
    public double balance;
    /** Account credit (virtual credit). */
    public double credit;
    /** Account mode: 0 = Master (full trading), 1 = Investor (read-only). */
    public int accountMode;
    /** Daylight saving time flag. */
    public byte timeDst;
    /** Server timezone offset. */
    public byte timeZone;

    /** Returns true if this is an investor (read-only) account. */
    public boolean isInvestor() {
        return accountMode == 1;
    }

    /** Returns true if this is a demo account. */
    public boolean isDemo() {
        return accountType == AccountType.Demo;
    }

    @Override
    public String toString() {
        return "AccountInfo{type=" + accountType + ", name='" + name + "', currency='" + currency
                + "', company='" + company + "', group='" + group
                + "', leverage=" + leverage + ", balance=" + balance + ", credit=" + credit
                + ", mode=" + (accountMode == 0 ? "Master" : "Investor") + '}';
    }
}
