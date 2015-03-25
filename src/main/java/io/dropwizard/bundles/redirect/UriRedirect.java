package io.dropwizard.bundles.redirect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Regular expression based redirect.  Has access to the full URI.
 */
public class UriRedirect implements Redirect {
  private final List<Entry> entries;

  /**
   * Construct a UriRedirect that maps all queries that match regex to the replacement string.
   *
   * @param regex       The regex to match against URIs.
   * @param replacement What to put in place of the regex instead. May refer to captured groups.
   */
  public UriRedirect(String regex, String replacement) {
    checkNotNull(regex);
    checkNotNull(replacement);

    entries = ImmutableList.of(new Entry(Pattern.compile(regex), replacement));
  }

  /**
   * Construct a UriRedirect that maps all queries that match against any regex to its replacement
   * string.
   *
   * @param uriMap The map from regex to replacement string.
   */
  public UriRedirect(Map<String, String> uriMap) {
    checkNotNull(uriMap);

    entries = Lists.newArrayList();
    for (Map.Entry<String, String> entry : uriMap.entrySet()) {
      String regex = entry.getKey();
      String replacement = entry.getValue();
      entries.add(new Entry(Pattern.compile(regex), replacement));
    }
  }

  @Override
  public String getRedirect(HttpServletRequest request) {
    String uri = getFullUri(request);
    for (Entry entry : entries) {
      Matcher matcher = entry.getRegex().matcher(uri);
      if (matcher.matches()) {
        return matcher.replaceAll(entry.getReplacement());
      }
    }

    return null;
  }

  private static String getFullUri(HttpServletRequest request) {
    StringBuffer requestUrl = request.getRequestURL();
    String queryString = request.getQueryString();

    if (queryString == null) {
      return requestUrl.toString();
    } else {
      return requestUrl.append('?').append(queryString).toString();
    }
  }

  private static final class Entry {
    Pattern regex;
    String replacement;

    Entry(Pattern regex, String replacement) {
      this.regex = regex;
      this.replacement = replacement;
    }

    Pattern getRegex() {
      return regex;
    }

    String getReplacement() {
      return replacement;
    }
  }
}