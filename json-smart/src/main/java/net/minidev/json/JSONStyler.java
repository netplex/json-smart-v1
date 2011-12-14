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
import net.minidev.json.parser.ContentHandler;
import net.minidev.json.parser.ParseException;

/**
 * This class is used to format JSon output, fot a better humain readability
 * 
 * @author Uriel Chemouni <uchemouni@gmail.com>
 */
public class JSONStyler extends JSONStyle implements ContentHandler {
	int deep = 0;
	Appendable out;
	String indent[];

	public JSONStyler(int FLAG) {
		super(FLAG);
		setIdentLevel(2);
	}

	public JSONStyler(int FLAG, int nbLevel) {
		super(FLAG);
		setIdentLevel(nbLevel);
	}

	public void setOutput(Appendable out) {
		this.out = out;
	}

	public void setIdentLevel(int nbLevel) {
		String[] indent = new String[nbLevel];
		StringBuilder sb = new StringBuilder("\n");
		for (int i = 0; i < nbLevel; i++) {
			indent[i] = sb.toString();
			sb.append(' ');
		}
		this.indent = indent;
	}

	public JSONStyler() {
		super();
	}

	public boolean indent() {
		return true;
	}

	public String getNewLine() {
		if (deep <= 0)
			return "";
		if (deep < indent.length)
			return indent[deep];
		return indent[deep - 1];
	}

	public JSONStyler getStyler() {
		return (JSONStyler) this;
	}

	@Override
	public void startJSON() throws ParseException {
	}

	@Override
	public void endJSON() throws ParseException {
	}

	@Override
	public boolean startObject() throws ParseException {
		deep++;
		return false;
	}

	@Override
	public boolean endObject() throws ParseException {
		deep--;
		return false;
	}

	@Override
	public boolean startObjectEntry(String key) throws ParseException {
		return false;
	}

	@Override
	public boolean endObjectEntry() throws ParseException {
		return false;
	}

	@Override
	public boolean startArray() throws ParseException {
		deep++;
		return false;
	}

	@Override
	public boolean endArray() throws ParseException {
		deep--;
		return false;
	}

	@Override
	public boolean primitive(Object value) throws ParseException {
		return false;
	}
}
