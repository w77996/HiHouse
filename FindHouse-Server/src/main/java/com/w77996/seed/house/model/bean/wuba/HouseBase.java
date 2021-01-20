package com.w77996.seed.house.model.bean.wuba;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author w77996
 * @description
 * @date 2020/5/25 21:38
 */
//class HouseBase(scrapy.Item):
//    """ 房源信息基类. """
//
//    _id = scrapy.Field()
//    """ 数据库 ID """
//    key = scrapy.Field()
//    """ 唯一 key(source_site_type, category, source_id) """
//    source_site_type = scrapy.Field()
//    """ 来源站点类型 (See findhouse_crawler.enums.crawler_enum.SourceSiteType) """
//    source_id = scrapy.Field()
//    """ 来源 ID """
//    source_url = scrapy.Field()
//    """ 源链接 """
//    title = scrapy.Field()
//    """ 标题 """
//    category = scrapy.Field()
//    """ 房源分类 (See findhouse_crawler.enums.crawler_enum.Category) """
//    publish_source_type = scrapy.Field()
//    """ 发布来源类型 (See findhouse_crawler.enums.crawler_enum.PublishSourceType) """
//    publish_source = scrapy.Field()
//    """ 发布来源信息（个人/中介名称） """
//    publish_source_contact = scrapy.Field()
//    """ 发布来源联系方式 """
//    province_id = scrapy.Field()
//    """ 省份 ID """
//    city_id = scrapy.Field()
//    """ 城市 ID """
//    area_id = scrapy.Field()
//    """ 区域 ID """
//    business_area_id = scrapy.Field()
//    """ 商圈 ID """
//    community_id = scrapy.Field()
//    """ 小区 ID """
//    community_title = scrapy.Field()
//    """ 小区标题 """
//    community_url = scrapy.Field()
//    """ 小区 URL """
//    floor_info = scrapy.Field()
//    """ 楼层信息 """
//    orientation = scrapy.Field()
//    """ 朝向 """
//    room_amount = scrapy.Field()
//    """ 房间数量信息 """
//    size = scrapy.Field()
//    """ 面积 """
//    address = scrapy.Field()
//    """ 详细地址 """
//    pictures = scrapy.Field()
//    """ 图片链接 """
//    update_at = scrapy.Field()
//    """ 页面上的更新时间 """
//
//
//class HouseForRent(HouseBase):
//    """ 房屋出租信息. """
//
//    rental_type = scrapy.Field()
//    """ 租赁方式 """
//    rent = scrapy.Field()
//    """ 租金 """
//    rent_info = scrapy.Field()
//    """ 租金其他信息 """
@Data
public class HouseBase {

    private String _id;
    /**
     * 唯一 key(source_site_type, category, source_id)
     */
    private String key;
    /**
     * 来源站点类型 (See findhouse_crawler.enums.crawler_enum.SourceSiteType)
     */
    private String sourceSiteType;
    /**
     * 来源 ID
     */
    private String sourceId;
    /**
     * 源链接
     */
    private String sourceUrl;

    private String title;
    /**
     * 房源分类
     */
    private String category;
    /**
     * 发布来源类型
     */
    private String publishSourceType;
    /**
     * 发布来源信息（个人/中介名称）
     */
    private String publishSource;
    /**
     * 发布来源联系方式
     */
    private String publishSourceContact;
    /**
     * 省份 ID
     */
    private Integer provinceId;
    /**
     * 城市ID
     */
    private Integer cityId;
    /**
     * 区域ID
     */
    private Integer areaId;
    /**
     * 商圈ID
     */
    private Integer businessAreaId;
    /**
     * 小区ID
     */
    private Integer communityId;
    /**
     * 小区名称
     */
    private String communityTitle;
    /**
     * 小区Url
     */
    private String communityUrl;
    /**
     * 楼层
     */
    private String floorInfo;
    /**
     * 朝向
     */
    private String orientation;
    /**
     * 房间数量
     */
    private String roomAmount;
    /**
     * 面积
     */
    private String size;
    /**
     * 地址
     */
    private String address;
    /**
     * 图片
     */
    private List<String> pictures;

    private Date updateTime ;

    private Date createTime;
}

