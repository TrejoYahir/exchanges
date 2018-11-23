package com.exchange.exchange.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Table(name = "USER")
@Entity
public class User extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true, name = "EMAIL")
    @Size(max = 100)
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Size(max = 20)
    @Column(name = "ALIAS")
    private String alias;

    @Size(max = 30)
    @Column(name = "PASS")
    private String pass;

    @NotNull
    @Column(name = "EXTERNAL")
    private Boolean external;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Boolean getExternal() {
        return external;
    }

    public void setExternal(Boolean external) {
        this.external = external;
    }
}
