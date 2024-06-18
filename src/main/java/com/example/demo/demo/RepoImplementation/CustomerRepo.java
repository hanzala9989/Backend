package com.example.demo.demo.RepoImplementation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.demo.Entities.CustomerEntity;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Integer> {
}