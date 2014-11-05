import com.polyfx.jnphi.*;

public class Test {
	public static void main(String args[]) {
		
		JNPhi ф = new JNPhi();
		try {			
			long startTime = System.currentTimeMillis();
			System.out.println(ф.execute("Test", JNPhiReturnType.INT));
			for (int i = 0; i < 100000; ++i) {
				ф.execute("Test", JNPhiReturnType.INT);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Total execution time: " + (endTime - startTime)+ "ms");
			
			startTime = System.currentTimeMillis();
			System.out.println(ф.zero());
			for (int i = 0; i < 100000; ++i) {
				ф.zero();
			}
			endTime = System.currentTimeMillis();			
			System.out.println("Total execution time: " + (endTime - startTime)+ "ms");			
			
			startTime = System.currentTimeMillis();
			System.out.println(ф.native42());
			for (int i = 0; i < 100000; ++i) {
				ф.native42();
			}
			endTime = System.currentTimeMillis();	
			System.out.println("Total execution time: " + (endTime - startTime)+ "ms");
		} catch (Exception e) {
			// gotta' catch'em all..
			e.printStackTrace();
		}
		System.out.println("Done!");
	}
}
