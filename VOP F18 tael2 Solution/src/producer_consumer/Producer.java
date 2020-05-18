/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package producer_consumer;

import java.util.Random;

/**
 *
 * @author erso 
 */
public class Producer implements Runnable {
   private ResourceBuffer buf;
   
   public static Random generator = new Random();
   
   public Producer(ResourceBuffer buf) {
      this.buf = buf;      
   }

   @Override
   public void run() {
      
      while(true) {
         buf.put(generator.nextInt(90) + 10);
          try {
              Thread.sleep((long) generator.nextInt(200) + 100);
          } catch (InterruptedException ex) {
              System.out.println(ex);
          }
      }
   }
}

