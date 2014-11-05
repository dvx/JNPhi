package com.polyfx.jnphi;

/**
 * @author David Titarenco
 *
 */
public class JNPhi {

	static {
		System.loadLibrary("jnphi-jni");
	}	
	
	private static int idx = -1;
	
	// some constants
	private static final int NO_RETURN = 0;
	private static final int VOID = 1;
	private static final int BREAK = 99;
		
	// possible return fields
	private boolean retBoolean;
	private byte retByte;
	private char retChar;
	private short retShort;
	private int retInt;
	private long retLong;
	private float retFloat;
	private double retDouble;
	private Object retObject;
	
	/*
	 * retType: the condition variable for the wait() call.
	 * This means that we wait until retType != NO_RETURN
	 * ----------
	 * 0: NO_RETURN
	 * 1: void
	 * 2: boolean
	 * 3: byte
	 * 4: char
	 * 5: short
	 * 6: int
	 * 7: long
	 * 8: float
	 * 9: double
	 * 10: object
	 * 99: BREAK
	 */	
	private int retType = NO_RETURN;
	private JNPhiReturnType JNPhiRetType = JNPhiReturnType.NO_RETURN;
	
	private Thread watcher;

	private native void watch();
	public native Object zero();
	
	/*
	 * This is here for historical reasons
	 * 
	 * */
	private void testWatch() throws InterruptedException {
		while (true) {
			synchronized (this) {
				while (consumed()) {
					wait();
				}
				// do work
				retInt = 42;
				reset();
				this.notifyAll();
			}
		}
	}
	
	public JNPhi() {
		(watcher = new Thread() {
			@Override
			public void run() {
				watch();
			}
		}).start();
	}

	public Object execute(String funcName, JNPhiReturnType type) throws JNPhiException, InterruptedException {
		synchronized (this) {
			if (!consumed()) {
				throw new JNPhiException("Block " + getIdx() + " not consumed yet.");
			} else {
				prepare(funcName, type);
				this.notifyAll();
				while (!consumed()) {
					wait();
				}
				switch (JNPhiRetType) {
				case BOOLEAN:
					return retBoolean;
				case BYTE:
					return retByte;
				case CHAR:
					return retChar;
				case DOUBLE:
					return retDouble;
				case FLOAT:
					return retFloat;
				case INT:
					return retInt;
				case LONG:
					return retLong;
				case OBJECT:
					return retObject;
				case SHORT:
					return retShort;
				case NO_RETURN:
				case VOID:
				default:
					return null;
				}
			}
		}
	}

	private void prepare(String funcName, JNPhiReturnType type) {
		++JNPhi.idx;
		JNPhiRetType = type;
		retType = type.ordinal();
	}
	
	public boolean consumed() {
		return (retType == 0 || retType == 99);
	}
	
	public int getIdx() {
		return JNPhi.idx;
	}		

	public void reset() {
		retType = 0;
	}

	public void unload() {
		watcher.interrupt();
	}
	
	public int native42() {
		return 42;
	}
}