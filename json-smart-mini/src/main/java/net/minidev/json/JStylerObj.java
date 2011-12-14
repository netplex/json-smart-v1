package net.minidev.json;

import java.io.IOException;

/**
 * protected class used to stored Internal methods
 * 
 * @author Uriel Chemouni <uchemouni@gmail.com>
 */
class JStylerObj {

	public static boolean isSpace(char c) {
		return (c == '\r' || c == '\n' || c == '\t' || c == ' ');
	}

	public static boolean isSpecialChar(char c) {
		return (c == '\b' || c == '\f' || c == '\n');
	}

	public static boolean isSpecialOpen(char c) {
		return (c == '{' || c == '[' || c == ',' || c == ':');
	}

	public static boolean isSpecialClose(char c) {
		return (c == '}' || c == ']' || c == ',' || c == ':');
	}

	public static boolean isSpecial(char c) {
		return (c == '{' || c == '[' || c == ',' || c == '}' || c == ']' || c == ':' || c == '\'' || c == '"');
	}

	public static boolean isUnicode(char c) {
		return ((c >= '\u0000' && c <= '\u001F') || (c >= '\u007F' && c <= '\u009F') || (c >= '\u2000' && c <= '\u20FF'));
	}

	public static boolean isKeyword(String s) {
		if (s.length() < 3)
			return false;
		char c = s.charAt(0);
		if (c == 'n')
			return s.equals("null");
		if (c == 't')
			return s.equals("true");
		if (c == 'f')
			return s.equals("false");
		if (c == 'N')
			return s.equals("NaN");
		return false;
	}

	/**
	 * Escape special chars form String including /
	 * 
	 * @param s
	 *            - Must not be null.
	 * @param sb
	 */
	public static void escape(String s, Appendable sb) {
		try {
			for (int i = 0; i < s.length(); i++) {
				char ch = s.charAt(i);
				switch (ch) {
				case '"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '/':
					sb.append("\\/");
					break;
				default:
					// Reference:
					// http://www.unicode.org/versions/Unicode5.1.0/
					if ((ch >= '\u0000' && ch <= '\u001F') || (ch >= '\u007F' && ch <= '\u009F')
							|| (ch >= '\u2000' && ch <= '\u20FF')) {
						sb.append("\\u");
						String hex = "0123456789ABCDEF";
						sb.append(hex.charAt(ch >> 12 & 0x0F));
						sb.append(hex.charAt(ch >> 8 & 0x0F));
						sb.append(hex.charAt(ch >> 4 & 0x0F));
						sb.append(hex.charAt(ch >> 0 & 0x0F));
					} else {
						sb.append(ch);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Impossible Error");
		}
	}
}
