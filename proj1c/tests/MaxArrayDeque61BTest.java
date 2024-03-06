import deque.ArrayDeque61B;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {

    class Person implements Comparable<Person> {
        private static class AgeComparator implements Comparator<Person> {
            public int compare(Person a, Person b) {return a.compareTo(b);}
        }

        public static Comparator<Person> getAgeComparator() { return new AgeComparator(); }
        int age;
        String name;

        public Person(String n, int a) {
            name = n;
            age = a;
        }

        @Override
        public int compareTo(Person otherPerson) { return this.age - otherPerson.age;}
    }

    class Dog implements Comparable<Dog> {
        private static class NaturalComparator implements Comparator<Dog> {
            public int compare(Dog a, Dog b) {
                return a.compareTo(b);
            }
        }
        private static class NameComparator implements Comparator<Dog> {
            public int compare(Dog a, Dog b) {
                return a.name.compareTo(b.name);
            }
        }
        public static Comparator<Dog> getNaturalComparator() {
            return new NaturalComparator();
        }
        public static Comparator<Dog> getNameComparator() {
            return new NameComparator();
        }
        int size;
        String name;
        public Dog(String n, int s) {
            name = n;
            size = s;
        }
        public void bark() {
            System.out.println(name + " barked!");
        }
        @Override
        public int compareTo(Dog otherDog) {
            return size - otherDog.size;
        }
    }

    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }


    @Test
    public void ageTest() {
        MaxArrayDeque61B<Person> people = new MaxArrayDeque61B<>(new Person.AgeComparator());
        people.addFirst(new Person("Jack", 10));
        people.addFirst(new Person("Michael", 20));
        people.addFirst(new Person("Livia", 51));
        assertThat(people.max().age).isEqualTo(51);
    }

    @Test
    public void intTest() {
        MaxArrayDeque61B<Integer> num = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        num.addFirst(-9);
        num.addFirst(-1);
        num.addFirst(3);
        assertThat(num.max()).isEqualTo(3);
    }

    @Test
    public void maxNoArgumentTest() {
        MaxArrayDeque61B<Dog> dogs = new MaxArrayDeque61B<>(Dog.getNaturalComparator());
        dogs.addLast(new Dog("Cadell", 3));
        dogs.addLast(new Dog("Grey", 9));
        dogs.addLast(new Dog("Nico", 5));
        Dog maxDog = dogs.max();

        assertThat("Grey").isEqualTo(maxDog.name);

    }

    @Test
    public void maxWithArgumentTest() {
        MaxArrayDeque61B<Dog> dogs = new MaxArrayDeque61B<>(Dog.getNaturalComparator());
        dogs.addLast(new Dog("Cadell", 8));
        dogs.addLast(new Dog("Grey", 2));
        dogs.addLast(new Dog("Nico", 5));
        Dog maxDog = dogs.max(Dog.getNameComparator());

        assertThat("Nico").isEqualTo(maxDog.name);
    }
}
