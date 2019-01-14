package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImp.UserServiceImp;
import cn.itcast.store.utils.MailUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	

	public String registUI(HttpServletRequest req, HttpServletResponse rep) {
		// TODO Auto-generated method stub
		return "/jsp/register.jsp";
	}
	
	public String loginUI(HttpServletRequest req, HttpServletResponse rep) {
		// TODO Auto-generated method stub
		return "/jsp/login.jsp";
	}
	
	public String userRegister(HttpServletRequest req, HttpServletResponse rep) throws Exception {
		// TODO Auto-generated method stub
		//1,接受表单参数
		
		Map<String, String[]> map = req.getParameterMap();
		User user = new User();
		
		//已经封装好了再myBeanUtils
		/*//将时间字符串转化成时间对象
		DateConverter dt = new DateConverter();
		dt.setPattern("yyyy-mm-dd");
		ConvertUtils.register(dt, java.util.Date.class);
		//底层就是调用user的class调用set方法
		BeanUtils.populate(user, map);*/
		
		MyBeanUtils.populate(user, map);
		
		//为用户的其他属性赋值
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
	
		//遍历map中的数据
		/*
		Set<String> mapKey = map.keySet();
		Iterator<String> interator = mapKey.iterator();
		while(interator.hasNext()) {
			String str = interator.next();
			String[] strs = map.get(str);
			for (String string : strs) {
				System.out.println(string);
			}
		}*/
		
		//2，调用业务层注册功能
		UserService service = new UserServiceImp();
		try {
			service.userRegister(user);
			//注册成功
			req.setAttribute("msg", "用户注册成功,请激活!");
			
			//给用户发送激活邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
		} catch (SQLException e) {
			//注册失败
			req.setAttribute("msg", "用户注册失败,请重新注册!");

		}
		
		return "/jsp/info.jsp";
	}

	public String active(HttpServletRequest req, HttpServletResponse rep) throws SQLException {
		// TODO Auto-generated method stub
		String code = req.getParameter("code");
		UserService service = new UserServiceImp();
		boolean flag = service.userActive(code);
		if(flag) {
			//用户激活成功 转到登陆
			req.setAttribute("msg", "激活成功,请登陆");
			return "/jsp/login.jsp";
		} else {
			//激活失败  转到提示页面
			req.setAttribute("msg", "激活失败,请重新激活");
			return "/jsp/info.jsp";
		}
		
		
	}

	public String userLogin(HttpServletRequest req, HttpServletResponse rep) {
		// TODO Auto-generated method stub
		//获取用户数据
		User user = new User();
		MyBeanUtils.populate(user, req.getParameterMap());
		//调用用户层登陆功能
		UserService service = new UserServiceImp();
		User userSession = null;
		try {
			//登陆成功
			userSession = service.userLogin(user);
			req.getSession().setAttribute("loginUser", userSession);
			rep.sendRedirect("/store/index.jsp");
			return null;
		} catch (Exception e) {
			//登陆失败
			String msg = e.getMessage();
			req.setAttribute("msg",msg);
			return "/jsp/login.jsp";
		}
	}

	public String logOut(HttpServletRequest req, HttpServletResponse rep) throws IOException {
		req.getSession().invalidate();
		rep.sendRedirect("/store/jsp/index.jsp");
		return null;
	
	}
}
