package com.order.order.service;

import com.order.order.dto.OrderDTO;
import com.order.order.model.Order;
import com.order.order.repo.OrderRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders(){
        List<Order> orderList=orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderDTO saveOrder(OrderDTO orderDTO){
        orderRepo.save(modelMapper.map(orderDTO, Order.class));
        return orderDTO;
    }

    public OrderDTO updateOrder(OrderDTO orderDTO){
        orderRepo.save(modelMapper.map(orderDTO, Order.class));
        return orderDTO;
    }

    public String deleteOrder(Integer orderId){
        orderRepo.deleteById(orderId);
        return "Order Deleted.";
    }
    public OrderDTO getOrderById(Integer orderId){
        Order order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }
}
