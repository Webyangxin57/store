package cn.itcast.store.web.servlet;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Cart;
import cn.itcast.store.domain.CartItem;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {
	
	public String addToCart(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		
		if(cart == null) {
			cart = new Cart();
			req.getSession().setAttribute("cart", cart);
		}
		
		int num = Integer.parseInt(req.getParameter("quantity"));
		String pid = req.getParameter("pid");
		
		ProductService service = new ProductServiceImp();
		Product product = service.findProductByPid(pid);
		CartItem item = new CartItem();
		item.setNum(num);
		item.setProduct(product);
		
		cart.addToCart(item);
		res.sendRedirect("/store/jsp/cart.jsp");
		
    	return null;
	}
	
}
