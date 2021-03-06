package com.zxsoft.crawler.storage;

import java.io.Serializable;
import java.util.Date;

import org.jsoup.nodes.Document;

import com.zxsoft.crawler.api.JobType;

public class WebPage implements Serializable, Cloneable {

        private static final long serialVersionUID = 6780885020548185632L;

        private String baseUrl;
        private String keyword;

        private String encode;
        
        /**
         * url所属的网站
         */
        public String website;
        private String listUrl; // 列表页URL

        private int status;
        private long fetchTime;
        private long prevFetchTime;
        private int retriesSinceFetch;
        private Document document;

        private JobType jobType;

        private String title;
        private long updateTime;
        private boolean ajax;
        private boolean auth;
        
        private Date releaseDate;
        private String author;
        private String homeUrl;
        /*
         * 通用字段
         */
        /**
         * 境内, 境外
         */
        private int region;
        private int provinceId;
        private int cityId;
        private int locationCode;
        private String location;
        private String ip;
        private int source_id;
        private String source_name;
        private int server_id;
        private int source_type;
        private int sectionId;
        private String comment;

        private ListConf ListConf;

        private int platform;

        @Override
        public WebPage clone() {
                try {
                        return (WebPage) super.clone();
                } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                }
                return null;
        }

        public WebPage() {
        }

        public WebPage(String baseUrl, boolean ajax) {
                super();
                this.baseUrl = baseUrl;
                this.ajax = ajax;
        }

        public WebPage(String keyword, String listUrl) {
                this.keyword = keyword;
                this.listUrl = listUrl;
        }

        public WebPage(String baseUrl, long fetchTime, Document document) {
                super();
                this.baseUrl = baseUrl;
                this.fetchTime = fetchTime;
                this.document = document;
        }

        public WebPage(String baseUrl, boolean ajax, long prevFetchTime) {
                super();
                this.baseUrl = baseUrl;
                this.ajax = ajax;
                this.prevFetchTime = prevFetchTime;
        }

        public WebPage(String baseUrl, String engineUrl, String keyword,int platform,  int sectionId, String comment,  int region, int provinceId, int cityId, int locationCode, String location,
                                        String ip, JobType jobType, int source_id, String source_name, int server_id, int source_type) {
                super();
                this.baseUrl = baseUrl;
                this.listUrl = engineUrl;
                this.keyword = keyword;
                this.platform = platform;
                this.sectionId = sectionId;
                this.comment = comment;
                this.region = region;
                this.provinceId = provinceId;
                this.cityId = cityId;
                this.locationCode = locationCode;
                this.location = location;
                this.ip = ip;
                this.jobType = jobType;
                this.source_id = source_id;
                this.source_name = source_name;
                this.server_id = server_id;
                this.source_type = source_type;
        }
        
        public WebPage(String baseUrl, int sectionId, String comment, long prevFetchTime, int region, int provinceId, int cityId, int locationCode, String location,
                                        String ip, JobType jobType, int source_id,String source_name, int server_id, int source_type) {
                super();
                this.baseUrl = baseUrl;
                this.sectionId = sectionId;
                this.comment = comment;
                this.prevFetchTime = prevFetchTime;
                this.region = region;
                this.provinceId = provinceId;
                this.cityId = cityId;
                this.locationCode = locationCode;
                this.location = location;
                this.ip = ip;
                this.jobType = jobType;
                this.source_id = source_id;
                this.source_name = source_name;
                this.server_id = server_id;
                this.source_type = source_type;
        }

        public WebPage(String title, String baseUrl, long fetchTime, Document document) {
                super();
                this.title = title;
                this.baseUrl = baseUrl;
                this.fetchTime = fetchTime;
                this.document = document;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public String getEncode() {
                return encode;
        }

        public void setEncode(String encode) {
                this.encode = encode;
        }

        public Date getReleaseDate() {
                return releaseDate;
        }

        public void setReleaseDate(Date releaseDate) {
                this.releaseDate = releaseDate;
        }

        public String getSource_name() {
            return source_name;
        }

        public void setSource_name(String source_name) {
            this.source_name = source_name;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public String getHomeUrl() {
                return homeUrl;
        }

        public void setHomeUrl(String homeUrl) {
                this.homeUrl = homeUrl;
        }

        public int getSource_id() {
                return source_id;
        }

        public void setSource_id(int source_id) {
                this.source_id = source_id;
        }

        public int getServer_id() {
                return server_id;
        }

        public void setServer_id(int server_id) {
                this.server_id = server_id;
        }

        public int getSource_type() {
                return source_type;
        }

        public void setSource_type(int source_type) {
                this.source_type = source_type;
        }

        public String getIp() {
                return ip;
        }

        public void setIp(String ip) {
                this.ip = ip;
        }

        public String getLocation() {
                return location;
        }

        public void setLocation(String location) {
                this.location = location;
        }

        public int getRegion() {
                return region;
        }

        public void setRegion(int region) {
                this.region = region;
        }

        public int getProvinceId() {
                return provinceId;
        }

        public void setProvinceId(int provinceId) {
                this.provinceId = provinceId;
        }

        public int getCityId() {
                return cityId;
        }

        public void setCityId(int cityId) {
                this.cityId = cityId;
        }

        public int getLocationCode() {
                return locationCode;
        }

        public void setLocationCode(int locationCode) {
                this.locationCode = locationCode;
        }

        public long getUpdateTime() {
                return updateTime;
        }

        public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
        }

        public ListConf getListConf() {
                return ListConf;
        }

        public void setListConf(ListConf listConf) {
                ListConf = listConf;
        }

        public boolean isAuth() {
                return auth;
        }

        public void setAuth(boolean auth) {
                this.auth = auth;
        }

        public String getListUrl() {
                return listUrl;
        }

        public void setListUrl(String listUrl) {
                this.listUrl = listUrl;
        }

        public String getKeyword() {
                return keyword;
        }

        public void setKeyword(String keyword) {
                this.keyword = keyword;
        }

        public JobType getJobType() {
                return jobType;
        }

        public void setJobType(JobType jobType) {
                this.jobType = jobType;
        }

        public boolean isAjax() {
                return ajax;
        }

        public void setAjax(boolean ajax) {
                this.ajax = ajax;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getBaseUrl() {
                return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
                this.baseUrl = baseUrl;
        }

        public int getStatus() {
                return status;
        }

        public void setStatus(int status) {
                this.status = status;
        }

        public int getSectionId() {
                return sectionId;
        }

        public void setSectionId(int sectionId) {
                this.sectionId = sectionId;
        }

        public String getComment() {
                return comment;
        }

        public void setComment(String comment) {
                this.comment = comment;
        }

        public long getFetchTime() {
                return fetchTime;
        }

        public void setFetchTime(long fetchTime) {
                this.fetchTime = fetchTime;
        }

        public long getPrevFetchTime() {
                return prevFetchTime;
        }

        public void setPrevFetchTime(long prevFetchTime) {
                this.prevFetchTime = prevFetchTime;
        }

        public int getRetriesSinceFetch() {
                return retriesSinceFetch;
        }

        public void setRetriesSinceFetch(int retriesSinceFetch) {
                this.retriesSinceFetch = retriesSinceFetch;
        }

        public Document getDocument() {
                return document;
        }

        public void setDocument(Document document) {
                this.document = document;
        }

}
