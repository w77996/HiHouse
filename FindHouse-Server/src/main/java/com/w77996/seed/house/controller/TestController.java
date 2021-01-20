package com.w77996.seed.house.controller;

import com.w77996.seed.house.event.NotifyPhoneDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName TestController
 * @Description
 * @author w77996
 * @date 2020/11/15 13:29
 */
@Slf4j
@Api("测试相关接口")
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @GetMapping("event")
    public void event(){
        applicationEventPublisher.publishEvent(NotifyPhoneDto.builder().wubaHouseDetail(null).build());
    }

    @GetMapping("notifyWp")
    public void notifyWp(){

        WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
        massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        massMessage.setContent("消息内容");
        List<String> collect = Stream.builder().add("o_2sDt7eqLpjiec1ViEt0M70QRUc").add("o_2sDt7BpbnW19c_Oo7qQxtcidNs").build().map(o -> o.toString()).collect(Collectors.toList());
        massMessage.getToUsers().addAll(collect);

        try {
            WxMpMassSendResult massResult = wxMpService.getMassMessageService().massOpenIdsMessageSend(massMessage);
            log.info(massResult.toString());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("kefu")
    public void kefu(){

//        WxMpKefuMessage wxMpKefuMessage = WxMpKefuMessage.NEWS()
//                .toUser("otAIM6PimOuARRXXTWcloLL-XLU0")
//                .addArticle(article1)
//                //.addArticle(article2)
//                .build();
        // 发送给指定openid
        WxMpKefuMessage build = WxMpKefuMessage
                .TEXT()
                .toUser("o_2sDt7BpbnW19c_Oo7qQxtcidNs")
                .content("文版、\n 181245504040")
                .build();
        try {
            boolean b = wxMpService.getKefuService().sendKefuMessage(
                    build);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

    }



}
