package semaphore;

import java.io.BufferedReader;
import java.util.concurrent.*;
import java.io.InputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;



public class Thread extends java.lang.Thread {
	static List<Lista> vList;
	final static int max=4;
	static Semaphore semFree = new Semaphore(max);
	static Semaphore semFull = new Semaphore(0);
	
//--------Producer---------//
	
public static class Producer extends Thread {
	
    
	String id="1",price="1";
	int namep;

    
    public synchronized void adds(String s1, String s2)
    {
    	if(vList.size()<max)
    		
    		
    	{
    		semFull.release();	
    //		System.out.println(semFull.availablePermits());
    		
    	vList.add(new Lista(s1,s2));
    	
    	System.out.println("Am adaugat un item");
    	
    	
    	}
    	
    	
    	
    }
    
    public void produce()
    {
	    try {
			this.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
    }
    
    public Producer(int i,List<Lista> kList) throws NoSuchAlgorithmException {

    	namep=i;
    	vList=kList;
    	
    }
    
   public void run() 
   {
	  
	   while(true)
	   {
			   try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
			produce();
			
			try {
				semFree.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    adds(id,price);
		   // System.out.println(vList.size());
	   	}
    }
    	
  }
  
  //-------------CONSUMER-------------//




public static class Consumer extends Thread {
	
	String id="1",price="1";
    int namec;
    public void consume()
    {
	    try {
			this.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
    }
    
    public Consumer(int i,List<Lista> kList ) throws NoSuchAlgorithmException {
    	namec=i;
    	vList=kList;    	
    }
    
    synchronized public void critical()
    {
		if(vList.size() > 0)
		{
//			System.out.println(semFull.availablePermits());
			
			try {
				semFull.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			vList.remove(0);
			
		    System.out.println("Am scos un item" );
		    
		    semFree.release();
  
		}
    }
    
   public void run() {
	  
	   while(true)
	   {
		   
			   try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
			   	critical();
		    		
		    	consume();
	    	}
	   
	   
    	}
    }
}
