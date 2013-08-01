We write a simple tool to generate a signed url for CloudFront.

```java

String domain = "[your_cloudfront_domain]";
String keyId = "[the id of your cloudfront keypair]";

/* private key file which downloaded from aws console without any format convertion */
String keyPath = "[the private key of your cloudfront keypair]";

DistributionConfiguration configuration = new DistributionConfiguration(keyId, keyPath, domain);
CloudFrontSignedUrlGenerator generator = new CloudFrontSignedUrlGenerator();

/* make the signed url */
String signedUrl = generator.signCannedPolicy(configuration, "some.resource.mp4", 60 * 1000);
```

