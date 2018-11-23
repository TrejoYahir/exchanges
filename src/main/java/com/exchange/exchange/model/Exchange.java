package com.exchange.exchange.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Table(name = "EXCHANGE")
@Entity
public class Exchange extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EXCHANGE_ID")
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @Column(name="EXCHANGE_NAME")
    private String exchangeName;

    @NotNull
    @Column(name="MAX_AMOUNT")
    private double maxAmount;

    @NotNull
    @NotBlank
    @Column(name="LIMIT_DATE")
    private String limitDate;

    @NotNull
    @NotBlank
    @Column(name="EXCHANGE_DATE")
    private String exchangeDate;

    @NotNull
    @NotBlank
    @Size(max = 300)
    @Column(name="EXCHANGE_DESCRIPTION")
    private String exchangeDescription;

    @NotNull
    @NotBlank
    @Column(name="ACCESS_CODE", unique = true)
    @Size(max = 8)
    private String accessCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User creator;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public String getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public String getExchangeDescription() {
        return exchangeDescription;
    }

    public void setExchangeDescription(String exchangeDescription) {
        this.exchangeDescription = exchangeDescription;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
