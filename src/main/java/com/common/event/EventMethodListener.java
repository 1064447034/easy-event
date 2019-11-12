package com.common.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: zengzehao
 * @CreateTime: 2019-11-07 11:51
 * @Description:
 */
public class EventMethodListener implements IEventListener {

    private Class<?> eventClass;

    private Object invoker;

    private Method method;

    public EventMethodListener(Class<?> eventClass, Object invoker, Method method) {
        this.eventClass = eventClass;
        this.invoker = invoker;
        this.method = method;
    }

    @Override
    public void handle(IEvent event) {
        try {
            method.invoke(invoker, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Class<?> getEventClass() {
        return eventClass;
    }

    public void setEventClass(Class<?> eventClass) {
        this.eventClass = eventClass;
    }

    public Object getInvoker() {
        return invoker;
    }

    public void setInvoker(Object invoker) {
        this.invoker = invoker;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
