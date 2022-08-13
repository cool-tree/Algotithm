/*
A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that
supports adding and removing items from either the front or the back of the data structure.
 */


import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("there is no next item");
            }

            Item item = current.item;
            current = current.next;

            return item;
        }

        // Throw an UnsupportedOperationException if the client calls the remove() method in the iterator
        public void remove() {
            throw new UnsupportedOperationException("unable to remove");
        }
    }


    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("null item");

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;


        if (isEmpty()) {
            first.next = null;
            last = first;
        }
        else {
            first.next = oldfirst;
            oldfirst.prev = first;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("null item");

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;


        if (isEmpty()) {
            last.prev = null;
            first = last;
        }
        else {
           last.prev = oldlast;
           oldlast.next = last;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("the queue is null");

        if (size > 0) {
            Item oldfirst = first.item;
            first = first.next;
            if (first != null)
                first.prev = null;
            else
                last = null;
            size--;
            return oldfirst;
        }
        else
            return null;


    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("the queue is null");

        if (size > 0) {
            Item oldlast = last.item;
            last = last.prev;

            if (last != null)
                last.next = null;
            else
                first = null;

            size--;
            return  oldlast;
        }
        else
            return null;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> testqueue = new Deque<Integer>();

        testqueue.addFirst(1);
        testqueue.addLast(2);
        testqueue.addLast(3);

        for (int num : testqueue) {
            StdOut.println(num);
        }
    }
}
