/**
 * represent a key of a keyboard
 * 
 * @author Benedikt
 * @version 1.0
 */
public class Key {
  public int x, height, key;

  public boolean isHalfTone;

  public Key(int x, int height, int key, boolean halfTone) {
    this.x = x;
    this.height = height;
    this.key = key;
    this.isHalfTone = halfTone;
  }

}
