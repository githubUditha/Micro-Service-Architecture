package com.order.order.repo;

import com.order.order.dto.OrderDTO;
import com.order.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM Order WHERE id=?1")
    Order getOrderById(Integer orderId);
}
