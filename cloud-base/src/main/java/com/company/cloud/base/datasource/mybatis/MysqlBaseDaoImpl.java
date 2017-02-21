package com.company.cloud.base.datasource.mybatis;

import com.company.cloud.base.datasource.MysqlBaseDao;
import com.company.cloud.base.datasource.mybatis.interceptor.PaginationInterceptor;
import com.company.cloud.base.model.Pagination;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Dao基类，扩展MyBatis功能，或实现一些通用的数据操作
 * Created by yoara on 2015/12/21.
 */
@Repository("mysqlBaseDao")
public class MysqlBaseDaoImpl extends SqlSessionDaoSupport implements MysqlBaseDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name="mysqlSqlSessionFactory")
	protected SqlSessionFactory mysqlSqlSessionFactory;

	@Resource(name="mysqlJdbcTemplate")
	protected JdbcTemplate jdbcTemplate;

	@Resource(name="mysqlSqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory mysqlSqlSessionFactory) {
		super.setSqlSessionFactory(mysqlSqlSessionFactory);
	}
	/**
	 * 支持批量操作
	 * 
	 * @param sqlSessionCallback
	 * @return
	 */
	public Object execute(SqlSessionCallback sqlSessionCallback) {
		SqlSession sqlSession = SqlSessionUtils.getSqlSession(
				mysqlSqlSessionFactory, ExecutorType.BATCH,
				new MyBatisExceptionTranslator(mysqlSqlSessionFactory
						.getConfiguration().getEnvironment().getDataSource(),true));
		try {
			return sqlSessionCallback.doInSqlSession(sqlSession);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			SqlSessionUtils.closeSqlSession(sqlSession, mysqlSqlSessionFactory);
		}
	}


	@Override
	public <T> Pagination<T> selectList(String statement, String statmentCount, Object parameter,
										Pagination<T> page) {
		if (page == null) {
			page = new Pagination<>();
		}
		if (page.isQueryRecordCount()) {
			int count = selectCount(statement, parameter);
			page.setRecordCount(count);
		}
		List<T> items = getSqlSession().selectList(
				statement,
				parameter,
				new RowBounds(page.getCurrentPageStartIndex(), page
						.getPageSize()));
		page.setItems(items);
		return page;
	}

	@Override
	public int selectCount(String statement, Object parameter) {
		return (Integer) getSqlSession().selectOne(statement,
				new PaginationInterceptor.CountParameter(parameter));
	}

	@Override
	public int update(String statement, Object parameter) {
		return proxy(statement, parameter, 3);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return proxy(statement, parameter, 2);
	}

	@Override
	public List<?> selectList(String statement) {
		return getSqlSession().selectList(statement, null);
	}

	@Override
	public int insert(String statement, Object parameter) {
		return proxy(statement, parameter, 1);
	}

	@Override
	public <T> List<T> selectList(String statement, Object parameter,
			int pageSize, int pageIndex) {
		List<T> items = getSqlSession().selectList(statement, parameter,
				new RowBounds((pageIndex - 1) * pageSize, pageSize));
		return items;
	}

	@Override
	public List<?> selectList(String statement, Object parameter) {
		return getSqlSession().selectList(statement, parameter);
	}

	@Override
	public void executeSql(String sql) {
		jdbcTemplate.execute(sql);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private int proxy(String statement, Object parameter, int type) {
		int result = 0;
		try {
			switch (type) {
			case 1:
				result = getSqlSession().insert(statement, parameter);
				break;
			case 2:
				result = getSqlSession().delete(statement, parameter);
				break;
			case 3:
				result = getSqlSession().update(statement, parameter);
				break;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/** 批量导入 **/
	public void batchInsert(final List<Map<String, Object>> list,
			final String statement) {
		execute(sqlSession -> {
            for (Map<String, Object> map : list) {
                insert(statement, map);
            }
            return sqlSession;
        });
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object selectOne(String statement, Object parameter) {
		List result = selectList(statement, parameter);
		if(result!=null&&result.size()>0){
			if(result.size()>1){
				logger.warn("more then 1 result:"+statement);
			}
			return result.get(0);
		}
		return null;
	}
}
