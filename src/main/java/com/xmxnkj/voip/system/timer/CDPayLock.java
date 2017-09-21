package com.xmxnkj.voip.system.timer;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.springframework.stereotype.Component;

/**
 * 同步撤单同一个用户的某个模块
 * @author fxc
 *
 */
@Component
public class CDPayLock implements Lock{
	
	private  volatile Hashtable<String,String> userCode =  new Hashtable<String,String>();
	
	private  ThreadLocal<HashMap<String,String>> map;
	
	private final String NAME_KEY = "USERNAME";
	
	private final String mark = "mark";
	
	public  HashMap<String, String> getMap() {
		if(this.map == null){
			this.map = new ThreadLocal<HashMap<String,String>>();
		}
		if(this.map.get()==null)
			this.map.set(new HashMap<String,String>());
		return map.get();
	}

	public void setMap(ThreadLocal<HashMap<String, String>> map) {
		this.map = map;
	}

	public void lock() {
		// TODO Auto-generated method stub
	}

	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 同步获取锁
	 * @return
	 */
	public synchronized boolean getLock(){
		if(this.userCode.get(getMap().get(NAME_KEY)) == null){
			//设置锁
			this.userCode.put(getMap().get(NAME_KEY), Thread.currentThread().getName()+"/"+getMap().get(NAME_KEY));
				return true;
		}
		return false;
	}
	
	public boolean tryLock()  {
		//每次使用该锁前必须保证调用setUserCode方法
		if(getMap().get(this.mark).equals("false")){
			try {
				throw new Exception("没有设置用户Code");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		//更新标记
		getMap().put(this.mark,"false");
		int i=0;
		while(i++<10){
			//如果没有记录对应该用户的namekey说明是未上锁状态
			if(this.userCode.get(getMap().get(NAME_KEY)) != null){
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("阻塞中"+this.userCode.get(getMap().get(NAME_KEY)));
			}else{
				if(getLock())
					return true;
			}
		}
		System.out.println(this.userCode.get(getMap().get(NAME_KEY))+"阻塞超时!");
		return false;
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	public void unlock() {
		this.userCode.remove(getMap().get(NAME_KEY));
	}

	public Condition newCondition() {
		return null;
	}

	/**
	 * 每个线程调用锁之前必须先调用该方法 存入userName 然后留下标记
	 * @param key
	 */
	public void setUserCode(String key){
		getMap().put(NAME_KEY,key) ;
		getMap().put(this.mark,"true");
	}
	
}
