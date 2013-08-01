package org.qty.cfutil;


public class CloudFrontSignedUrlGenerator {

    public final static String CANNED_POLICY = "{\"Statement\":[{\"Resource\":\"%s\",\"Condition\":{\"DateLessThan\":{\"AWS:EpochTime\":%d}}}]}";
    
    public String signCannedPolicy(DistributionConfiguration configuration, String s3key, long keepInSeconds) {
        final String url = makeResouce(configuration, s3key);
        final long timestamp = makeTimestamp(keepInSeconds);
        String policy = makeCannedPolicy(url, timestamp);
        
        try {
            String sig = KeyTool.signAndSafeBase64(configuration.getKeyPath(), policy);
            return buildUrl(configuration, url, timestamp, sig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    protected String buildUrl(DistributionConfiguration configuration, final String url, final long timestamp,
            String sig) {
        String signedUrl = url
                + (url.indexOf('?') >= 0 ? "&" : "?")
                + "Expires=" + timestamp
                + "&Signature=" + sig
                + "&Key-Pair-Id=" + configuration.getKeyId();
        return signedUrl;
    }

    private String makeResouce(DistributionConfiguration configuration, String s3key) {
        return String.format("http://%s/%s", configuration.getDomain(), s3key);
    }

    private String makeCannedPolicy(String url, long timestamp) {
        return String.format(CANNED_POLICY, url, timestamp);
    }

    private long makeTimestamp(long keepInSeconds) {
        return keepInSeconds + (System.currentTimeMillis() / 1000);
    }
}
