package com.szx.ssm.dao;

import com.szx.ssm.domain.Member;
import com.szx.ssm.domain.Orders;
import com.szx.ssm.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IOrdersDao {
    //顺带这把产品信息也要传递出来
    @Select("select * from orders")
    @Results({
            @Result(id = true, property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            @Result(property = "product",column = "productId", javaType = Product.class, one = @One(select = "com.szx.ssm.dao.IProductDao.findById")),
/*<td>${orders.product.productName }</td> order 对应的Product对象查询出来，用到的方法是IProductDap中的findById*/

    })
    public List<Orders> findAll() throws Exception;
    //涉及到多表操作
    //顺带这把产品信息也要传递出来
    @Select("select * from orders where id = #{orderId}")
    @Results({
            @Result(id = true, property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            @Result(property = "product",column = "productId", javaType = Product.class, one = @One(select = "com.szx.ssm.dao.IProductDao.findById")),
            /*<td>${orders.product.productName }</td> order 对应的Product对象查询出来，用到的方法是IProductDap中的findById*/
            @Result(property = "member", column = "memberId", javaType = Member.class, one = @One(select = "com.szx.ssm.dao.IMemberDao.findById")),
            @Result(property = "travellers",column = "id",javaType = java.util.List.class, many = @Many(select = "com.szx.ssm.dao.ITravellerDao.findByOrdersId"))
    })
    public Orders findById(String orderId) throws Exception;
}
