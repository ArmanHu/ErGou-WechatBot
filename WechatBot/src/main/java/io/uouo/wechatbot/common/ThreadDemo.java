package io.uouo.wechatbot.common;

public class ThreadDemo {
	int a = 0;
	boolean isWho = true;

	public synchronized void sub() {
		if (isWho) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("sub" + " " + a);
		a = a + 1;
		isWho = true;
		this.notify();
	}

	public synchronized void plus() {
		if (!isWho) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("plus" + " " + a);
		a = a + 1;
		isWho = false;
		this.notify();
	}

	public static void main(String[] args) {
		ThreadDemo demo = new ThreadDemo();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					demo.sub();
				}
			}
		});
		thread.start();
		
		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					demo.plus();
				}
			}
		});
		thread2.start();
	}
}
