package com.order.order.service;

import com.inventory.inventory.dto.InventoryDTO;
import com.order.order.common.ErrorOrderResponce;
import com.order.order.common.OrderResponce;
import com.order.order.common.SuccessOrderResponce;
import com.order.order.dto.OrderDTO;
import com.order.order.model.Order;
import com.order.order.repo.OrderRepo;
import com.product.product.dto.ProductDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional
public class OrderService {
    private final WebClient inventoryWebClient;
    private final WebClient orderWebClient;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    ModelMapper modelMapper;

    public OrderService(WebClient inventoryWebClient, WebClient orderWebClient, OrderRepo orderRepo, ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.orderWebClient = orderWebClient;
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders(){
        List<Order> orderList=orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderResponce saveOrder(OrderDTO orderDTO){
        Integer itemId = orderDTO.getItemId();

        try {
            InventoryDTO inventoryResponce = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            assert inventoryResponce != null;

            Integer productId = inventoryResponce.getProductId();

            ProductDTO productResponce = orderWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/product/{productId}").build(productId))
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            assert productResponce != null;

            if (inventoryResponce.getQuantity()>0){
                if (productResponce.getForSale()==1) {
                    orderRepo.save(modelMapper.map(orderDTO, Order.class));
                }
                else{
                    return new ErrorOrderResponce("This Item is not for sale");
                }
                return new SuccessOrderResponce(orderDTO);
            }
            else{
                return new ErrorOrderResponce("Item Not Available. Please Try Later");
            }
        }
        catch (WebClientResponseException e) {
            if(e.getStatusCode().is5xxServerError()){
                return new ErrorOrderResponce("Item is not found");
            }
        }
        return null;

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
