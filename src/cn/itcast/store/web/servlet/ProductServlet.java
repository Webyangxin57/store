package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	public String findProductByPid(HttpServletRequest req, HttpServletResponse rep) throws SQLException {
		String pid = req.getParameter("pid");
		ProductService service = new ProductServiceImp();
		Product product =  service.findProductByPid(pid);
		req.setAttribute("product", product);
		return "/jsp/product_info.jsp";
	}
	
	public String findProductWithPage(HttpServletRequest req, HttpServletResponse rep) throws SQLException {
		int num = Integer.parseInt(req.getParameter("num"));
		ProductService service = new ProductServiceImp();
		PageModel pm = service.findProductWithPage(num);
		req.setAttribute("page", pm);
	
		return "/jsp/product_list.jsp";
	}
}
