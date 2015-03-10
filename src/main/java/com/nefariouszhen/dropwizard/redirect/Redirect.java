package com.nefariouszhen.dropwizard.redirect;

import javax.servlet.http.HttpServletRequest;

public interface Redirect {
    /**
     * Determine where to redirect the given request.  If no redirection should take place, then {@code null} should be
     * returned.
     *
     * @param request The request to potentially redirect.
     * @return The redirected URL.
     */
    String getRedirect(HttpServletRequest request);
}
