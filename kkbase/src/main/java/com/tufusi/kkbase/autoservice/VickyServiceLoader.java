package com.tufusi.kkbase.autoservice;

import java.util.ServiceLoader;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description 实现我们自定义的加载某些类的服务加载器
 * @see
 */
public final class VickyServiceLoader {

    public VickyServiceLoader() {
    }

    /**
     * ServiceLoader Vs ClassLoader
     * <p>
     * ClassLoader: JVM 利用ClassLoader将类加载到内存中，这是一个类声明周期的第一步（Java类声明周期：加载、连接、初始化、使用、卸载）
     * ServiceLoader：一个简单的服务提供者加载设施类，服务 是一个通识的接口/抽象类 的集合
     * <p>
     * 通过在资源目录META-INF/services中放置提供者配置文件 来标识服务提供者。
     * <p>
     * 以延迟方式查找和实例化提供者，即按取所需。服务加载器维护目前为止已经加载的提供者缓存。每次调用 iterator 方法返回一个迭代器。
     * 它首先按照实例化顺序生成缓存的所有元素，然后以延迟方式查找和实例化所有剩余的提供者，一次、依次将每个提供者添加到缓存。可以通过 reload 方法清除缓存。
     */
    public static <S> S load(Class<S> service) {
        try {
            return ServiceLoader.load(service).iterator().next();
        } catch (Exception ex) {
            return null;
        }
    }
}
