package cn.itcast.store.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;

public interface ProductService {
	List<Product> getAllNews() throws SQLException;

	List<Product> getAllHots()  throws SQLException;

	Product findProductByPid(String pid)  throws SQLException;

	PageModel findProductWithPage(String cid, int num) throws SQLException;

	PageModel findProductWithPage(int num) throws SQLException;
}
