# dropwizard-redirect-bundle

A [Dropwizard](http://dropwizard.io) bundle that makes redirection a blast.

[![Build Status](https://travis-ci.org/dropwizard-bundles/dropwizard-redirect-bundle.png)](https://travis-ci.org/dropwizard-bundles/dropwizard-redirect-bundle)

## Getting Started

Just add this maven dependency:
```xml
<dependency>
  <groupId>io.dropwizard-bundles</groupId>
  <artifactId>dropwizard-redirect-bundle</artifactId>
  <version>0.8.1</version>
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

## Non-HTTPS to HTTPS Redirect

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

To redirect non-HTTPS traffic to HTTPS and redirect a path to another path:
```java
public class MyApplication extends Application<...> {
  // ...

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.addBundle(new RedirectBundle(
      new PathRedirect("/", "docs"),
      new HttpsRedirect()
    ));
  }

  // ...
}
```

## Regular Expression Redirect

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

To redirect non-HTTPS traffic to HTTPS in Dropwizard v0.9.0, use a Regular Expression Redirect:
```java
public class MyApplication extends Application<...> {
  // ...

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.addBundle(new RedirectBundle(
      new UriRedirect("http://localhost:8080(.*)$", "https://localhost:8443$1")
    ));
  }

  // ...
}
```
