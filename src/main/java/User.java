
import java.io.Serializable;
import java.math.BigDecimal;



public class User extends InputService implements Serializable {

    private Long id;

    private Integer code;

    private Boolean lock;

    private BigDecimal balance;

    private String file;

    public User(Long id, Integer code, Boolean lock, BigDecimal balance, String file) {
        this.id = id;
        this.code = code;
        this.lock = lock;
        this.balance = balance;
        this.file = file;

    }

    protected User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal addCash(BigDecimal money) {
        this.balance = balance.add(money);
        return balance;
    }

    public BigDecimal getCash(BigDecimal money) {
        this.balance = balance.subtract(money);
        return balance;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


}














