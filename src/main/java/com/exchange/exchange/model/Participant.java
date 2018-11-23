package com.exchange.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "PARTICIPANT")
@Entity
public class Participant extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARTICIPANT_ID")
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @JsonIgnore
    @JoinColumn(name = "EXCHANGE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    private Exchange exchange;

    @NotNull
    @Column(name = "ACCEPT_INVITE")
    private boolean acceptInvite;

    @NotNull
    @Column(name = "IS_IN_GROUP")
    private boolean isInGroup;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "THEME_ID")
    private Theme theme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public boolean getAcceptInvite() {
        return acceptInvite;
    }

    public void setAcceptInvite(boolean acceptInvite) {
        this.acceptInvite = acceptInvite;
    }

    public boolean getIsInGroup() {
        return isInGroup;
    }

    public void setInGroup(boolean inGroup) {
        isInGroup = inGroup;
    }

    @Nullable
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(@Nullable Theme theme) {
        this.theme = theme;
    }
}
