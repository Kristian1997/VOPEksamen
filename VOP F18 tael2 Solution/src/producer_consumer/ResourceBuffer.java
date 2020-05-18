package producer_consumer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author erso
 */
public class ResourceBuffer {
    
    private Integer[] buf;
    
    private int putIndex;
    private int getIndex;
    
    private CallbackInterface caller;

    public ResourceBuffer(int size, CallbackInterface caller) {
        buf = new Integer[size];
        this.caller = caller;
    }

    public synchronized void put(int n) {
        while (buf[putIndex] != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        buf[putIndex] = n;
        caller.updateMessage(putIndex, n);
        //System.out.println(Thread.currentThread().getName() + " Put: " + putIndex + ": " + n);
        putIndex = (putIndex + 1) % buf.length;
        notify();
    }

    public synchronized int get() {
        while (buf[getIndex] == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        int value = buf[getIndex];
        buf[getIndex] = null;
        //System.out.println(Thread.currentThread().getName() + " Got: " + getIndex + ": " + value);
        caller.updateMessage(getIndex, null);
        getIndex = (getIndex + 1) % buf.length;
        notify();
        return value;
    }
    
    public static void main(String[] arg){
        CallbackInterface caller = new CallbackInterface(){
            @Override
            public void updateMessage(int index, Integer value) {
                System.out.println(index + " er opdateret med " + value);
            }
        };
        
        ResourceBuffer rb = new ResourceBuffer(5, caller);
        
        Producer p1 = new Producer(rb);
        Producer p2 = new Producer(rb);
        
        Consumer c1 = new Consumer(rb);
        Consumer c2 = new Consumer(rb);
        Consumer c3 = new Consumer(rb);
        
        Thread tp1 = new Thread(p1);
        tp1.setDaemon(true);
        
        Thread tp2 = new Thread(p2);
        tp2.setDaemon(true);
              
        new Thread(c1).start();
        tp1.start();
        new Thread(c2).start();
        tp2.start();
        new Thread(c3).start();       
    }
}
