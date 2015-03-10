Getting Started
===============

Just add this maven dependency:
```xml
<dependency>
  <groupId>com.nefariouszhen.dropwizard</groupId>
  <artifactId>dropwizard-redirect-bundle</artifactId>
  <version>0.8.0</version>
</dependency>
```

To redirect one path to another path:
```java
public class MyApplication extends Application<...> {
  // ...

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.addBundle(new RedirectBundle(
      new PathRedirect("/old", "/new")
    ));
  }

  // ...
}
```

To redirect many paths at once:
```java
public class MyApplication extends Application<...> {
  // ...

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.addBundle(new RedirectBundle(
      new PathRedirect(ImmutableMap.<String, String>builder()
        .put("/old1", "/new1")
        .put("/old2", "/new2")
        .build())
    ));
  }

  // ...
}
```

To redirect non-HTTPS traffic to the HTTPS port:
```java
public class MyApplication extends Application<...> {
  // ...

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.addBundle(new RedirectBundle(
      new HttpsRedirect()
    ));
  }

  // ...
}
```

For more advanced users, there is also a regular expression based redirector that has access to the full URI.  This
operates in a similar fashion to the mod-rewrite module for Apache:
```java
public class MyApplication extends Application<...> {
  // ...

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.addBundle(new RedirectBundle(
      new UriRedirect("(.*)/welcome.html$", "$1/index.html")
    ));
  }

  // ...
}
```

