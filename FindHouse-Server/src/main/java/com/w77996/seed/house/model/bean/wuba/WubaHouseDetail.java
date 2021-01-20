package com.w77996.seed.house.model.bean.wuba;

/**
 * @ClassName WubaHouseDetail
 * @Description TODO
 * @author w77996
 * @date 2020/7/11 21:35
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * agent : {"jobinfopic":"","distance":0,"lon":114.09433,"authenticCompany":false,"facePic":"https://pic1.ajkimg.com/display/anjuke/647f209cee3372d5f1c4192ff614abbe/571x571x0x21/100x133.jpg","ldtCity":false,"trueName":"刘健","businesslicensepic":"https://pic3.58cdn.com.cn/nowater/crmfangnowatermark/n_v2a7d2ef3867a547539d491df4fa6a948c.jpg","company":"深圳市乐有家房产交易有限公司","lat":22.566532,"area":"福田","brokerId":7311463,"business":"新洲","star":6,"jobinfoCode":"","userName":"1552529068_wkq","userId":62237752584966,"businesslicenseCode":"914403006837904509","areaId":0,"phone":"15170533367","cateId":"12","encPhone":"","swUser":true}
 * code : 0
 * infodetail : {"infoId":42589445619356,"localname":"sz","fushu":"","chanquan":"商品房70年","pano":false,"shipin":true,"fittype":"豪华装修","lon":114.046773,"wosVideo":true,"title":"绿景物业花园式小区，车位一比一，仰望平安大厦步行即可到达","louxing":"暂无信息","objectType":"普通住宅","tuijianInfoList":[{"brokerId":0,"infoId":42697394831369,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic4.58cdn.com.cn/anjuke_58/26bb5dca6afa8384037a819362dc27d6?w=240&h=180&crop=1","towards":"南","title":"高楼层前排看海景 视野景观好 南向通风采光优 ...","price":"888","huxing":"3室2厅","anxuan":true,"tag":[],"mianji":"121.96㎡"},{"brokerId":0,"infoId":42697305648387,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic5.58cdn.com.cn/anjuke_58/34c722c4c20da571dc3ae42eddd9413e?w=240&h=180&crop=1","towards":"南","title":"2室2厅 电梯房 绿景蓝湾半岛海景房 诚心出售...","price":"628","huxing":"2室2厅","anxuan":true,"tag":[],"mianji":"81.63㎡"},{"brokerId":0,"infoId":42697149563167,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic3.58cdn.com.cn/anjuke_58/e6e581109628939b6998ba2983b74207?w=240&h=180&crop=1","towards":"南","title":"朝南高楼层看海景 绿景蓝湾半岛 满五 楼层好 ...","price":"620","huxing":"2室2厅","anxuan":true,"tag":[],"mianji":"82.03㎡"},{"brokerId":0,"infoId":42697137306758,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic7.58cdn.com.cn/anjuke_58/317bb0512c495be5656bd9d48c7f904c?w=240&h=180&crop=1","towards":"南","title":"笋盘出炉 俯览整个红树林！蓝湾半岛居家3房 社...","price":"1400","huxing":"5室2厅","anxuan":true,"tag":[],"mianji":"161.93㎡"},{"brokerId":0,"infoId":42454159491093,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic6.58cdn.com.cn/anjuke_58/cf32330ae7c60d207f9879d09db8cfe8?w=240&h=180&crop=1","towards":"南北","title":"蓝湾半岛新上实用三房！看一线无遮海景！低税红五...","price":"889","huxing":"3室2厅","anxuan":true,"tag":[],"mianji":"121.82㎡"},{"brokerId":0,"infoId":42387971745931,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic5.58cdn.com.cn/anjuke_58/712c152b83636d9b506bc75a8a15b366?w=240&h=180&crop=1","towards":"南北","title":"蓝湾半岛经典双龙抱珠户型！业主回香港发展急卖资...","price":"1088","huxing":"3室2厅","anxuan":true,"tag":[],"mianji":"121.96㎡"},{"brokerId":0,"infoId":42354070084377,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic1.58cdn.com.cn/anjuke_58/117c5e2f358c81b33d830913c46ed235?w=240&h=180&crop=1","towards":"东南","title":"蓝湾半岛 十大美丽家园 车位11 花园小区 一...","price":"660","huxing":"2室2厅","anxuan":true,"tag":["满五唯一"],"mianji":"81.52㎡"},{"brokerId":0,"infoId":42244859488388,"pano":true,"distanceMap":{},"shipin":true,"pic":"https://pic5.58cdn.com.cn/anjuke_58/cbfab34c6e944e847e479b04e578f6c3?w=240&h=180&crop=1","towards":"西北","title":"大花园社区，红树海景海天一色。","price":"880","huxing":"3室2厅","anxuan":true,"tag":["满五唯一"],"mianji":"108.6㎡"},{"brokerId":0,"infoId":42190708567710,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic1.58cdn.com.cn/anjuke_58/53a6f4feab606eef43f8551677c4cdb1?w=240&h=180&crop=1","towards":"南","title":"家中俯瞰深圳湾一线海景和春笋灯光秀！九年制名校...","price":"616","huxing":"2室2厅","anxuan":true,"tag":["满五唯一"],"mianji":"82.05㎡"},{"brokerId":0,"infoId":42190390818852,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic1.58cdn.com.cn/anjuke_58/5c700557df5635e6d8d4442dd13eecd4?w=240&h=180&crop=1","towards":"南","title":"深圳十大美丽家园！业主急卖资产回香港发展！全新...","price":"612","huxing":"2室2厅","anxuan":true,"tag":["满五唯一"],"mianji":"82.03㎡"}],"biz":true,"gongnuan":"","price":"890","huxing":"4室2厅2卫","ditie":["距7号线沙尾站577m","距7号线上沙站1029m"],"yongjin":"≤2%","anxuan":true,"tag":[],"floor":"高层/32层","pics":["//pic1.58cdn.com.cn/anjuke_58/9185caac22c32969a92672f201a2d125?w=750&h=562&crop=1","//pic2.58cdn.com.cn/anjuke_58/76858563d22fc86e9f0e7d9a6656a0ba?w=750&h=562&crop=1","//pic3.58cdn.com.cn/anjuke_58/e1a7d61703b74263a2f9fdfbcba02f08?w=750&h=562&crop=1","//pic4.58cdn.com.cn/anjuke_58/e84a559d963c28f3c5e933cc15453100?w=750&h=562&crop=1","//pic5.58cdn.com.cn/anjuke_58/28c09d804a9ee3962c814a06e012ab29?w=750&h=562&crop=1","//pic6.58cdn.com.cn/anjuke_58/22c2f971a0b2328b5abf23d9f4e52243?w=750&h=562&crop=1","//pic7.58cdn.com.cn/anjuke_58/5688bd084f542405a31bd412622f1928?w=750&h=562&crop=1","//pic8.58cdn.com.cn/anjuke_58/32a4c2b4d63bfc9e3c513f744649683c?w=750&h=562&crop=1","//pic1.58cdn.com.cn/anjuke_58/c4b9f5e195b517f28ed8b53f0594a9b6?w=750&h=562&crop=1","//pic2.58cdn.com.cn/anjuke_58/ec4b8bc2f829ebe57ef1b09af8909d64?w=750&h=562&crop=1","//pic3.58cdn.com.cn/anjuke_58/8e6ce90e63ac8ee0e23003bf3efb1756?w=750&h=562&crop=1","//pic4.58cdn.com.cn/anjuke_58/0ddf9b5a7bdc065596e2eadf7c25b625?w=750&h=562&crop=1","//pic5.58cdn.com.cn/anjuke_58/39683ff0fac3eeecf97811b2db3677cd?w=750&h=562&crop=1","//pic6.58cdn.com.cn/anjuke_58/9fe1db380af6d8aa263547e0c16425df?w=750&h=562&crop=1","//pic7.58cdn.com.cn/anjuke_58/447d3a814bd1fb522aaf12c2a63c33e4?w=750&h=562&crop=1","//pic8.58cdn.com.cn/anjuke_58/6a17155ead200225e41332ca94437744?w=750&h=562&crop=1","//pic1.58cdn.com.cn/anjuke_58/d69d8a79c21a1350615dfeaa1913a859?w=750&h=562&crop=1","//pic2.58cdn.com.cn/anjuke_58/80f4ea0e42d9b630af8dae0c22734d61?w=750&h=562&crop=1","//pic3.58cdn.com.cn/anjuke_58/3a9e24143121a9a197e9ffa8197d0503?w=750&h=562&crop=1","//pic4.58cdn.com.cn/anjuke_58/3a21f95f9c946bc04f0b3a380d0200e6?w=750&h=562&crop=1","//pic5.58cdn.com.cn/anjuke_58/14e880c5dd8db6e79891d16d81e1d494?w=750&h=562&crop=1","//pic6.58cdn.com.cn/anjuke_58/d72b5d9de0d70a85dd4b6c9c1990bf49?w=750&h=562&crop=1"],"lat":22.521942,"housedetail":[{"tit":"核心卖点","des":"【房源优势】绿景蓝湾半岛位于福田新洲沙尾福荣路，是品质比较高的小区，是非常好适合居家自住的小区花园，靠近红树林自然生态公园，毗邻高尔夫球场，地段核心优势，生活出行非常方便！【业主产权】满五税费非常低。"},{"tit":"业主心态","des":"房东置换，诚心出售，欢迎看房，随时恭候您的到来。"},{"tit":"服务介绍","des":"本人是乐有家纯大学生团队的刘健，乐有家书面承诺不吃差价、不做私单、不收红包、不威胁恐吓业主客户的公司，真服务，真保障！价格阳光透明，服务简单真诚！差价无理由双倍赔偿!我们将用专业，真诚的服务，让您满意而归！如果这套房子您不满意，欢迎您致电，一定会让您满意而归！让您找到温馨舒适，满意的家。为您，真诚服务到永远！"}],"area":"福田","xiaoquDitu":"http://api.map.baidu.com/staticimage?center=114.046773,22.521942&markers=114.046773,22.521942&labels=114.046773,22.522441999999998&labelStyles=绿景蓝湾半岛,1,14,0x000000,0xffffff,1&width=640&height=220&zoom=16&copyright=1","xiaoquName":"绿景蓝湾半岛","business":"沙嘴","cityid":"4","jianzhuniandai":"2003年","xiaoquId":21108,"userId":62237752584966,"toward":"南","videourl":"https://wosmedia1.anjukestatic.com/UcSrQpOnMUW/esftransform/077582ee-f63a-4054-8793-01e57eca49df.1591154088104.51544-3053076103.mp4","shipinType":1,"postDate":"3小时前","dianti":"无电梯","mianji":"121.96㎡","danjia":"72974元/㎡"}
 * collected : false
 * message : 成功
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "db_wuba_house")
public class WubaHouseDetail {

    @Id
    private String id;
    /**
     * 房源ID
     */
    private String infoId;
    private String pic;
    /**
     * 用户名称
     */
    private String trueName;
    /**
     * 手机号码
     */
    String phone;
    /**
     * 产权
     */
    String chanquan;
    /**
     * 装修类型
     */
    String fittype;
    /**
     *
     */
    String louxing;
    /**
     * 实体类型：普通住宅
     */
    String objectType;
    /**
     * 标题
     */
    String title;
    /**
     * 单价
     */
    private String price;
    /**
     * 户型3室1厅1卫
     */
    private String huxing;
    /**
     * 楼层
     */
    private String floor;
    /**
     * 图片
     */
    private List<String> images;
    /**
     * 地区：伊宁市
     */
    private String area;
    /**
     * 小区名称
     */
    private String xiaoquName;
    /**
     * 城区：伊宁城区
     */
    private String business;
    /**
     * 城市ID
     */
    private String cityid;
    /**
     * 朝向
     */
    private String toward;

    /**
     * 无电梯
     */
    private String dianti;

    /**
     * 面积
     */
    private String mainji;
    /**
     * 单价
     */
    private String danjia;

    private String sourceUrl;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<WubaHouseMiniAppDetailResult.InfodetailBean.HousedetailBean> houseDetailDesc;


//    @Data
//    public static class HousedetailBean {
//        /**
//         * tit : 核心卖点
//         * des : 【房源优势】绿景蓝湾半岛位于福田新洲沙尾福荣路，是品质比较高的小区，是非常好适合居家自住的小区花园，靠近红树林自然生态公园，毗邻高尔夫球场，地段核心优势，生活出行非常方便！【业主产权】满五税费非常低。
//         */
//
//        private String tit;
//        private String des;
//
//        public String getTit() {
//            return tit;
//        }
//
//        public void setTit(String tit) {
//            this.tit = tit;
//        }
//
//        public String getDes() {
//            return des;
//        }
//
//        public void setDes(String des) {
//            this.des = des;
//        }
//    }

}
