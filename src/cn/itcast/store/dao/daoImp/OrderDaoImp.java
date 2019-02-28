package cn.itcast.store.dao.daoImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.store.dao.OrderDao;
import cn.itcast.store.domain.CartItem;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.Product;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;

public class OrderDaoImp implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		qr.update(conn,sql,params);
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem item) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into orderitem values(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
		qr.update(conn,sql,params);
	}

	@Override
	public int getTotalRecords(User user) throws SQLException {
		String sql = "select count(*) from orders where uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)qr.query(sql, new ScalarHandler(), user.getUid());
		return num.intValue();
	}

	@Override
	public List findMyOrderWithPage(User user, int startIndex, int pageSize) throws Exception {
		String sql = "select * from orders where uid=? limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		//这个list里面pid所在的User为null
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),user.getUid(), startIndex, pageSize);
		
		//没玩,还要把每个订单的订单项放进去
		for (Order order : list) {
			String oid = order.getOid();
			sql = "select * from orderitem o,product p where o.pid=p.pid and oid=?";
			List<Map<String, Object>> list2 = qr.query(sql, new MapListHandler(),oid);
			//遍历list
			for (Map<String, Object> map : list2) {
				OrderItem OrderItem = new OrderItem();
				Product product = new Product();
				
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				
				//将map中属于cartItem的项自动填充到cartItem中
				BeanUtils.populate(OrderItem, map);
				BeanUtils.populate(product, map);
				
				OrderItem.setProduct(product);
				order.getList().add(OrderItem);
			}
		}
		return list;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		String sql = "select * from orders where oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		
		sql = "select * from orderitem o,product p where o.pid=p.pid and oid=?";
		List<Map<String, Object>> list2 = qr.query(sql, new MapListHandler(),oid);
		//遍历list
		for (Map<String, Object> map : list2) {
			OrderItem OrderItem = new OrderItem();
			Product product = new Product();
			
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			//将map中属于cartItem的项自动填充到cartItem中
			BeanUtils.populate(OrderItem, map);
			BeanUtils.populate(product, map);
			
			OrderItem.setProduct(product);
			order.getList().add(OrderItem);
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "UPDATE orders SET ordertime=?,total=?,state=?,address=?,name=?,telephone=? WHERE oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getOid()};
		qr.update(sql, params);
	}

}
