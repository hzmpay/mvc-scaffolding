package com.hzm.freestyle.sharding_jdbc;

import com.hzm.freestyle.service.BaseService;
import com.hzm.freestyle.sharding.ExampleMain;
import com.hzm.freestyle.sharding.entity.Order;
import com.hzm.freestyle.sharding.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author Hezeming
 * @version 1.0
 * @date 2021年04月09日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleMain.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Slf4j
public class ShardingJdbcTest extends BaseService {

    @Resource
    private DataSource dataSource;
    @Resource
    private OrderRepository orderRepository;

    @Test
    public void insert() {
        Order order = new Order();
//        order.setOrderId();
//        orderRepository.insert()
    }
}
