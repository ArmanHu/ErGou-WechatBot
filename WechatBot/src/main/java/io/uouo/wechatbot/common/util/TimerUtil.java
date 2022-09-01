package io.uouo.wechatbot.common.util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 倒计时专用工具类
 * @author ming
 *
 */
public class TimerUtil {
    @SuppressWarnings("unused")
	private int lin;
    private int curSec;
    public TimerUtil(int lin)throws InterruptedException{
        this.lin = lin;
        this.curSec = lin;
        System.out.println("最后倒计时：" + lin + "秒");
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("倒计时:" + curSec + "秒");
                --curSec;
            }
        },0,1000);
        TimeUnit.SECONDS.sleep(lin);
        t.cancel();
        System.out.println("Game over!!!");
    }
 
    public static void main(String[] args) throws InterruptedException{
        /*new TimerUtil(20);*/
    	
        /*TimerTask task=new TimerTask(){
        	@Override
        	   public void run() {
        	    System.out.println("hello");
        	   }
        };
        Timer timer=new Timer();
        timer.schedule(task, 0,5000); */ //0标识要延迟的时间，5000指毫秒

    
    }
}
