package net.minidev.json.parser;

import java.io.IOException;
import java.io.Reader;

/**
 * Parser for JSON text. Please note that JSONParser is NOT thread-safe.
 * 
 * @author Uriel Chemouni <uchemouni@gmail.com>
 */
public class JSONParser extends JSONParserStream {

	protected static boolean[] stopArray = new boolean[126];
	protected static boolean[] stopKey = new boolean[126];
	protected static boolean[] stopValue = new boolean[126];
	protected static boolean[] stopX = new boolean[126];
	static {
		stopKey[':'] = true;
		stopValue[','] = stopValue['}'] = true;
		stopArray[','] = stopArray[']'] = true;
	}

	public Object parse(String in) throws IOException, ParseException {
		return parse(new FStringReader(in), ContainerFactory.FACTORY);
	}

	public Object parse(String in, ContainerFactory containerFactory) throws ParseException {
		try {
			return parse(new FStringReader(in), containerFactory);
		} catch (IOException e) {
			// cant not be throws
			return null;
		}
	}

	/**
	 * Non Syncronized limited StringReader
	 */
	private static class FStringReader extends Reader {
		private String str;
		private int length;
		private int next = 0;

		public FStringReader(String s) {
			this.str = s;
			this.length = s.length();
		}

		public int read() throws IOException {
			if (next >= length)
				return -1;
			return str.charAt(next++);
		}

		public int read(char cbuf[], int off, int len) throws IOException {
			if ((off < 0) || (off > cbuf.length) || (len < 0) || ((off + len) > cbuf.length) || ((off + len) < 0)) {
				throw new IndexOutOfBoundsException();
			} else if (len == 0) {
				return 0;
			}
			if (next >= length)
				return -1;
			int n = Math.min(length - next, len);
			str.getChars(next, next + n, cbuf, off);
			next += n;
			return n;
		}

		public boolean ready() throws IOException {
			return true;
		}

		public boolean markSupported() {
			return false;
		}

		public void close() {
		}
	}
}
