package com.example.demo.demo.Entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerEntity {
    @Id
    private int id;
    private String CustomerId;
    private String FirstName;
    private String LastName;
    private String Company;
    private String City;
    private String Country;
    private String Phone1;
    private String Phone2;
    private String Email;
    private String SubscriptionDate;
    private String Website;

}

