import com.polyfx.jnphi.*;

public class Test {
	public static void main(String args[]) {
		
		JNPhi ф = new JNPhi();
		try {
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < 10000000; ++i) {
				ф.execute(new byte[] {});
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Total execution time: " + (endTime - startTime)+ "ms");
			
			startTime = System.currentTimeMillis();
			for (int i = 0; i < 10000000; ++i) {
				ф.zero();
			}
			endTime = System.currentTimeMillis();			
			System.out.println("Total execution time: " + (endTime - startTime)+ "ms");			
			
			startTime = System.currentTimeMillis();
			for (int i = 0; i < 10000000; ++i) {
				ф.jz();
			}
			endTime = System.currentTimeMillis();	
			System.out.println("Total execution time: " + (endTime - startTime)+ "ms");
		} catch (JNPhiException | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Done!");
	}
}
