package com.common.event.listener;

import com.common.event.EventAnno;
import com.common.event.Listener;
import com.common.event.impl.TestEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: zengzehao
 * @CreateTime: 2019-11-07 12:23
 * @Description:
 */
@Listener
@Component
public class TestListener {

    @EventAnno
    public void testEvent(TestEvent event) {
        int id = event.getId();
        System.out.println("+++++++++" + id);
    }

    @EventAnno
    private void testEvent1(TestEvent event) {
        int id = event.getId();
        System.out.println("testEvent1" + id);
    }
}
