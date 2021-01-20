package com.w77996.seed.house.controller;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.w77996.seed.house.core.annotation.Limiter;
import com.w77996.seed.house.core.result.Result;
import com.w77996.seed.house.core.result.ResultGenerator;
import com.w77996.seed.house.model.bean.wuba.WubaHouseDetail;
import com.w77996.seed.house.model.bean.wuba.WubaHouseMiniAppDetailResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author w77996
 * @description 爬虫爬取信息后调用此方法推送
 * @date 2020/5/17 18:00
 */
@Api("爬虫通知接口")
@RestController
@RequestMapping("/crawler")
@Slf4j
public class CrawlerNotifyController {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * {"agent":{"distance":0,"lon":0.0,"authenticCompany":false,"facePic":"https://pic1.58cdn.com.cn/m1/bigimage/n_v23c2f01c3dfdc404483158818e480cf64.jpg","ldtCity":false,"trueName":"姜浩","company":"","lat":0.0,"brokerId":-1,"star":-1.0,"userName":"acf6h53hp","userId":54933702258708,"areaId":0,"phone":"","cateId":"12","encPhone":"","swUser":false},"code":0,"infodetail":{"infoId":40564441532311,"localname":"yili","fushu":"","chanquan":"商品房70年","pano":false,"shipin":false,"fittype":"精装修","lon":81.393862,"title":"多层二楼 领包入住","louxing":"暂无信息","objectType":"普通住宅","tuijianInfoList":[{"brokerId":0,"infoId":42930129575696,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic4.58cdn.com.cn/anjuke_58/1b044794ff8d894d57228a33bb7a59e0?w=240&h=180&crop=1","towards":"南北","title":"城东西雅图，环境优美","price":"46","huxing":"3室2厅","xiaoqu":"西雅图","anxuan":true,"tag":[],"mianji":"107㎡"},{"brokerId":0,"infoId":42778049054483,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic5.58cdn.com.cn/anjuke_58/b0eeb7ab0520a558608686b0299e8ac9?w=240&h=180&crop=1","towards":"南北","title":"30小18中东城旁西雅图","price":"45.5","huxing":"3室2厅","xiaoqu":"西雅图","anxuan":true,"tag":[],"mianji":"107.74㎡"},{"brokerId":0,"infoId":42855435121169,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic7.58cdn.com.cn/anjuke_58/7f479cc1c8d3bfb9e801353982bc4754?w=240&h=180&crop=1","towards":"南北","title":"低首付东城旁 西雅图 南北通透  精装修 3室...","price":"45.5","huxing":"3室2厅","xiaoqu":"西雅图","anxuan":true,"tag":[],"mianji":"107㎡"},{"brokerId":0,"infoId":43562770416005,"pano":false,"distanceMap":{},"shipin":false,"pic":"https://pic3.58cdn.com.cn/anjuke_58/f046df13dcf4f768ba18e5b0be753953?w=240&h=180&crop=1","towards":"南北","title":"首付八3室2厅 南北通透 西雅图 6加七 42...","price":"42","huxing":"3室2厅","xiaoqu":"西雅图","anxuan":true,"tag":["满五唯一"],"mianji":"107㎡"},{"brokerId":0,"infoId":43516467997085,"pano":false,"distanceMap":{},"shipin":false,"pic":"https://pic2.58cdn.com.cn/anjuke_58/f22f2e226ee6967e25ffb0627123972b?w=240&h=180&crop=1","towards":"南北","title":"东城花园 西雅图 精装五楼三室双卫 30小18...","price":"42.5","huxing":"3室2厅","xiaoqu":"西雅图","anxuan":true,"tag":[],"mianji":"121㎡"},{"brokerId":0,"infoId":43393654727681,"pano":false,"distanceMap":{},"shipin":false,"pic":"https://pic2.58cdn.com.cn/anjuke_58/7075d6d1855d6fa45b615a31b81620df?w=1080&h=1440?w=240&h=180&crop=1","towards":"南","title":"西雅图3室2厅2卫","price":"62","huxing":"3室2厅","xiaoqu":"西雅图","anxuan":true,"tag":[],"mianji":"121.78㎡"},{"brokerId":0,"infoId":42501911046308,"pano":false,"distanceMap":{},"shipin":false,"pic":"https://pic1.58cdn.com.cn/anjuke_58/33bb7fec0fb69211cf79eb31eb1e2307?w=240&h=180&crop=1","towards":"南北","title":"30小18中旁中间楼层大两室，有钥匙随时看房。...","price":"36","huxing":"2室2厅","xiaoqu":"西雅图","anxuan":true,"tag":[],"mianji":"88㎡"},{"brokerId":0,"infoId":41893240691360,"pano":false,"distanceMap":{},"shipin":false,"pic":"https://pic2.58cdn.com.cn/anjuke_58/0d8884f6a82f641b46c577ca3f61da00?w=240&h=180&crop=1","towards":"南北","title":"东城11区  户型好 三室两厅明卫  地暖","price":"32.5","huxing":"3室2厅","xiaoqu":"西雅图","anxuan":true,"tag":[],"mianji":"93㎡"},{"brokerId":0,"infoId":42491125311647,"pano":false,"distanceMap":{},"shipin":false,"pic":"https://pic6.58cdn.com.cn/anjuke_58/27e7a3836ae23f29ce56a3439845b1da?w=240&h=180&crop=1","towards":"南北","title":"东城旁 钻2楼 三室两厅 低首付 明卫侧厨 随...","price":"43","huxing":"3室2厅","xiaoqu":"西雅图","anxuan":true,"tag":[],"mianji":"100㎡"},{"brokerId":0,"infoId":42908041052433,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic3.58cdn.com.cn/anjuke_58/7e97172fbe4ed8a290dbe8118fa70cfe?w=240&h=180&crop=1","towards":"南北","title":"8万首付 东城4楼精装大三室 拎包入住 随时看房","price":"45","huxing":"3室2厅","xiaoqu":"西雅图","anxuan":false,"tag":[],"mianji":"107㎡"},{"brokerId":0,"infoId":42918674991129,"pano":false,"distanceMap":{},"shipin":true,"pic":"https://pic1.58cdn.com.cn/anjuke_58/0e04d58e683ccf3317d20dfde7404a19?w=240&h=180&crop=1","towards":"南北","title":"2万首付 东城旁边简装大两室 随时看房 价位谈...","price":"25.8","huxing":"2室2厅","xiaoqu":"西雅图","anxuan":false,"tag":[],"mianji":"95㎡"}],"biz":false,"gongnuan":"自供暖","price":"50","huxing":"3室2厅1卫","ditie":[],"yongjin":"","anxuan":false,"tag":[],"floor":"中层/6层","pics":["//pic7.58cdn.com.cn/mobile/big/n_v2b74a145c37c8462cb986ec9d9b59755c.jpg?w=750&h=562&crop=1","//pic8.58cdn.com.cn/mobile/big/n_v29258722c13574de8941a8e8a558a1441.jpg?w=750&h=562&crop=1","//pic1.58cdn.com.cn/mobile/big/n_v29878fe1c884b4a6bbd8a4e1c1fada80f.jpg?w=750&h=562&crop=1","//pic2.58cdn.com.cn/mobile/big/n_v23b320a75626248dd90a71957f53b5afe.jpg?w=750&h=562&crop=1","//pic3.58cdn.com.cn/mobile/big/n_v2c6903dd4e8eb4bdd8b6d1c029318dc6b.jpg?w=750&h=562&crop=1","//pic4.58cdn.com.cn/mobile/big/n_v25794b9bb1ba94053bfceed5353009fd0.jpg?w=750&h=562&crop=1","//pic5.58cdn.com.cn/mobile/big/n_v232456bea6a3f45ff941c5b67078102c6.jpg?w=750&h=562&crop=1","//pic6.58cdn.com.cn/mobile/big/n_v289985307d5e94ed4838b6af32d9ded4b.jpg?w=750&h=562&crop=1","//pic7.58cdn.com.cn/mobile/big/n_v27f2965d494bb4da28a051e28dd17f0bf.jpg?w=750&h=562&crop=1"],"lat":43.926888,"housedetail":[{"tit":"核心卖点","des":"周边新华医院、州技校、月亮湾建材市场、城东市场、城东客运站、8中、18中学 、30小、伊顿幼儿园等。东城区现有市郊区域房产性价比低。"}],"area":"伊宁县","xiaoquDitu":"http://api.map.baidu.com/staticimage?center=81.393862,43.926888&markers=81.393862,43.926888&labels=81.393862,43.927388&labelStyles=西雅图,1,14,0x000000,0xffffff,1&width=640&height=220&zoom=16&copyright=1","xiaoquName":"西雅图","business":"伊宁县城区","cityid":"9472","jianzhuniandai":"2000年","xiaoquId":2539600,"userId":54933702258708,"toward":"南北","shipinType":0,"postDate":"20.07.25","dianti":"无电梯","mianji":"108.89㎡","danjia":"4591元/㎡"},"collected":false,"message":"成功"}
     * @param id
     * @return
     */
    @ApiOperation(value = "通知")
    @GetMapping("/notify")
    @Limiter(perSecond = 1.0, timeOut = 3000)
    public Result notify(@RequestParam("id") String id) {
        log.info(id);

        saveDetail(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "限制")
    @GetMapping("/limit")
    @Limiter(perSecond = 1.0, timeOut = 1000)
    public Result limit() {
        log.info("limit");
        return ResultGenerator.genSuccessResult();
    }

    @Async
    public void saveDetail(String infoId){
        String time;
        time = System.currentTimeMillis() + "";
        log.info(time);
        String key = DigestUtil.sha1Hex("2f5c8229_tdc=" + time + "&infoid=" + infoId + "&openid=oIArb4lWR54EaFYrh8eZrbNd34P0");
        log.info(key);
        Map<String, Object> param = Maps.newHashMap();
        param.put("infoid", infoId);
        param.put("_tdc", time);
        param.put("openid", "oIArb4lWR54EaFYrh8eZrbNd34P0");
        param.put("_gxm", key);

        String body = HttpRequest.get("https://miniappfang.58.com/api/infodetail")
                .header("charset", "utf-8")
                .header("referer", "https://servicewechat.com/wxe5b752fbe3874df1/73/page-frame.html")
                .header("content-type", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; SM-G955F Build/JLS36C; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/74.0.3729.136 Mobile Safari/537.36 MicroMessenger/7.0.9.1560(0x27000933) Process/appbrand2 NetType/WIFI Language/zh_CN ABI/arm32")
                .header("Host", "miniappfang.58.com")
                .header("Connection", "Keep-Alive")
                .form(param)
                .execute().body();
        if (StringUtils.isEmpty(body)) {
            log.error("错误！！！！！ id{}", infoId);
            return;
//            return ResultGenerator.genFailResult(infoId);
        }
        log.info(body);
        try {
            WubaHouseMiniAppDetailResult wubaHouseDetailResult = JSONObject.parseObject(body, WubaHouseMiniAppDetailResult.class);
            WubaHouseDetail build = WubaHouseDetail.builder()
                    .infoId(wubaHouseDetailResult.getInfodetail().getInfoId()+"")
                    .trueName(wubaHouseDetailResult.getAgent().getTrueName())
                    .phone(wubaHouseDetailResult.getAgent().getPhone())
                    .chanquan(wubaHouseDetailResult.getInfodetail().getChanquan())
                    .fittype(wubaHouseDetailResult.getInfodetail().getFittype())
                    .louxing(wubaHouseDetailResult.getInfodetail().getLouxing())
                    .objectType(wubaHouseDetailResult.getInfodetail().getObjectType())
                    .title(wubaHouseDetailResult.getInfodetail().getTitle())
                    .price(wubaHouseDetailResult.getInfodetail().getPrice())
                    .huxing(wubaHouseDetailResult.getInfodetail().getHuxing())
                    .images(wubaHouseDetailResult.getInfodetail().getPics())
                    .area(wubaHouseDetailResult.getInfodetail().getArea())
                    .xiaoquName(wubaHouseDetailResult.getInfodetail().getXiaoquName())
                    .business(wubaHouseDetailResult.getInfodetail().getBusiness())
                    .cityid(wubaHouseDetailResult.getInfodetail().getCityid())
                    .danjia(wubaHouseDetailResult.getInfodetail().getDanjia())
                    .dianti(wubaHouseDetailResult.getInfodetail().getDianti())
                    .floor(wubaHouseDetailResult.getInfodetail().getFloor())
                    .houseDetailDesc(wubaHouseDetailResult.getInfodetail().getHousedetail())
                    .mainji(wubaHouseDetailResult.getInfodetail().getMianji())
                    .toward(wubaHouseDetailResult.getInfodetail().getToward())
                    .createTime(new Date())
                    .build();
            log.info(wubaHouseDetailResult.toString());
            mongoTemplate.insert(build);
            String url = "https://oapi.dingtalk.com/robot/send?access_token=d6d8d0bb4cea60f93dccb47b6fb37f42f09060a153e6ee45f7d77090567cccec";
            Map<String,Object> params = Maps.newHashMap();
            Map<String,String> text = Maps.newHashMap();
            text.put("content",build.toString());
            params.put("msgtype","text");
            params.put("text",text);
            HttpResponse execute = HttpUtil.createPost(url).body(JSON.toJSONString(params)).execute();
            log.info(execute.body());
        } catch (Exception e) {
            log.error("JSON解析错误！！！！！ id{}", infoId);
        }
    }

    public static void main(String[] args) {
//        String url = "https://oapi.dingtalk.com/robot/send?access_token=d6d8d0bb4cea60f93dccb47b6fb37f42f09060a153e6ee45f7d77090567cccec";
//        Map<String,Object> params = Maps.newHashMap();
//        Map<String,Object> text = Maps.newHashMap();
//        text.put("content","content");
//        params.put("msgtype","text");
//        params.put("text",text);
////        String post = HttpUtil.post(url, params);
//        HttpResponse execute = HttpUtil.createPost(url).body(JSON.toJSONString(params)).execute();
//        System.out.println(execute.body());
        long time = System.currentTimeMillis();
        String key = DigestUtil.sha1Hex("2f5c8229_tdc=" + time + "&infoid=" + "1702055150150665" + "&openid=oIArb4lWR54EaFYrh8eZrbNd34P0");
        log.info(key);
        Map<String, Object> param = Maps.newHashMap();
        param.put("infoid", "1702055150150665");
        param.put("_tdc", time);
        param.put("openid", "oIArb4lWR54EaFYrh8eZrbNd34P0");
        param.put("_gxm", key);

        String body = HttpRequest.get("https://miniappfang.58.com/api/infodetail")
                .header("charset", "utf-8")
                .header("referer", "https://servicewechat.com/wxe5b752fbe3874df1/73/page-frame.html")
                .header("content-type", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; SM-G955F Build/JLS36C; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/74.0.3729.136 Mobile Safari/537.36 MicroMessenger/7.0.9.1560(0x27000933) Process/appbrand2 NetType/WIFI Language/zh_CN ABI/arm32")
                .header("Host", "miniappfang.58.com")
                .header("Connection", "Keep-Alive")
                .form(param)
                .execute().body();
        System.out.println(body);
    }


}
