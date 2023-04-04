package com.editev.util;

public interface Name {
  public String getNameString();

  public class Reference implements Name {
    public final Name name;
    public Reference(Name name) { this.name = name; }
    public String getNameString() { return name.getNameString(); }
  }
}
