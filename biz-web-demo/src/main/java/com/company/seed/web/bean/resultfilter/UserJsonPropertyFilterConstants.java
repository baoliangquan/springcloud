package com.company.seed.web.bean.resultfilter;

import com.company.seed.module.model.user.UserModel;
import com.company.seed.basic.web.bean.resultfilter.JsonPropertyFilterConstants;
import com.company.seed.basic.web.bean.resultfilter.MultipleClassPropertyPreFilter;

/**
 * Created by yoara on 2016/3/2.
 */
public class UserJsonPropertyFilterConstants extends JsonPropertyFilterConstants {
	public static final String[] USER_LIST_PROPERTY = new String[]{"name"};
	
	/************************************************************/
	public static final MultipleClassPropertyPreFilter USER_FILTER = new MultipleClassPropertyPreFilter();
	private static final String[] USER_DETAIL_USER = new String[]{"name"};
	static {
		USER_FILTER.putInclude(UserModel.class, USER_DETAIL_USER);
	}
}
