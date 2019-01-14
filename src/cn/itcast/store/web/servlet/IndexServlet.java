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
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {
	@Override
	public String exectue(HttpServletRequest req, HttpServletResponse res) throws SQLException {
		/*CategoryService service = new CategoryServiceImp();
		List<Category> list = service.getAllCats();
		req.setAttribute("allCats", list);
		return "/jsp/index.jsp";*/ //已在ajax中实现
		
		//调用service中的查询最新,最热商品
		ProductService service = new ProductServiceImp();
		List<Product> newList = service.getAllNews();
		req.setAttribute("allNews", newList);
		List<Product> hotList = service.getAllHots();
		req.setAttribute("allHots", hotList);
	
		return "/jsp/index.jsp";
	}//这样访问首页才有列表信息,要想每个页面都有得各个地方写这个方法  不如ajax
}
