package cn.itcast.store.service.serviceImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.itcast.store.dao.OrderDao;
import cn.itcast.store.dao.daoImp.OrderDaoImp;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.utils.JDBCUtils;

public class OrderServiceImp implements OrderService {

	@Override
	public void saveOrder(Order order) throws SQLException {
		// 给dbutils传入connection对象可以使用事务
		Connection conn = null;
		
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			
			OrderDao dao = new OrderDaoImp();
			//订单和该订单下的所有商品在两张表  同时操作两张表需要开启事务
			dao.saveOrder(conn, order);
			
			for (OrderItem item : order.getList()) {
				dao.saveOrderItem(conn, item);
			}
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			conn.rollback();
			System.out.println("zhechucuo");
		}
	}

	@Override
	public PageModel findMyOrderWithPage(User user, int curNum) throws Exception {
		OrderDao dao = new OrderDaoImp();
		int totalRecords = dao.getTotalRecords(user);
		//创建Model对象 携带分页参数
		PageModel pm = new PageModel(curNum, totalRecords, 3);
		List list = dao.findMyOrderWithPage(user, pm.getStartIndex(), pm.getPageSize());
		
		pm.setList(list);
		pm.setUrl("OrderServlet?method=findMyOrderWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		OrderDao dao = new OrderDaoImp();
		Order order = dao.findOrderByOid(oid);
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		// TODO Auto-generated method stub
		OrderDao dao = new OrderDaoImp();
		dao.updateOrder(order);
	}

}
