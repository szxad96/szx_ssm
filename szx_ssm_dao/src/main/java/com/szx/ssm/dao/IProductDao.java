package com.szx.ssm.dao;

import com.szx.ssm.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * product持久化工作
* */
public interface IProductDao {
    //根据ID查询产品
    @Select("select * from product where id = #{id}")
    public Product findById(String id) throws Exception;
    /**
     * 查询所有的商品信息
     * @return
     * @throws Exception
     */
    @Select("select * from product")
    public List<Product> findAll() throws Exception;
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void save (Product product);
}
