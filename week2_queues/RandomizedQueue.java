/*
A randomized queue is similar to a stack or queue,
 except that the item removed is chosen uniformly at random among items in the data structure.
 */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] elements;
    private int count = 0;
    private int head, tail;

    // construct an empty randomized queue
    public RandomizedQueue() {
        elements = (Item[]) new Object[1];
        head = 0;
        tail = 0;
    }

    // shuffle the elements in the queue
    private void shuffle(Item[] items) {
        for (int i = head; i < head + count; i++) {
            int r = StdRandom.uniform(head, i+1) % elements.length;
            exch(items, i % elements.length, r);
        }
    }

    private void exch(Item[] a, int i, int j) {
        Item swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (count == elements.length) {
            resize(2* elements.length);
        }

        tail = (tail+1) % elements.length;
        count++;
        elements[tail] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = head; i < head + count; i++)
            copy[i - head] = elements[i % elements.length];
        elements = copy;
        head = 0;
        tail = count - 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
           throw new NoSuchElementException(" the queue is empty");

        int r = StdRandom.uniform(head, head+count) % elements.length;
        exch(elements, head, r);
        Item item = elements[head];

        elements[head] = null;
        head = (head+1) % elements.length;
        count--;
        if (count > 0 && count == elements.length/4) resize(elements.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("the queue is empty");

        int r = StdRandom.uniform(head, head+count) % elements.length;
        Item item = elements[r];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        // shuffle(elements);
        // 除了标准的接口，不应该涉及其他操作
        return new ShuffleIterator();
    }

    private class ShuffleIterator implements Iterator<Item> {

        private  int i = head;
        private Item newelements[] = (Item[]) new Object[elements.length];

        private ShuffleIterator() {
            for (int k = head; k < head+count; k++) {
                newelements[k % elements.length] = elements[k % elements.length];
            }
        }

        public boolean hasNext() {
            return i < head + count;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("there is no next item");
            int random = StdRandom.uniform(i, head + count) % elements.length;
            Item item = newelements[random];
            if (random != i % elements.length) // 已经取过的值会被 没有取过的、i指向的值 覆盖掉；而如果恰好取的是i指向的值，那么就不会再取
                newelements[random] = newelements[i % elements.length];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("unable to remove");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        for (int i = 0; i < 5; i++)
            test.enqueue(i+5);
        test.dequeue();
        test.dequeue();

        for (int i = 0; i < 5; i++)
            test.enqueue(i+10);

        for (int num:test)
            StdOut.println(num);
    }

}
