package com.migration_map;

import java.util.List;

public class UNHCRResponse {
    private int page;
    private String shortUrl;
    private int maxPages;
    private List<UNHCRData> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public List<UNHCRData> getItems() {
        return items;
    }

    public void setItems(List<UNHCRData> items) {
        this.items = items;
    }
}
