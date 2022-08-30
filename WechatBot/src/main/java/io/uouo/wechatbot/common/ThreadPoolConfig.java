package io.uouo.wechatbot.common;

import java.util.concurrent.*;

public class ThreadPoolConfig {

    public static ExecutorService gamePool = Executors.newCachedThreadPool();

}
