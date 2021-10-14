package pl.local.neoteo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Utility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private double amount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private UtilityType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UtilityType getType() {
        return type;
    }

    public void setType(UtilityType type) {
        this.type = type;
    }
}
