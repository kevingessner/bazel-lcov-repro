package foo;

import org.junit.Assert;
import org.junit.Test;

public class WithReferenceTest {
  @Test
  public void test() {
    Assert.assertEquals("world", ReferencedFile.hello);
  }
}
