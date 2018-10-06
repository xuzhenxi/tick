package com.qfedu.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.qfedu.dao.IAuthorityDao;
import com.qfedu.dao.IRoleDao;
import com.qfedu.dao.IUserDao;
import com.qfedu.entity.User;



public class MyRealm extends AuthorizingRealm{
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IAuthorityDao auty;
	
	@Autowired
	private IRoleDao roleDao;

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取输入的用户名
		String no = (String) principals.getPrimaryPrincipal();
		
		List<String> roleList = roleDao.findRoleByNo(no);  
		List<String> permitList = auty.findPermitByNo(no);  
		
		Set<String> roleSet = new HashSet<>(roleList);
		Set<String> permitSet = new HashSet<>(permitList);
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roleSet);
		info.setStringPermissions(permitSet);
		
		return info;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		String no = (String)token.getPrincipal();
		
		User user = userDao.findByNo(no);
		
		if (user == null) {
			throw new UnknownAccountException();
		}
		String password = user.getPassword();
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(no, password, this.getName());
		
		return info;
	}

}
