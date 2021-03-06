package cn.itcast.store.dao.daoImp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.domain.Category;
import cn.itcast.store.utils.JDBCUtils;

public class CategoryDaoImp implements CategoryDao {

	@Override
	public List<Category> getAllCats() throws SQLException {
		String sql = "select * from category";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Category> list = qr.query(sql, new BeanListHandler<Category>(Category.class));
		return list;
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into category values(?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql, category.getCid(), category.getCname());
	}

	@Override
	public void deleteCategory(String cid)  throws SQLException{
		String sql = "delete from category where cid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql, cid);
	}

}
