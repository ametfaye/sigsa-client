package org.cages.moulinette.mailer.model;

public final class Header {

	private static final String CONTENT_TYPE = "text/HTML; charset=UTF-8";
	private static final String FORMAT = "flowed";
	private static final String ENCODING = "8bit";

	public Header() {
		//empty constructor
	}

	public String getContentType() {
		return CONTENT_TYPE;
	}

	public String getFormat() {
		return FORMAT;
	}

	public String getContentTransferEncoding() {
		return ENCODING;
	}

	@Override
	public String toString() {
		return "Header{" + "contentType='" + CONTENT_TYPE + '\'' + ", format='" + FORMAT + '\''
				+ ", contentTransferEncoding='" + ENCODING + '\'' + '}';
	}
}
