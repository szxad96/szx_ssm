package com.szx.ssm.dao;

import com.szx.ssm.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ITravellerDao {
    @Select("select * from traveller where id in(select travellerId from order_traveller where orderId=#{ordersId})")
    public List<Traveller> findByOrdersId(String orderId) throws Exception;
}
