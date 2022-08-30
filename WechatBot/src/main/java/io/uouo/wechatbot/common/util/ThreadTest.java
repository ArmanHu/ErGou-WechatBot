package io.uouo.wechatbot.common.util;

import java.util.Scanner;
public class ThreadTest {
    Scanner sc=new Scanner(System.in);
    int a=0;
    public static void main(String[] args) {
        new ThreadTest().show();
    }
    private void show() {
        System.out.println("请输入太上老君生日是哪一天?");
        new Thread() {
            public void run() {
                a=sc.nextInt();
            }
        }.start();
        new Thread() {
            public void run() {
                for(int i=0;i<5;) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(++i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(a!=-9000) {
                    System.out.println("太渣了!");
                     
                }else {
                    System.out.println("还行!");
                }
                System.out.println("要不要在玩一把? Y/N 来决定!");
                sc=new Scanner(System.in);
                if(sc.nextLine().equals("y")) {
                    show();
                }else {
                    return;
                }
            }
        }.start();
    }
}