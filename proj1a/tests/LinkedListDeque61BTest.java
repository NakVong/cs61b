import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
    /** Check toString */
    public void toStringTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        System.out.println(lld1.toString());
    }

    @Test
    /** Check equals */
    public void equalsTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld3 = new LinkedListDeque61B<>();
        Integer num = 2;
        assertThat(lld1.equals(num)).isFalse();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        lld2.addLast(0);   // [0]
        lld2.addLast(2);   // [0, 2]
        lld2.addFirst(-1); // [-1, 0, 2]
        lld2.addLast(2);   // [-1, 0, 2, 2]
        lld2.addFirst(-2); // [-2, -1, 0, 2, 2]

        lld3.addLast(0);   // [0]
        lld3.addLast(1);   // [0, 1]
        lld3.addFirst(-1); // [-1, 0, 1]
        lld3.addLast(2);   // [-1, 0, 1, 2]
        lld3.addFirst(-2); // [-2, -1, 0, 1, 2]
        lld3.addLast(-4); // [-2, -1, 0, 1, 2, -4]

        assertThat(lld1.equals(lld2)).isFalse();

        assertThat(lld1.equals(lld3)).isFalse();
    }

    @Test
    /** Check iterator */
    public void iteratorTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        for (Integer i : lld1) {
            System.out.println(i);
        }
    }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    /** This test checks where or not the list is empty. */
    public void isEmptyTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.isEmpty()).isTrue();
        lld1.addFirst(1);
        assertThat(lld1.isEmpty()).isFalse();
        lld1.removeLast();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    /** This test checks whether size returns is the correct size */
    public void sizeTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.size()).isEqualTo(0);
        lld1.addFirst(20);
        lld1.addLast(-5);
        lld1.addFirst(1);
        assertThat(lld1.size()).isEqualTo(3);
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    /** This test performs interspersed removeFirst and removeLast calls */
    public void removeFirstAndRemoveLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        assertThat(lld1.toList()).containsExactly(-1, 0, 1).inOrder();

        Integer firstElement = lld1.removeFirst();
        assertThat(firstElement).isEqualTo(-1);
        assertThat(lld1.toList()).containsExactly(0, 1).inOrder();

        Integer lastElement = lld1.removeLast();
        assertThat(lastElement).isEqualTo(1);
        assertThat(lld1.toList()).containsExactly(0).inOrder();

        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly().inOrder();

        lld1.addLast(69);
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly().inOrder();
    }

    @Test
    /** This test fills the list and calls removeFirst until the list is empty */
    public void removeFirstUntilEmpty() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.removeFirst()).isEqualTo(-2);
        assertThat(lld1.removeFirst()).isEqualTo(-1);
        assertThat(lld1.removeFirst()).isEqualTo(0);
        assertThat(lld1.removeFirst()).isEqualTo(1);
        assertThat(lld1.removeFirst()).isEqualTo(2);
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    /** This test fills the list and calls removeLast until the list is empty */
    public void removeLastUntilEmpty() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.removeLast()).isEqualTo(2);
        assertThat(lld1.removeLast()).isEqualTo(1);
        assertThat(lld1.removeLast()).isEqualTo(0);
        assertThat(lld1.removeLast()).isEqualTo(-1);
        assertThat(lld1.removeLast()).isEqualTo(-2);
        assertThat(lld1.removeLast()).isNull();
    }

    @Test
    /** This test performs get calls to check the correct element is being accessed and returned. */
    public void getTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.get(3)).isEqualTo(1);
        assertThat(lld1.get(1)).isEqualTo(-1);
        assertThat(lld1.get(5)).isEqualTo(null);
        assertThat(lld1.get(-2)).isEqualTo(null);
        assertThat(lld1.get(20)).isEqualTo(null);
    }

    @Test
    /** This test performs getRecursive calls to check the correct element is being accessed and returned. */
    public void getRecursiveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.getRecursive(3)).isEqualTo(1);
        assertThat(lld1.getRecursive(1)).isEqualTo(-1);
        assertThat(lld1.getRecursive(5)).isEqualTo(null);
        assertThat(lld1.getRecursive(-2)).isEqualTo(null);
        assertThat(lld1.getRecursive(2000)).isEqualTo(null);
    }
}


