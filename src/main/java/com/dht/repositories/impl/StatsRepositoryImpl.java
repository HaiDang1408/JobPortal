///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.dht.repositories.impl;
//
//import com.dht.hibernatedemov2.HibernateUtils;
//import com.dht.pojo.OrderDetail;
//import com.dht.pojo.Product;
//import com.dht.pojo.SaleOrder;
//import jakarta.persistence.Query;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Join;
//import jakarta.persistence.criteria.JoinType;
//import jakarta.persistence.criteria.Root;
//import java.sql.Types;
//import java.util.List;
//import org.hibernate.Session;
//
///**
// *
// * @author huu-thanhduong
// */
//public class StatsRepositoryImpl {
//    public List<Object[]> statsrevenueByProduct() {
//        try (Session s = HibernateUtils.getFactory().openSession()) {
//            CriteriaBuilder b = s.getCriteriaBuilder();
//            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
//            
//            Root root = q.from(OrderDetail.class);
//            Join<OrderDetail, Product> join = root.join("productId", JoinType.RIGHT);
//            
//            q.multiselect(join.get("id"), join.get("name"),
//                    b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));
//            q.groupBy(join.get("id"));
//            
//            Query query = s.createQuery(q);
//            return query.getResultList();
//        }
//    }
//    
//    public List<Object[]> statsRevenueByTime(String time, int year) {
//        try (Session s = HibernateUtils.getFactory().openSession()) {
//            CriteriaBuilder b = s.getCriteriaBuilder();
//            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
//            
//            Root root = q.from(OrderDetail.class);
//            Join<OrderDetail, SaleOrder> join = root.join("orderId", JoinType.INNER);
//            
//            q.multiselect(b.function(time, Integer.class, join.get("createdDate")), 
//                    b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));
//            
//            q.where(b.equal(b.function("YEAR", Integer.class, join.get("createdDate")), year));
//            
//            q.groupBy(b.function(time, Integer.class, join.get("createdDate")));
//            
//            Query query = s.createQuery(q);
//            return query.getResultList();
//        }
//    }
//}
