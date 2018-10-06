package com.qfedu.entity;

import java.util.ArrayList;
import java.util.List;

public class Role {
    private Integer id;
    private String info;
    private String name;
    private Integer parentid;
    
    //父级角色
    private Role role;
    //使用该色的用户
    private List<User> user;
    
    
    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	private List<Authority> auty;
    //用于保存auty中所有的权限id
    private List<Integer> aids;
    

    public List<Authority> getAuty() {
		return auty;
	}

	public void setAuty(List<Authority> auty) {
		this.auty = auty;
	}

	public List<Integer> getAids() {
		return aids;
	}

	public void setAids(List<Integer> aids) {
		this.aids = aids;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
}