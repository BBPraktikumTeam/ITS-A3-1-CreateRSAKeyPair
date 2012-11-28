package main;

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
			//Allocating 4 Bytes for the Integer, like the example
			ByteBuffer nameLength = ByteBuffer.allocate(4);
			nameLength.asIntBuffer().put(name.length());
			ByteBuffer keyLength = ByteBuffer.allocate(4);
			keyLength.asIntBuffer().put(publicKeyBytes.length);
			// Writing Public Key into file
			FileOutputStream fos = new FileOutputStream(publicKeyFilename);
			fos.write(nameLength.array());
			fos.write(name.getBytes());
			fos.write(keyLength.array());
			fos.write(publicKeyBytes);
			fos.close();
			
			String privateKeyFilename = name + ".prv";
			
			byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();

			keyLength.asIntBuffer().put(privateKeyBytes.length);
			//Writing Private key file
			fos = new FileOutputStream(privateKeyFilename);
			fos.write(nameLength.array());
			fos.write(name.getBytes());
			fos.write(keyLength.array());
			fos.write(privateKeyBytes);
			fos.close();
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}
}
