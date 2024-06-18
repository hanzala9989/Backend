package com.example.demo.demo.Config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.example.demo.demo.Entities.CustomerEntity;

public class CustomerPorcessor implements ItemProcessor<CustomerEntity, CustomerEntity> {

    @Override
    @Nullable
    public CustomerEntity process(@NonNull CustomerEntity customerEntity) throws Exception {
        return customerEntity;
    }

}
