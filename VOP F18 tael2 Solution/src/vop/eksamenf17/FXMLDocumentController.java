/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vop.eksamenf17;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import producer_consumer.CallbackInterface;
import producer_consumer.Consumer;
import producer_consumer.Producer;
import producer_consumer.ResourceBuffer;

/**
 *
 * @author erso
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField buffer0;
    @FXML
    private TextField buffer1;
    @FXML
    private TextField buffer2;
    @FXML
    private TextField buffer3;
    @FXML
    private TextField buffer4;
    @FXML
    private Button startProducer;
    @FXML
    private Button startConsumer;

    @FXML
    private TextField producers;
    @FXML
    private TextField consumers;

    private TextField[] bufferFields;
    private int producerCount = 0;
    private int consumerCount = 0;

    private ResourceBuffer rBuf;
    private CallbackInterface callBack = new CallbackInterface() {
        
        @Override
        public void updateMessage(int index, Integer value) {
          
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    bufferFields[index].setText(" " + value);
                }
            });
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rBuf = new ResourceBuffer(5, callBack);
        bufferFields = new TextField[]{buffer0, buffer1, buffer2, buffer3, buffer4};

    }

 
 
    @FXML
    private void producerStartHandler(ActionEvent event) {

        Thread t = new Thread(new Producer(rBuf));
        t.setDaemon(true);
        t.start();
        producerCount++;
        producers.setText(" " + producerCount);

    }

    @FXML
    private void consumerStartHandler(ActionEvent event) {

        Thread t = new Thread(new Consumer(rBuf), "Consumer " + consumerCount);
        t.setDaemon(true);
        t.start();
        consumerCount++;
        consumers.setText(" " + consumerCount);
    }

}
