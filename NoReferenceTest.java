package foo;

import org.junit.Assert;
import org.junit.Test;

public class NoReferenceTest {
  @Test
  public void test() {
    Assert.assertEquals("world", "world");
  }
}
