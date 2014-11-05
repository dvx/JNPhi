package com.polyfx.jnphi;

import java.lang.reflect.InvocationTargetException;

public class JNPhi {
	
	static {
		System.loadLibrary("jnphi-jni");
	}

	private ExecutableAccessor accessor = new ExecutableAccessor();
	private Object retObj = null;
	private Thread watcher;

	private native void watch();
	public native Integer zero();
	
	/* This is still here for historical reasons
	 * 
	private void testWatch() throws InterruptedException {
		while (true) {
			synchronized(this) {
				while (accessor.consumed()) {
					wait();
				}
				// do work
				retObj = accessor.getIdx();
				accessor.clearBlock();
				this.notifyAll();
			}
		}
	}
	*/

	public JNPhi() {
		(watcher = new Thread() {
			@Override
			public void run() {
				watch();
			}
		}).start();
	}
	
	public Object execute(byte[] code) throws JNPhiException, InterruptedException {
		synchronized(this) {
			if (!accessor.consumed()) {
				throw new JNPhiException("Block " + accessor.getIdx() + " not consumed yet.");
			} else {
				accessor.setBlock(code);
				this.notifyAll();
				return ret();
			}
		}
	}

	private Object ret() throws InterruptedException {
		synchronized (this) {
			while (!accessor.consumed()) {
				wait();
			}
		}
		return retObj;
	}

	public void unload() {
		watcher.interrupt();
	}
	
	public Integer jz() throws Exception {
	     //return Integer.class.getConstructor(int.class).newInstance(0);
		return new Integer(0);
	}
}