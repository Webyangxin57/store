package cn.itcast.store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class AdminCategoryServlet
 */
@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	//findAllCarts
	public String findAllCarts(HttpServletRequest req, HttpServletResponse res) throws SQLException {
		CategoryService service = new CategoryServiceImp();
		List<Category> list = service.getAllCats();
		req.setAttribute("allCarts", list);
    	return "/admin/category/list.jsp";
	}
	
	public String addCategoryUI(HttpServletRequest req, HttpServletResponse res) throws SQLException {
		
    	return "/admin/category/add.jsp";
	}
	
	public String addCategory(HttpServletRequest req, HttpServletResponse res) throws SQLException {
		String cname = req.getParameter("cname");
		String cid = UUIDUtils.getCode();
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		CategoryService service = new CategoryServiceImp();
		service.addCategory(category);
    	return "/store/AdminCategoryServlet?method=findAllCarts";
	}
}
