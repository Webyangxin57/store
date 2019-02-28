package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class AdminProductServlet
 */
@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
	
	public String findProductWithPage(HttpServletRequest req, HttpServletResponse rep) throws SQLException {
		String cid = req.getParameter("cid");
		int num = Integer.parseInt(req.getParameter("num"));
		ProductService service = new ProductServiceImp();
		PageModel pm = service.findProductWithPage(num);
		req.setAttribute("page", pm);
	
		return "/admin/product/list.jsp";
	}
}
