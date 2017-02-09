package com.company.seed.solr.local.core.facet;

import com.company.seed.model.Pagination;

import java.util.List;

public class FacetSolrPagination<T> extends Pagination<T> {

    private List<SolrFacetBean> facetFields;
    public FacetSolrPagination() {
        super();
    }
    public FacetSolrPagination(int pageSize, int page) {
        super(pageSize, page);
    }
    public FacetSolrPagination(int pageSize, int page, String uri) {
        super(pageSize, page, uri);
    }

    public List<SolrFacetBean> getFacetFields() {
        return facetFields;
    }

    public void setFacetFields(List<SolrFacetBean> facetFields) {
        this.facetFields = facetFields;
    }
}
