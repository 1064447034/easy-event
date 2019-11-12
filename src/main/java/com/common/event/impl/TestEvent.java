package com.common.event.impl;

import com.common.event.IEvent;

/**
 * @Author: zengzehao
 * @CreateTime: 2019-11-07 12:24
 * @Description:
 */
public class TestEvent implements IEvent {

    private int id;

    public TestEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
