package de.hsbremen.tc.tnc.natives;

import org.junit.Test;

import de.hsbremen.tc.tnc.natives.CLibrary.UTSNAME;

public class NativesFirstTest {

//	CLibrary uts = CLibrary.INSTANCE;  Gm
	
	@Test
	public void test(){
		UTSNAME u = new UTSNAME();
		// int i = uts.uname(u);  Gm
		int i = 0;
		System.out.println("Returncode: " + i);
		
		System.out.println("Nodename: " + new String(u.nodename));
		System.out.println("Sysname: " + new String(u.sysname));
		System.out.println("Version: " + new String(u.version));
		System.out.println("Release: " + new String(u.release));
		System.out.println("Machine: " + new String(u.machine));
		System.out.println("DomainName: " + new String(u.domainname));
		
		// Gm These are commented out temporarily, they are what
		// will be obtained by sensible java calls.
		/*
		System.out.println("----------------------");
		System.out.println("os.arch: " + System.getProperty("os.arch"));
		System.out.println("os.name: " + System.getProperty("os.name"));
		System.out.println("os.version: " + System.getProperty("os.version"));
		*/

	}
}
