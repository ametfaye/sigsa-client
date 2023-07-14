package sn.awi.pgca.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * @author mmb
 *
 */
public class Password {

  private byte[] _encodedPassword;

  String         _charset = "ISO-8859-1";

  private String _hex;

  public Password() {

  }

  public Password(String charset) {
    _charset = charset;
  }

  public Password(String charset, String passCrypted) throws UnsupportedEncodingException {
    _charset = charset;
    _encodedPassword = passCrypted.getBytes(_charset);
  }

  public static String encryptPassword(String clair) throws UnsupportedEncodingException {
    String hex = "";
    int h = -1;
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      byte[] p = messageDigest.digest(clair.getBytes());
      //String sp = new String(p);
      for (int i = 0; i < p.length; i++) {
        h = p[i] & 0xFF;
        if (h < 16)
          hex += "0";
        hex += Integer.toString(h, 16).toUpperCase() + "";
        //        hex = hex + Byte.toString(p[i]);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return hex;
  }

  /**
   * @param clair
   * @return
   */
  public boolean verifyPassword(String clair) {
    try {
      String hex = "";
      String hex1 = "";
      String hex2 = "";
      int h = -1;
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      byte[] p = messageDigest.digest(clair.getBytes());
      //String sp = new String(p);
      for (int i = 0; i < p.length; i++) {
        hex1 += Byte.toString(p[i]);
        h = p[i] & 0xFF;

        if (h < 16)
          hex += "0";
        hex2 += Integer.toString(h, 16).toUpperCase();
        hex += Integer.toString(h, 16).toUpperCase() + "";
        hex = hex + Byte.toString(p[i]);
      }//fin if

      //hex = hex.substring(0, 14);
      System.out.println("pass md5 hex = " + hex);
      System.out.println("pass md5 hex1 = " + hex1);
      System.out.println("pass md5 hex2 = " + hex2);

      String sp = new String(p, _charset);
      System.out.println("pass md5 = " + sp);
      System.out.println("verif ex = " + UtilString.equal(hex, _hex));
      return MessageDigest.isEqual(p, _encodedPassword);
    }
    catch (Exception exc) {
      exc.printStackTrace();
      return false;
    }
  }

  public void setPassword(String clair) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      _encodedPassword = messageDigest.digest(clair.getBytes());
      _hex = encryptPassword(clair);
    }
    catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  public String getEncodedPassword() throws UnsupportedEncodingException {
    return new String(_encodedPassword, _charset);
  }

  public String getEncodedPasswordWithoutCharset() throws UnsupportedEncodingException {
    return new String(_encodedPassword);
  }

  public String getEncodedPasswordWithoutCharset(String clair) throws UnsupportedEncodingException {
    if (!UtilString.isCorrect(clair))
      return "";
    return new String(encryptPassword(clair));
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    Password pwd = new Password();
    pwd.setPassword("toto");
    System.out.println("Verifie toto : " + pwd.verifyPassword("toto"));
    System.out.println("walabokkkkkkkkkkk : " + pwd.getEncodedPasswordWithoutCharset("admin"));

    
   /* System.out.println("Verifie titi : " + pwd.verifyPassword("titi"));
    System.out.println("Verifie titi : " + pwd.verifyPassword("madoumaa"));
    try {
      System.out.println("pass rccm = " + pwd.getEncodedPasswordWithoutCharset("rccm"));
      System.out.println("pass sedab = " + pwd.getEncodedPasswordWithoutCharset("sedab"));
      System.out.println("pass 01 = " + pwd.getEncodedPasswordWithoutCharset("admin"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }*/
  }
}
