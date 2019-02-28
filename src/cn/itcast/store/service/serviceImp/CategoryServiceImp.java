package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.dao.daoImp.CategoryDaoImp;
import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImp implements CategoryService {

	@Override
	public List<Category> getAllCats() throws SQLException {
		CategoryDao dao = new CategoryDaoImp();
		
		return dao.getAllCats();
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		// TODO Auto-generated method stub
		CategoryDao dao = new CategoryDaoImp();
		dao.addCategory(category);
		//清空jedis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

	@Override
	public void deleteCategory(String cid) throws SQLException {
		CategoryDao dao = new CategoryDaoImp();
		dao.deleteCategory(cid);
		//清空jedis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);		
	}

}
