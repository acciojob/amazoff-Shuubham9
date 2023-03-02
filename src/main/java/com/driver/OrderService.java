package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    static
    OrderRepository OrdRepo;



    public void addOrder(Order order) {
        OrdRepo.addOrder(order);
    }

    public void addPartner(String partnerId) {
        OrdRepo.addPartner(partnerId);
    }

    public static Order getOrderById(String orderId) {
        Order order = OrdRepo.getOrderById(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return  OrdRepo.getPartnerById(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        OrdRepo.addOrderPartnerPair(orderId,partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return OrdRepo.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
         return OrdRepo.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
       return  OrdRepo.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return OrdRepo.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String deliveryTime, String partnerId) {
        String [] str = deliveryTime.split(":");
        int a = Integer.parseInt(str[0]);
        int b= Integer.parseInt(str[1]);
        int c= a+b;
        return OrdRepo.getOrdersLeftAfterGivenTimeByPartnerId(c,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int lasttime = OrdRepo.getLastDeliveryTimeByPartnerId(partnerId);
        int h = lasttime/60;
        int m =lasttime%60;

        String hr = Integer.toString(h);
        String min = Integer.toString(m);

        if(hr.length()<2)
            hr= '0'+hr;
        if(min.length()<2)
            min= '0'+min;

        return hr+':'+min;
    }

    public void deletePartnerById(String partnerId) {
        OrdRepo.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        OrdRepo.deleteOrderById(orderId);
    }
}
