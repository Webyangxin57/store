package cn.itcast.store.domain;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import cn.itcast.store.utils.JDBCUtils;

public class TestBean {
	@Test
	public void findMyOrderWithPage() throws Exception {
		String sql = "select * from orders where uid=? limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),"373eb242933b4f5ca3bd43503c34668b", 0, 3);
		
		//没玩,还要把每个订单的订单项放进去
		for (Order order : list) {
			System.out.println(order.getUser() + "     ");
		}
		
	}
}
