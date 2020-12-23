package it.bper.activemq;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.swing.JTextArea;

public class TopicListener implements MessageListener {
	public static Boolean TRANSACTIONAL = false;
	JTextArea textArea;

	public TopicListener(JTextArea textArea) {
		this.textArea = textArea;
	}

	public void onMessage(Message msg) {
		try {

			if (msg instanceof TextMessage) {
				TextMessage text = (TextMessage) msg;
				textArea.append("====nuovo messaggio ====" + new Date() + "\n");
				textArea.append(text.getText().toString());
				textArea.append("\n");
			} else if (msg instanceof ObjectMessage) {
				ObjectMessage objmsg = (ObjectMessage) msg;
				Object obj = objmsg.getObject();
				System.out.println(" - Consuming object msg: " + obj);
				textArea.append(obj.toString());
			} else {
				System.out.println(" - Unrecognized Message type " + msg.getClass());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}