package com.company.seed.dao.basic.mybatis;

import com.company.seed.model.Pagination;

import java.util.List;

/**
 * DAO基类，封装了常用方法
 * Created by yoara on 2015/12/21.
 */
public interface BaseDao {
	/**
	 * 扩展MyBatis的分页查询
	 * @param statement 查询数据的sql
	 * @param statmentCount 查询总数的sql，可能会造成严重的性能瓶颈，建议新写一条，若确认没有性能问题，也可复用statement
	 * @param parameter 传参
     */
	<T> Pagination<T> selectList(String statement,String statmentCount, Object parameter, Pagination<T> page);
	/**
	 * 查询结果集总数
	 */
	int selectCount(String statement, Object parameter);
	/**
	 * 查询一个结果
	 */
	public Object selectOne(String statement, Object parameter);
	/**
	 * 更新操作
	 */
	int update(String statement, Object parameter);
	/**
	 * 删除操作
	 */
	int delete(String statement, Object parameter);
	/**
	 * 新增操作
	 */
	int insert(String statement, Object parameter);
	/**
	 * 查询结果集
	 */
	List<?> selectList(String statement);
	/**
	 * 查询结果集
	 */
	List<?> selectList(String statement, Object parameter);
	/**
	 * 查询结果集
	 */
	<T> List<T> selectList(String statement, Object parameter, int pageSize, int pageIndex);
	/**
	 * 执行sql
	 */
	void executeSql(String sql);
}
