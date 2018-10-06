package com.qfedu.dao;

import com.qfedu.entity.Interviewquest;

public interface IInterviewquestDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Interviewquest record);

    int insertSelective(Interviewquest record);

    Interviewquest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Interviewquest record);

    int updateByPrimaryKeyWithBLOBs(Interviewquest record);

    int updateByPrimaryKey(Interviewquest record);
}