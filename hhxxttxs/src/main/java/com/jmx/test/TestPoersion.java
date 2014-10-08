package com.jmx.test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.swing.JDialog;

public class TestPoersion {

	public static void main(String[] args) throws MalformedObjectNameException,
			NullPointerException, InstanceAlreadyExistsException,
			MBeanRegistrationException, NotCompliantMBeanException,
			InterruptedException, IOException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("com.jmx.test:type=Persion");

		Persion mbean = new Persion();
		mbs.registerMBean(mbean, name);

		int rmiPort = 9999;
		String jmxServerName = "jmxrmi";

		java.rmi.registry.LocateRegistry.createRegistry(rmiPort);

		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/"
						+ jmxServerName);
		JMXConnectorServer jmxConnServer = JMXConnectorServerFactory
				.newJMXConnectorServer(url, null, mbs);
		
		jmxConnServer.start();
		
		System.out.println("Waiting ...................");
		javax.swing.JDialog dialog = new JDialog();
		dialog.setName("jmx test");
		dialog.setVisible(true);
		
		Thread.sleep(Long.MAX_VALUE);

	}

}
