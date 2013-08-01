package org.qty.cfutil;

import java.io.File;

public class DistributionConfiguration {

    private String domain;
    private String keyId;
    private String keyPath;

    public DistributionConfiguration(String keyId, String keyPath, String domain) {
        this.domain = domain;
        this.keyId = keyId;
        this.keyPath = keyPath;
    }

    public String getDomain() {
        return domain;
    }

    public String getKeyId() {
        return keyId;
    }

    public File getKeyPath() {
        return new File(keyPath);
    }

}
