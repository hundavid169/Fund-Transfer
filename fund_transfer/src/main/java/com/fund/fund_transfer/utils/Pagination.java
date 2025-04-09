package com.fund.fund_transfer.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
public class Pagination {
    private Integer page;
    private Integer size;
    private Long totalPages;
    private Long totalCounts;

    public Pagination(Integer page, Integer size, Long totalPages, Long totalCounts) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalCounts = totalCounts;
    }

    public Pagination() {
        this(1, 10, 0L, 0L);
    }

    public Long getTotalPages() {
        return (long) Math.ceil((double) this.totalCounts / size);
    }

    @JsonProperty("totalPages")
    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getOffSet() {
        return (this.page - 1) * this.size;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty("totalCounts")
    public Long getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Long totalCounts) {
        this.totalCounts = totalCounts;
    }

    @JsonIgnore
    public int getJPAPage() {
        return this.page - 1;
    }

    @JsonIgnore
    public PageRequest getJPAPageRequest() {
        return PageRequest.of(this.getJPAPage(), this.getSize());
    }

    @JsonIgnore
    public PageRequest getJPAPageRequest(Sort sort) {
        return PageRequest.of(this.getJPAPage(), this.getSize(), sort);
    }
}
