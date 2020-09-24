package com.szx.ssm.service;

import com.szx.ssm.domain.Orders;

import java.util.List;

public interface IOrderService {
    List<Orders> findAll(int page, int size) throws Exception;

    Orders findById(String orderId) throws Exception;
}