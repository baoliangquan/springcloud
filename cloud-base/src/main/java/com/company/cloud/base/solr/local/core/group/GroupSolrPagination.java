package com.company.cloud.base.solr.local.core.group;


import com.company.cloud.base.model.Pagination;

import java.util.List;

public class GroupSolrPagination<T> extends Pagination<T> {
	private static final long serialVersionUID = 3159747442189949328L;

    public GroupSolrPagination() {
        super();
    }
    public GroupSolrPagination(int pageSize, int page) {
        super(pageSize, page);
    }
    public GroupSolrPagination(int pageSize, int page, String uri) {
        super(pageSize, page, uri);
    }

    private List<SolrGroupBean> groupFields;

    public List<SolrGroupBean> getGroupFields() {
        return groupFields;
    }

    public void setGroupFields(List<SolrGroupBean> groupFields) {
        this.groupFields = groupFields;
    }

    @Override
    public List<T> getItems() {
        throw new RuntimeException("wrong method called!!please use GroupSolrPagination.getGroupFields()");
    }

    @Override
    public int getRecordCount() {
        throw new RuntimeException("wrong method called!!please use GroupSolrPagination.getGroupFields()");
    }
}
