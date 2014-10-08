package com.jmx.test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Map;

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

public class Test {

	public static void main(String[] args) throws IOException,
			NotBoundException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			ObjectName name = new ObjectName("com.jmx.test:type=Hello");

			Hello mbean = new Hello();
			mbs.registerMBean(mbean, name);

			int rmiPort = 9999;
			String jmxServerName = "jmxrmi";
			String userName = "Administrator";
			String password = "";

			Map<String, String[]> map = new HashMap<String, String[]>();

			String[] credentials = new String[] { userName, password };
			map.put("jmx.remote.credentials", credentials);

			//   java.rmi.registry.LocateRegistry.createRegistry(iPort);
			
			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/"
							+ jmxServerName);
			JMXConnectorServer jmxConnServer = JMXConnectorServerFactory
					.newJMXConnectorServer(url, map, mbs);
			System.out.println(jmxConnServer == null);

			jmxConnServer.start();

			// Wait forever
			System.out.println("Waiting forever...");

			javax.swing.JDialog dialog = new JDialog();
			dialog.setName("jmx test");
			dialog.setVisible(true);

			Thread.sleep(Long.MAX_VALUE);

		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
