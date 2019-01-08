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
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	
	public String findAllCats(HttpServletRequest req, HttpServletResponse rep) throws SQLException, IOException {
		// TODO Auto-generated method stub
		CategoryService service = new CategoryServiceImp();
		List<Category> list = service.getAllCats();
		String str = JSONArray.fromObject(list).toString();
		System.out.println(str);
		rep.setContentType("application/json;charset=utf-8");
		rep.getWriter().print(str);
		return null;
	}
}
