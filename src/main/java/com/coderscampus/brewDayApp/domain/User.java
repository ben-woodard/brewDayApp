package com.coderscampus.brewDayApp.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {
    private static final long serialVersionUID = 2025389852147750927L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<Authority> authorities = new ArrayList<>();
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnoreProperties("user")
    private List<Product> products;

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long companyId;
//    private String companyName;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
//    @JsonIgnoreProperties("company")
//    private List<User> users;

    //    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
//    @JsonIgnore
//    private List<Message> messages = new ArrayList<>();

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long companyId;
//    private String companyName;
//    @OneToMany(mappedBy = "company", orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private List<Brand> brands;
//    private String companyEmail;
//    private String companyPassword;





    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String getUsername() {
        // email in our case
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    
    public User firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }
    
    public User lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    
    public User email(String email) {
        this.email = email;
        return this;
    }

    public User authority(String authority) {
        Authority auth = new Authority(authority, this);
        this.getAuthorities().add(auth);
        return this;
    }
    
    public User password(String password) {
        this.password = password;
        return this;
    }

    public User products(List<Product> products) {
        this.products = products;
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public User build () {
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}