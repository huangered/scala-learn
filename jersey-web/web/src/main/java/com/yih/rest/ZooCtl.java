//package com.yih.rest;
//
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.springframework.stereotype.Component;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//@Path("/api/v1")
//@Component
//public class ZooCtl {
//    CuratorFramework client;
//
//    public ZooCtl() {
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
//        client.start();
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/zoo")
//    public String zoo() {
//
//
//        //创建分布式锁, 锁空间的根节点路径为/curator/lock
//        InterProcessMutex mutex = new InterProcessMutex(client, "/tmp/zoo/lock");
//        try {
//            mutex.acquire();
//            //获得了锁, 进行业务流程
//            System.out.println("Enter mutex");
//            //完成业务流程, 释放锁
//            Thread.sleep(30*1000);
//            System.out.println("Release mutex");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                mutex.release();
//            } catch (Exception e) {
//            } finally {
//            }
//        }
//        //关闭客户端
//        //client.close();
//
//        return "true";
//    }
//}