package com.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zengzehao
 * @CreateTime: 2019-11-07 11:45
 * @Description: 事件处理中心
 */
@Component
public class EventBus implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(EventBus.class);

    private ApplicationContext applicationContext;
    private static EventBus instance;

    private Map<Class<?>, List<IEventListener>> listenerMap = new HashMap<>();

    public static EventBus getInstance() {
        return instance;
    }

    @PostConstruct
    public void init() {
        instance = this;
        initListeners();
    }

    private void initListeners() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Listener.class);
        beans.values().forEach(this::register);
    }


    public void register(Object object) {
        Method[] declaredMethods = object.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            EventAnno annotation = method.getAnnotation(EventAnno.class);
            if (annotation == null) {
                continue;
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                logger.error("The Listener Method Modifier Must be public" + method.getName());
                continue;
            }
            checkListenerMethod(method);
            Class<?> eventClass = method.getParameterTypes()[0];
            EventMethodListener eventMethodListener = new EventMethodListener(eventClass, object, method);
            addListener(eventMethodListener);
        }
    }

    private void checkListenerMethod(Method method) {
        if (method.getParameterTypes().length != 1) {
            throw new IllegalArgumentException("The method params wrong on" + method.getName()
                    + ", The method params length need equals 1");
        }
        if (!IEvent.class.isAssignableFrom(method.getParameterTypes()[0])) {
            throw new IllegalArgumentException("The method params wrong on" + method.getName()
                    + ", The method params need to subClass of IEvent");
        }
    }


    public void fire(IEvent event) {
        Class<?> clz = event.getClass();
        while (clz != Object.class && clz != null) {
            List<IEventListener> listeners = listenerMap.get(clz);
            if (listeners != null) {
                listeners.forEach(listener -> handleListener0(listener, event));
            }
            clz = clz.getSuperclass();
        }
    }

    private void handleListener0(IEventListener listener, IEvent event) {
        listener.handle(event);
    }

    private void addListener(EventMethodListener listener) {
        List<IEventListener> listeners = listenerMap.computeIfAbsent(listener.getEventClass(), a -> new ArrayList<>());
        listeners.add(listener);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
