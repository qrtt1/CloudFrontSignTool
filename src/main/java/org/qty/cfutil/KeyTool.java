package org.qty.cfutil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

public class KeyTool {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static PrivateKey getPrivateKey(File privateKey) throws IOException {

        FileReader fileReader = new FileReader(privateKey);
        PEMReader r = new PEMReader(fileReader);
        try {
            return ((KeyPair) r.readObject()).getPrivate();
        } catch (IOException ex) {
            throw new IOException("The private key could not be decrypted", ex);
        } finally {
            r.close();
            fileReader.close();
        }
    }

    public static String signAndSafeBase64(File privateKey, String data) throws Exception {
        System.err.println(data);
        Signature signature = Signature.getInstance("SHA1withRSA", "BC");
        signature.initSign(getPrivateKey(privateKey));
        signature.update(data.getBytes("UTF-8"));
        return Base64.encodeBase64String(signature.sign())
                .replace('+', '-')
                .replace('=', '_')
                .replace('/', '~');
    }

}
