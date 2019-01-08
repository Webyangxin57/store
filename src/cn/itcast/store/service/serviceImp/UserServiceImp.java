package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.daoImp.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;

public class UserServiceImp implements UserService{

	@Override
	public void userRegister(User user) throws SQLException {
		// TODO Auto-generated method stub
		//实现注册功能
		UserDao userDao = new UserDaoImp();
		userDao.userRegister(user);
	}

	@Override
	public boolean userActive(String code) throws SQLException {
		// TODO Auto-generated method stub
		UserDao userDao = new UserDaoImp();
		User user = userDao.userActive(code);
		if(user == null) {
			//没查到code 不能激活
			return false;
		}else {
			//可以激活 需要改状态 删code 更新user
			user.setState(1);
			user.setCode(null);
			userDao.updateUser(user);
			return true;
		}
		
	}

	@Override
	public User userLogin(User user) throws SQLException {
		//利用异常于模块之间传递数据
		UserDao dao = new UserDaoImp();
		User uu = dao.userLogin(user);
		if(uu == null) {
			throw new RuntimeException("用户名或密码不正确!");
		} else if(uu.getState() == 0){
			throw new RuntimeException("账户未激活");
		} else {
			return uu;
		}
	}
}
