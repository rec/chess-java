package com.editev.util;

import java.util.*;
import java.lang.reflect.*;

public abstract class CraftyMapper implements java.io.Serializable {

  // TO DO:  make this like toHTMLString
  public String toXMLString(String topTag) {
    StringBuffer b = new StringBuffer();
    b.append("<" + topTag + ">");
    for (Iterator i = getTags(); i.hasNext(); ){
      TV tv = (TV) i.next();
      b.append("<"); b.append(tv.tag); b.append(">");
      b.append(tv.value);
      b.append("</"); b.append(tv.tag); b.append(">");
    }
    b.append("</" + topTag + ">");
    return b.toString();
  }

  public StringBuffer toHTMLString(StringBuffer buf) {
    buf.append("<table>");
    for (Iterator i = getTags(); i.hasNext(); ){
      TV tv = (TV) i.next();
      buf.append("<tr><td>")
	.append(tv.tag)
	.append("</td><td>")
	.append(tv.value)
	.append("</td></tr>");
    }
    buf.append("</table>");
    return buf;
  }

  public void setTags(Map tagMap) {
    Class clas = getClass();
    for (Iterator i = tagMap.keySet().iterator(); i.hasNext();) {
      String key = (String) i.next();
      Object value = tagMap.get(key);
      try {
	Field f = clas.getField(key);
	f.set(this, value);
      } catch (IllegalArgumentException ex) { }
      catch (IllegalAccessException iaex) { }
      catch (NoSuchFieldException nsfex) { }
    }
  }

  public Iterator getTags() {
    List tagList = new Vector();
    Class clas = getClass();
    Field[] fields = clas.getFields();
    for (int i = 0; i < fields.length; i++) {
      Object value = null;
      try {
	value = fields[i].get(this);
      } catch (IllegalArgumentException ex) { }
      catch (IllegalAccessException iaex) { }
      if (value != null) {
	TV tv = new TV();
	tv.tag = fields[i].getName();
	tv.value = value.toString();
	tagList.add(tv);
      }
    }
    return tagList.iterator();
  }


  class TV {
    String tag;
    String value;
  }
}
