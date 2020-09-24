package com.szx.ssm.service.impl;

import com.szx.ssm.dao.IUserDao;
import com.szx.ssm.domain.Role;
import com.szx.ssm.domain.UserInfo;
import com.szx.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * userDetail的实现类
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void save(UserInfo userInfo) throws Exception {
        //对密码进行加密
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);//再拿到就是加密之后的

    }

    /**
     * 做用户登陆
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userinfo = null;
        try {
            userinfo = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*因为user实现了userDetail
        * 处理自己的用户对象封装成UserDetails
        * 将自己查询出来的userInfo封装到user中
        * 框架底层就可以通过用户名密码来看一看是否正确*/
        //User user = new User(userinfo.getUsername(), "{noop}"+userinfo.getPassword(), getAuthority(userinfo.getRoles()));
        /*public User(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
			"{noop}"+userinfo.getPassword(),*/
        User user = new User(userinfo.getUsername(),userinfo.getPassword(),(userinfo.getStatus() == 0 ? false : true),true,true,true,getAuthority(userinfo.getRoles()));
        return user;
    }
    //作用就是返回一个list集合，集合中装入的是角色的描述
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role:roles){
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        return list;
    }

    @Override
    public List<UserInfo> findAll() throws Exception {
        return userDao.findAll();
    }

    /**
     * 为了做用户详情查询
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public UserInfo findById(String id) throws Exception {
        return userDao.findById(id);
    }

    @Override
    public List<Role> findOtherRole(String userId) {
        return userDao.findOtherRoles(userId);
    }

    @Override
    public void addRoleToUser(String userId, String[] roleIds) {
        for (String roleId : roleIds) {
            userDao.addRoleToUser(userId,roleId);
        }

    }


}
