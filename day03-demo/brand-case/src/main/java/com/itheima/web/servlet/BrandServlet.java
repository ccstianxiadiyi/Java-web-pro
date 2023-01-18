package com.itheima.web.servlet;

import com.alibaba.fastjson.JSON;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.service.impl.BrandServiceimpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet {
    private BrandService brandService=new BrandServiceimpl();

    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Brand> brands = brandService.selectAll();
        String s = JSON.toJSONString(brands);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);
    }
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String s = br.readLine();
        Brand brand= JSON.parseObject(s, Brand.class);
        brandService.add(brand);
        response.getWriter().write("success");
    }
    public void selectByIdServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Brand brand = new Brand();
        brand.setId(Integer.valueOf(request.getParameter("id")));
        Brand brand1 = brandService.selectById(brand);
        String s = JSON.toJSONString(brand1);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);
    }
    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String s = br.readLine();
        Brand brand= JSON.parseObject(s, Brand.class);
        brandService.edit(brand);
        response.getWriter().write("success");
    }
    public void deleteOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Brand brand = new Brand();
        brand.setId(Integer.valueOf(request.getParameter("id")));
        brandService.delete(brand);
        PrintWriter writer = response.getWriter();
        writer.write("success");

    }
    public void deleteMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        int[] ints = JSON.parseObject(s, int[].class);
        brandService.deleteMore(ints);
        response.getWriter().write("success");
    }
    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageBean<Brand> pageBean = brandService.selectByPage(Integer.valueOf(request.getParameter("currentPage")), Integer.valueOf(request.getParameter("pageSize")));
        String s = JSON.toJSONString(pageBean);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);
    }
    public void selectByConditions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageSize = request.getParameter("pageSize");
        String currentPage = request.getParameter("currentPage");
        BufferedReader reader = request.getReader();
        String s1 = reader.readLine();
        Brand brand = JSON.parseObject(s1, Brand.class);
        PageBean<Brand> pageBean = brandService.selectByConditions(Integer.parseInt(pageSize), Integer.parseInt(currentPage), brand);
        String s = JSON.toJSONString(pageBean);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);
    }



}
