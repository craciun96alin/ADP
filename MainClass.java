package semaphore;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;



public class MainClass extends Thread {
	public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException {


	Thread[] thread = new Thread[4];
	List<Lista> kList = new ArrayList<Lista>();
	
	
	
	 thread[0] = new Producer(0,kList);
	 thread[0].start();
	 thread[1] = new Consumer(1,kList);
	 thread[1].start();
	 thread[2] = new Producer(2,kList);
	 thread[2].start();
	 thread[3] = new Consumer(3,kList);
	 thread[3].start();
	
	for ( int i =0; i< 4; i++)
	 {
		 try {
			 thread[i].join();
		 }
		 catch(InterruptedException e )
		 {
			 e.printStackTrace();
		 }
	 }

}
}

