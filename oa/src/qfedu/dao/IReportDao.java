package com.qfedu.dao;

import com.qfedu.entity.Report;

public interface IReportDao {
    int deleteByPrimaryKey(Long id);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKeyWithBLOBs(Report record);

    int updateByPrimaryKey(Report record);
}