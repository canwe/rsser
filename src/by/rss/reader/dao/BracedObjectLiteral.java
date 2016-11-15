/*
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package by.rss.reader.dao;

import java.util.Collection;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class BracedObjectLiteral
{
	private StringBuffer buffer = new StringBuffer("(");
	private boolean first = true;
	
	public BracedObjectLiteral(Collection<?> collection) {
		if (collection != null) {
			for (Object obj : collection) {
				this.add(obj);
			}
		}
	}
	
    public BracedObjectLiteral() {
		
	}
	
	public BracedObjectLiteral addString(String value) {
		return add("\"" + value + "\"");
	}

	public BracedObjectLiteral add(Object value) {
		if (!first) {
			buffer.append(", ");
		}
		if (value.getClass().isAssignableFrom(Key.class)) {
			Key key = (Key) value;
			buffer.append("\"" + KeyFactory.keyToString(key) + "\"");
		} else {
		    buffer.append(value);
		}
		
		first = false;
		return this;
	}

	public String toString() {
		buffer.append(")");
		
		String string = buffer.toString();
		
		buffer.deleteCharAt(buffer.length() - 1);
		
		return string;
	}
	
	public boolean isEmpty() {
		return "()".equals(toString());
	}
}
