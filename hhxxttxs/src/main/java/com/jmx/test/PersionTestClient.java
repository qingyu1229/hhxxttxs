package com.jmx.test;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class PersionTestClient {


	
	
	public static void main(String[] args) throws IOException,
			MalformedObjectNameException, NullPointerException,
			AttributeNotFoundException, InstanceNotFoundException,
			MBeanException, ReflectionException, InvalidAttributeValueException {
		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
		ObjectName mbeanName = new ObjectName("com.jmx.test:type=Persion");
		// 把所有Domain都打印出来
		/*
		 * System.out.println("Domains:---------------"); String domains[] =
		 * mbsc.getDomains(); for (int i = 0; i < domains.length; i++) {
		 * System.out.println("\tDomain[" + i + "] = " + domains[i]); }
		 */
		
		Attribute attribute=new Attribute("Name", "qing");
		
		mbsc.setAttribute(mbeanName, attribute);
		
		int age= (Integer) mbsc.getAttribute(mbeanName, "Age");
		System.out.println("Age:"+age);
		System.out.println("Name = " + mbsc.getAttribute(mbeanName, "Name"));// 取值
		
	}

}
