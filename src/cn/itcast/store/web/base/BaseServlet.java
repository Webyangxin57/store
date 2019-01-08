package cn.itcast.store.web.base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BaseServlet() {
        super();
    }
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	String method = req.getParameter("method");
    	System.out.println(method);
    	
    	if(method == null || "".equals(method) || method.trim().equals("")) {
    		method = "exectue";
    	}
    	
    	Class clazz = this.getClass();
    	
    	try {
			Method md = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			if(null != md) {
				String jsPath = (String)md.invoke(this, req, res);
				if(null != jsPath) {
					req.getRequestDispatcher(jsPath).forward(req, res);
				}
			}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    };
    
    public String exectue(HttpServletRequest req, HttpServletResponse res) throws SQLException {
		// TODO Auto-generated method stub
    	return null;
	}

}
