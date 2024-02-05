package com.base.servicer1.repositories;

import com.base.servicer1.entities.Order;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
