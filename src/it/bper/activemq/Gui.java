package it.bper.activemq;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Gui extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	public static void main(String[] param) {
//
//		String args[] = new String[3];
//		String[] topics = { "nicola-topic", "Fabrick_Tenant_PSD2_Movements", "Fabrick_Agata_Tenant_PSD2_Accounts",
//				"Fabrick_Agata_Tenant_PSD2_Users", "Fabrick_Agata_Tenant_PSD2_Bank_Profile",
//				"Fabrick_Agata_Tenant_PSD2_Consent", "Fabrick_Agata_Tenant_PSD2_Card" };
//
//		String[] subscribers = { "bper", "Meniga", "Fabrick", "par-tec" };
//
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				Gui.showFrame(args, topics, subscribers);
//			}
//		});
//	}

	public Gui(String[] args, String[] topics, String[] subscribers) throws JMSException {
		String text = "Listening... \n ";
        
		//setup jtextArea
		JTextArea textArea = new JTextArea(text);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);

		String appendText = "";
		textArea.append(appendText);

		this.setPreferredSize(new Dimension(500, 200));
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
        
		//init parametri per JOptionPane
		args[0] = (String) JOptionPane.showInputDialog(null, "ip-host:porta", "ip-host:porta",
				JOptionPane.QUESTION_MESSAGE, null, null, "172.16.64.237:61616");
		args[1] = (String) JOptionPane.showInputDialog(null, "id subscriber", "lista subscriber",
				JOptionPane.QUESTION_MESSAGE, null, subscribers, subscribers[1]);
		args[2] = (String) JOptionPane.showInputDialog(null, "Scegli Topic", "lista topics",
				JOptionPane.QUESTION_MESSAGE, null, topics, topics[1]);
		
		connettiSubscriber(args, textArea);
	}

	//connette il subscriber ad activeMq
	public void connettiSubscriber(String[] args, JTextArea textArea) throws JMSException {

		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + args[0]);
		Connection connection = factory.createConnection();

		connection.setClientID(args[1]);
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic destination = session.createTopic(args[2]);
		
		//crea un subscriber di tipo durable
		MessageConsumer consumer = session.createDurableSubscriber(destination, "Listener");
		
		//crea un subscriber non durable
		//MessageConsumer consumer = session.createConsumer(destination, "Listener");

		consumer.setMessageListener(new TopicListener(textArea));

	}

	public static void showFrame(String[] args, String[] topics, String[] subscribers) {
		JPanel panel;
		try {
			panel = new Gui(args, topics, subscribers);

			panel.setOpaque(true);

			JFrame frame = new JFrame(args[2]);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setContentPane(panel);
			frame.pack();
			frame.setVisible(true);
		} catch (JMSException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

}
