package co.jaqobb.dinjector.java;

public final class JavaHelper {
  private JavaHelper() {
  }

  public static float getJavaVersion() {
    return Float.parseFloat(System.getProperty("java.class.version"));
  }
}