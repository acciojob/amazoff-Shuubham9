package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<String,Order> ord = new HashMap<>();
    Map <String,DeliveryPartner> delivery  = new HashMap<>();
    Map<String,String> ordnpartner =  new HashMap<>();
    Map<String, List<String>> partnerOrdersList =  new HashMap<>();

    public void addOrder(Order order) {
        ord.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        delivery.put(partnerId, new DeliveryPartner(partnerId));
    }

    public Order getOrderById(String orderId) {
        return ord.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return delivery.get(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(ord.containsKey(orderId) && delivery.containsKey(partnerId)){
            ordnpartner.put(orderId,partnerId);

            List<String> curOrd = new ArrayList<>();
            if(partnerOrdersList.containsKey(partnerId)){
                curOrd= partnerOrdersList.get(partnerId);
            }
            curOrd.add(orderId);

            partnerOrdersList.put(partnerId,curOrd);

            delivery.get(partnerId).setNumberOfOrders(curOrd.size());
        }
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return partnerOrdersList.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrdersList.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> ords = new ArrayList<>();
        for (String order: ord.keySet()) {
            ords.add(order);
        }
        return ords;
    }

    public Integer getCountOfUnassignedOrders() {
        return ord.size()-ordnpartner.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int deliveryTime, String partnerId) {

        int cnt=0;
        List<String> orders = partnerOrdersList.get(partnerId);
        for(String order: orders){
            if(ord.get(order).getDeliveryTime()>deliveryTime){
                cnt++;
            }
        }
        return cnt;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> orders = partnerOrdersList.get(partnerId);
        int maxTime= Integer.MIN_VALUE;
        for(String curOrd : orders){
           maxTime=Math.max(maxTime,ord.get(curOrd).getDeliveryTime());
        }
        return maxTime;
    }

    public void deletePartnerById(String partnerId) {
        delivery.remove(partnerId);

        List<String> orders = partnerOrdersList.get(partnerId);
        partnerOrdersList.remove(partnerId);

        for (String order: orders) {
            ordnpartner.remove(order);
        }
    }

    public void deleteOrderById(String orderId) {
        ord.remove(orderId);

        String partner= ordnpartner.get(orderId);
        ordnpartner.remove(orderId);

        List<String> orders = partnerOrdersList.get(partner);
        orders.remove(orderId);

        partnerOrdersList.put(partner,orders);

        delivery.get(partner).setNumberOfOrders(orders.size());
    }
}
