package cn.itcast.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import cn.itcast.store.domain.User;

/**
 * Servlet Filter implementation class PrivileadgeFilter
 */
@WebFilter({ "/jsp/cart.jsp", "/jsp/order_info.jsp", "/jsp/order_list.jsp" })
public class PrivileadgeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PrivileadgeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 判断是否登录  未登录拦截
		HttpServletRequest req = (HttpServletRequest)request;
		User user = (User)req.getSession().getAttribute("loginUser");
		
		if(null == user) {
			req.setAttribute("msg", "请登陆后再访问!");
			req.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}else {
			chain.doFilter(request, response);
		}
		// pass the request along the filter chain
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
