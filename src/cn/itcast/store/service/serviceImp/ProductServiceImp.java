package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.dao.daoImp.ProductDaoImp;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;

public class ProductServiceImp implements ProductService {
	ProductDao dao = new ProductDaoImp();
	
	@Override
	public List<Product> getAllNews() throws SQLException {
		
		return dao.getAllNews();
	}

	@Override
	public List<Product> getAllHots() throws SQLException {
		
		return dao.getAllHots();
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		
		return dao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductWithPage(String cid, int num) throws SQLException {
		//创建pagemodel对象 计算分页参数
		//统计当前cid分类下商品个数

		int total = dao.findTotal(cid);

		PageModel pm = new PageModel(num, total, 12);

		List list = dao.findProductWithPage(pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		pm.setUrl("/ProductServlet?method=findProductWithPage&cid=" + cid);

		return pm;
		
	}

	@Override
	public PageModel findProductWithPage(int num) throws SQLException {
		//创建pagemodel对象 计算分页参数
				//统计当前cid分类下商品个数

				int total = dao.findTotal();

				PageModel pm = new PageModel(num, total, 12);

				List list = dao.findProductWithPage(pm.getStartIndex(), pm.getPageSize());
				pm.setList(list);
				pm.setUrl("/AdminProductServlet?method=findProductWithPage&");

				return pm;
	}

}
