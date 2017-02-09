package com.company.seed.module.solr.user.local.searcher;

import com.company.seed.module.model.user.UserModel;
import com.company.seed.solr.local.AbstractSearcher;

/**
 * Created by yoara on 2016/3/14.
 */
public class UserBaseSearcher extends AbstractSearcher<UserModel>{
    public final static String ID = "id";
    public final static String NAME = "name";
}
