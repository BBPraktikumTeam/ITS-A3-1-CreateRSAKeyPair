package main;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class RSAKeyCreation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "";
		if (args.length == 1) {
			name = args[0];
		} else {
			System.out.println("Bitte Nutzername als Parameter eingeben");
			System.exit(1);
		}

		//KEy Pair Generator
		KeyPairGenerator keyGenerator;
		try {
			keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(1024);
			KeyPair keyPair = keyGenerator.genKeyPair();
			String publicKeyFilename = name + ".pub";
			
			byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
		
			// Writing Public Key into file
			FileOutputStream fos = new FileOutputStream(publicKeyFilename);
			DataOutputStream out = new DataOutputStream(fos);
			out.writeInt(name.length());
			out.write(name.getBytes());
			out.writeInt(publicKeyBytes.length);
			out.write(publicKeyBytes);
			out.close();
			
			String privateKeyFilename = name + ".prv";
			
			byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
			//Writing Private key file
			fos = new FileOutputStream(privateKeyFilename);
			out = new DataOutputStream(fos);
			out.writeInt(name.length());
			out.write(name.getBytes());
			out.writeInt(privateKeyBytes.length);
			out.write(privateKeyBytes);
			out.close();
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}
}
