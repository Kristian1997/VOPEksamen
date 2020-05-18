/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package producer_consumer;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author erso 
 */
public class Consumer implements Runnable {
    
//    public static final int[]PRIMES 
//            = {11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };

    private ResourceBuffer buf;
    private Set<Integer> primeSet;

    public Consumer(ResourceBuffer buf) {
        this.buf = buf;
        this.primeSet = new TreeSet<>();
    }

    @Override
    public void run() {
        while (primeSet.size() < 5) {
            int i = buf.get();
            if (isPrime(i)) {
                primeSet.add(i);
            }
            try {
                Thread.sleep((long) (Math.random() * 200)+100);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
        System.out.println(new Date().toString() + ": " + Thread.currentThread().getName()
                + " has primes: " + primeSet);
    }

    private synchronized boolean isPrime(int x) {
        int sqrt = (int) Math.sqrt(x) + 1;
        for (int i = 2; i < sqrt; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
