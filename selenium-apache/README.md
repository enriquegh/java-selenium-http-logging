# selenium-apache

Apache uses System properties to define a class that logs the requests.
You can see more about this [here](https://hc.apache.org/httpcomponents-client-4.5.x/logging.html).

Main thing here is to add this to your test:

```java
System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");
```

Nothing else is needed and this will enable full wire + context logging
