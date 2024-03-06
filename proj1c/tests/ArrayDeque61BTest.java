import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import edu.princeton.cs.algs4.In;
import jh61b.utils.Reflection;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    /** Check toString */
    public void toStringTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

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
        Deque61B<Integer> lld3 = new ArrayDeque61B<>();
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

        lld3.addLast(lld1.get(0));   // [0]
        lld3.addLast(lld1.get(1));   // [0, 1]
        lld3.addFirst(lld1.get(-1)); // [-1, 0, 1]
        lld3.addLast(lld1.get(2));   // [-1, 0, 1, 2]
        lld3.addFirst(lld1.get(-2)); // [-2, -1, 0, 1, 2]

        assertThat(lld1.equals(lld3)).isFalse();

        lld3.addLast(-4); // [-2, -1, 0, 1, 2, -4]

        assertThat(lld1.equals(lld2)).isFalse();

        assertThat(lld1.equals(lld3)).isFalse();
    }

    @Test
    /** Check iterator */
    public void iteratorTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

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
    /** In this test, we addFirst until the list is full */
    public void addFirstFull() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(0);
        List<Integer> returned = lld1.toList();
        System.out.println(returned);
        for (int i = 1; i < 8; i++) {
            lld1.addFirst(i);
        }
    }

    @Test
    /** In this test, we alternate between addFirst and addLast calls to verify it works correctly, additionally resize. */
    public void addFirstLastAlternate() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        for (int i = 1; i <= 16; i++) {
            if (i % 2 == 0) {
                lld1.addFirst(i);
            } else {
                lld1.addLast(i);
            }
        }
        assertThat(lld1.toList()).containsExactly(16, 14, 12, 10, 8, 6, 4, 2, 1, 3, 5, 7, 9, 11, 13, 15).inOrder();
    }

    @Test
    /** In this test, we call check the size of an array deque. */
    public void checkSize() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        for (int i = 1; i <= 8; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.size()).isEqualTo(8);
    }

    @Test
    /** In this test, we call check whether an array deque is empty. */
    public void checkIsEmpty() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        for (int i = 1; i <= 8; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    /** In this test, we call check the correctness of removeFirst and removeLast. */
    public void removeFirstLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 0) {
                lld1.addLast(i);
            } else {
                lld1.addFirst(i);
            }
        }
        assertThat(lld1.removeFirst()).isEqualTo(7);
        assertThat(lld1.removeLast()).isEqualTo(8);
    }

    @Test
    /** In this test, we keep calling removeFirst until the array deque is empty and then start adding elements. */
    public void removeFirstUntilEmpty() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        for (int i = 1; i <= 16; i++) {
            lld1.addLast(i);
        }
        while (!lld1.isEmpty()) {
            lld1.removeFirst();
        }
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addLast(2);
        lld1.addFirst(1);
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    /** In this test, we keep calling removeLast until the array deque is empty and then start adding elements. */
    public void removeLastUntilEmpty() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        for (int i = 1; i <= 16; i++) {
            lld1.addLast(i);
        }
        while (!lld1.isEmpty()) {
            lld1.removeLast();
        }
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addLast(2);
        lld1.addFirst(1);
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    /** In this test, we call get using negative and out-of-bounds indexes */
    public void getInvalid() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(1);
        assertThat(lld1.get(0)).isEqualTo(1);
        assertThat(lld1.get(0)).isEqualTo(1);
        assertThat(lld1.get(-1)).isEqualTo(null);
        assertThat(lld1.get(20)).isEqualTo(null);
    }

    @Test
    /** In this test, we try calling toList on an empty list */
    public void toEmptyListTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.toList()).isNull();
    }

    @Test
    /** In this test, we try calling size on an empty list and after trying to removing an element from it */
    public void sizeTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        for (int i = 0; i < 4; i++) {
            lld1.addFirst(i);
        }
        while (!lld1.isEmpty()) {
            lld1.removeLast();
        }
        assertThat(lld1.size()).isEqualTo(0);
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void fillDequeThenRemoveLast() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) {
            lld1.addFirst(i);
        }
        while (!lld1.isEmpty()) {
            lld1.removeLast();
        }
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void test() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addLast(0);
        lld1.removeLast();
        lld1.addFirst(2);
        lld1.addLast(3);
        lld1.removeFirst();
        lld1.addFirst(5);
        lld1.addFirst(6);
        lld1.addLast(7);
        lld1.addLast(8);
        lld1.addLast(9);
        lld1.addFirst(10);
        lld1.addFirst(11);
        lld1.addLast(12);
        lld1.isEmpty();
        lld1.addFirst(14);
        lld1.removeLast();
        lld1.addFirst(16);
        lld1.size();
        assertThat(lld1.removeFirst()).isEqualTo(16);
        assertThat(lld1.toList()).containsExactly(14, 11, 10, 6, 5, 3, 7, 8, 9).inOrder();
    }

    @Test
    public void test2() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(2);
        lld1.removeFirst();
        lld1.removeLast();
        lld1.addLast(5);
        lld1.addFirst(6);
        lld1.addFirst(7);
        lld1.removeFirst();
        lld1.addFirst(9);
        lld1.addFirst(10);
        lld1.removeLast();
    }

    @Test
    public void test3() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addLast(0);
        lld1.addFirst(1);
        lld1.addLast(2);
        lld1.addFirst(3);
        lld1.addLast(4);
        lld1.removeFirst();
        lld1.addLast(6);
        lld1.removeLast();
        lld1.isEmpty();
        lld1.addLast(9);
        lld1.addFirst(10);
        lld1.addLast(11);
        lld1.addFirst(12);
        lld1.addFirst(13);
        lld1.addLast(14);
        lld1.removeLast();
        lld1.addLast(16);
        lld1.addFirst(17);
        lld1.removeFirst();
        lld1.size();
        lld1.addLast(20);
        lld1.removeLast();
        lld1.size();
        lld1.addLast(23);
        lld1.addFirst(24);
        lld1.addLast(25);
        lld1.addFirst(26);
        lld1.addLast(27);
        lld1.addFirst(28);
        lld1.addLast(29);
        lld1.size();
        assertThat(lld1.removeFirst()).isEqualTo(28);
    }

}