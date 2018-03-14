package CondVar;

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
	final static int max = 4;
	static Object condProd = new Object();
	static Object condCons = new Object();

	// --------Producer---------//

	public static class Producer extends Thread {

		static Thread m = new Thread();
		String id = "1", price = "1";
		int namep;

		synchronized public void adds(String s1, String s2) {

			while (vList.size() == max) {

				try {
					synchronized (condProd) {
						condProd.wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			vList.add(new Lista(s1, s2));

			System.out.println("Am adaugat un item");

		}

		public void produce() {
			try {
				this.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
		}

		public Producer(int i, List<Lista> kList) throws NoSuchAlgorithmException {

			namep = i;
			vList = kList;

		}

		public void run() {

			while (true) {
				try {
					this.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				produce();

				adds(id, price);

				synchronized (condCons) {
					condCons.notifyAll();
				}

				// System.out.println(vList.size());
			}
		}

	}

	// -------------CONSUMER-------------//

	public static class Consumer extends Thread {

		String id = "1", price = "1";
		int namec;

		public void consume() {
			try {
				this.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
		}

		public Consumer(int i, List<Lista> kList) throws NoSuchAlgorithmException {
			namec = i;
			vList = kList;
		}

		synchronized public void consumer() {

			while (vList.size() == 0) {
				try {
					synchronized (condCons) {
						condCons.wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			vList.remove(0);

			System.out.println("Am scos un item");

		}

		public void run() {

			while (true) {

				try {
					this.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				consumer();

				synchronized (condProd) {
					condProd.notifyAll();
				}

				consume();

			}

		}
	}
}
