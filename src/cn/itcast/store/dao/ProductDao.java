package cn.itcast.store.dao;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;

public interface ProductDao {
	List<Product> getAllNews() throws SQLException;

	List<Product> getAllHots() throws SQLException;

	Product findProductByPid(String pid) throws SQLException;

	List findProductWithPage(String cid, int num, int i) throws SQLException;

	int findTotal(String cid)  throws SQLException;
}
