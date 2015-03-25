package io.dropwizard.bundles.redirect;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Redirects requests coming on a specific source path to a target path.
 */
public class PathRedirect implements Redirect {
  private final Map<String, String> pathMapping;
  private final boolean keepParameters;

  public PathRedirect(String sourceUri, String targetUri) {
    this(sourceUri, targetUri, true);
  }

  /**
   * Construct a path redirect from sourceUri to targetUri.
   *
   * @param sourceUri      The source URI to watch for.
   * @param targetUri      Where to redirect requests that match the source URI.
   * @param keepParameters If true, then add the query parameters to the redirect URL.
   */
  public PathRedirect(String sourceUri, String targetUri, boolean keepParameters) {
    checkNotNull(sourceUri);
    checkNotNull(targetUri);

    pathMapping = ImmutableMap.of(sourceUri, targetUri);
    this.keepParameters = keepParameters;
  }

  public PathRedirect(Map<String, String> uriMap) {
    this(uriMap, true);
  }

  /**
   * Construct a path redirect from multiple source URIs to multiple target URIs.
   *
   * @param uriMap         The mapping from source URI's to redirected URIs.
   * @param keepParameters If true, then add the query parameters to the redirect URL.
   */
  public PathRedirect(Map<String, String> uriMap, boolean keepParameters) {
    checkNotNull(uriMap);

    pathMapping = ImmutableMap.copyOf(uriMap);
    this.keepParameters = keepParameters;
  }

  @Override
  public String getRedirect(HttpServletRequest request) {
    String uri = pathMapping.get(request.getRequestURI());
    if (uri == null) {
      return null;
    }

    StringBuilder redirect = new StringBuilder(uri);
    if (keepParameters) {
      String query = request.getQueryString();
      if (query != null) {
        redirect.append('?');
        redirect.append(query);
      }
    }

    return redirect.toString();
  }
}