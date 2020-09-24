package com.szx.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.szx.ssm.domain.Orders;
import com.szx.ssm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private IOrderService orderService;
    //未分页
    /*@RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Orders> ordersList = orderService.findAll();
        mv.addObject("ordersList",ordersList);
        mv.setViewName("orders-list");
        return mv;*/
    @RequestMapping("/findAll.do")
        public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Orders> ordersList = orderService.findAll(page,size);
        //pageInfo就是一个分页的bean
        PageInfo pageInfo = new PageInfo(ordersList);
        //则对制第一次查询的集合传入，可以获得更多的页面操作信息，封装在PageInfo这个类上
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-page-list");
        return mv;

    }
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name="id", required = true)String orderId) throws Exception{
        ModelAndView mv = new ModelAndView();
        Orders orders = orderService.findById(orderId);
        mv.addObject("orders", orders);//106
        mv.setViewName("orders-show");
        return mv;
    }
}
