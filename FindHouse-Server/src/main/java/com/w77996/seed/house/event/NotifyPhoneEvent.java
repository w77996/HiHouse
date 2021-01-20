package com.w77996.seed.house.event;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName NotifyPhoneEvent
 * @Description
 * @author w77996
 * @date 2020/11/14 20:53
 */
public class NotifyPhoneEvent extends ApplicationEvent {


    NotifyPhoneDto notifyPhoneDto;

    public NotifyPhoneEvent(Object source, NotifyPhoneDto notifyPhoneDto) {
        super(source);
        this.notifyPhoneDto = notifyPhoneDto;
    }


    public NotifyPhoneDto getNotifyPhoneDto() {
        return notifyPhoneDto;
    }

    public void setNotifyPhoneDto(NotifyPhoneDto notifyPhoneDto) {
        this.notifyPhoneDto = notifyPhoneDto;
    }
}