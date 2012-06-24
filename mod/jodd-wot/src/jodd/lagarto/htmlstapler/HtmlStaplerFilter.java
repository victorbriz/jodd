// Copyright (c) 2003-2012, Jodd Team (jodd.org). All Rights Reserved.

package jodd.lagarto.htmlstapler;

import jodd.datetime.TimeUtil;
import jodd.io.StreamUtil;
import jodd.lagarto.TagVisitor;
import jodd.lagarto.TagWriter;
import jodd.lagarto.adapter.StripHtmlTagAdapter;
import jodd.lagarto.filter.SimpleLagartoServletFilter;
import jodd.log.Log;
import jodd.servlet.ServletUtil;
import jodd.util.MimeTypes;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static jodd.lagarto.htmlstapler.HtmlStaplerBundlesManager.Strategy;

/**
 * HtmlStapler filter.
 */
public class HtmlStaplerFilter extends SimpleLagartoServletFilter {

	private static final Log log = Log.getLogger(HtmlStaplerFilter.class);

	protected HtmlStaplerBundlesManager bundlesManager;

	protected boolean enabled = true;
	protected boolean stripHtml = true;
	protected boolean resetOnStart = true;
	protected Strategy staplerStrategy = Strategy.RESOURCES_ONLY;
	protected boolean useGzip;


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);

		bundlesManager = createBundleManager(filterConfig.getServletContext(), staplerStrategy);

		if (resetOnStart) {
			bundlesManager.reset();
		}
	}

	/**
	 * Creates {@link HtmlStaplerBundlesManager} instance.
	 */
	protected HtmlStaplerBundlesManager createBundleManager(ServletContext servletContext, Strategy strategy) {
		return new HtmlStaplerBundlesManager(servletContext, strategy);
	}

	@Override
	protected LagartoParsingProcessor createParsingProcessor() {
		if (enabled == false) {
			return null;
		}

		return new LagartoParsingProcessor() {
			@Override
			protected char[] parse(TagWriter rootTagWriter, HttpServletRequest request) {

				TagVisitor firstVisitor = rootTagWriter;

				if (stripHtml) {

					firstVisitor = new StripHtmlTagAdapter(rootTagWriter) {
						@Override
						public void end() {
							super.end();
							if (log.isDebugEnabled()) {
								log.debug("Stripped: " + getStrippedCharsCount() + " chars.");
							}
						}
					};
				}

				HtmlStaplerTagAdapter htmlStaplerTagAdapter =
						new HtmlStaplerTagAdapter(firstVisitor, request);

				char[] content = invokeLagarto(htmlStaplerTagAdapter);

				return htmlStaplerTagAdapter.postProcess(content);
			}
		};
	}

	@Override
	protected boolean processActionPath(HttpServletRequest servletRequest, HttpServletResponse servletResponse, String actionPath) throws IOException {

		String bundlePath = bundlesManager.getStaplerPath();

		if (actionPath.startsWith(bundlePath) == false) {
			return false;
		}

		String bundleId = actionPath.substring(bundlePath.length());

		File file = bundlesManager.lookupBundleFile(bundleId);

		if (log.isDebugEnabled()) {
			log.debug("bundle: " + bundleId);
		}

		int ndx = bundleId.lastIndexOf('.');
		String extension = bundleId.substring(ndx + 1);

		String contentType = MimeTypes.getMimeType(extension);
		servletResponse.setContentType(contentType);

		if (useGzip && ServletUtil.isGzipSupported(servletRequest)) {
			file = bundlesManager.lookupGzipBundleFile(file);

			servletResponse.setHeader("Content-Encoding", "gzip");
		}

		if (file.exists() == false) {
			throw new IOException("bundle not found: " + bundleId);
		}

		servletResponse.setHeader("Content-Length", String.valueOf(file.length()));
		servletResponse.setHeader("Last-Modified", TimeUtil.formatHttpDate(file.lastModified()));

		sendBundleFile(servletResponse, file);

		return true;
	}

	/**
	 * Outputs bundle file to the response.
	 */
	protected void sendBundleFile(HttpServletResponse resp, File bundleFile) throws IOException {
		OutputStream out = resp.getOutputStream();
		StreamUtil.copy(new FileInputStream(bundleFile), out);
	}

}