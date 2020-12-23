package it.bper.activemq;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] param) {

		String args[] = new String[3];
		String[] topics = { "nicola-topic", "Fabrick_Tenant_PSD2_Movements", "Fabrick_Agata_Tenant_PSD2_Accounts",
				"Fabrick_Agata_Tenant_PSD2_Users", "Fabrick_Agata_Tenant_PSD2_Bank_Profile",
				"Fabrick_Agata_Tenant_PSD2_Consent", "Fabrick_Agata_Tenant_PSD2_Card" };

		String[] subscribers = { "bper", "Meniga", "Fabrick", "par-tec" };

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Gui.showFrame(args, topics, subscribers);
			}
		});
	}
}
