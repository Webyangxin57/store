package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	
	public String findAllCats(HttpServletRequest req, HttpServletResponse rep) throws SQLException, IOException {
		//在Jedis中获取全部分类信息
		Jedis Jedis = JedisUtils.getJedis();
		String jsonStr = Jedis.get("allCats");
		if(null == jsonStr || "".equals(jsonStr)) {
			CategoryService service = new CategoryServiceImp();
			List<Category> list = service.getAllCats();
			jsonStr = JSONArray.fromObject(list).toString();
			Jedis.set("allCats", jsonStr);
			System.out.println("redis中没有缓存数据");
		} else {
			System.out.println("redis中有缓存数据");
		}
		
		JedisUtils.closeJedis(Jedis);
		
		rep.setContentType("application/json;charset=utf-8");
		rep.getWriter().print(jsonStr);
		return null;
	}
}
