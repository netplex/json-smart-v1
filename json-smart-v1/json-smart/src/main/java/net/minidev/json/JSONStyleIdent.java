package net.minidev.json;

/*
 *    Copyright 2011 JSON-SMART authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.IOException;

/**
 * This class is used to format JSon output, fot a better humain readability
 * 
 * @author Uriel Chemouni <uchemouni@gmail.com>
 */
public class JSONStyleIdent extends JSONStyle {
	char identChar = ' ';
	String newline = "\n";
	int deep = 0;

	public JSONStyleIdent(int FLAG) {
		super(FLAG);
	}

	public JSONStyleIdent() {
		super();
	}

	private void ident(Appendable out) throws IOException {
		out.append(newline);
		for (int i = 0; i < deep; i++)
			out.append(this.identChar);
	}

	/**
	 * begin Object
	 */
	public void objectStart(Appendable out) throws IOException {
		out.append('{');
		deep++;
		ident(out);
	}

	/**
	 * terminate Object
	 */
	public void objectStop(Appendable out) throws IOException {
		deep--;
		ident(out);
		out.append('}');
	}

	/**
	 * Start the first Obeject element
	 */
	public void objectFirstStart(Appendable out) throws IOException {
	}

	/**
	 * Start a new Object element
	 */
	public void objectNext(Appendable out) throws IOException {
		out.append(',');
		ident(out);
	}

	/**
	 * End Of Object element
	 */
	public void objectElmStop(Appendable out) throws IOException {
	}

	/**
	 * end of Key in json Object
	 */
	public void objectEndOfKey(Appendable out) throws IOException {
		out.append(':');
	}

	/**
	 * Array start
	 */
	public void arrayStart(Appendable out) throws IOException {
		out.append('[');
		deep++;
		ident(out);
	}

	/**
	 * Array Done
	 */
	public void arrayStop(Appendable out) throws IOException {
		deep--;
		ident(out);
		out.append(']');
	}

	/**
	 * Start the first Array element
	 */
	public void arrayfirstObject(Appendable out) throws IOException {
	}

	/**
	 * Start a new Array element
	 */
	public void arrayNextElm(Appendable out) throws IOException {
		out.append(',');
		ident(out);
	}

	/**
	 * End of an Array element
	 */
	public void arrayObjectEnd(Appendable out) throws IOException {
	}

}
