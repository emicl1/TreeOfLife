package cz.cvut.fel.pjv;

/**
 * Implementation of the {@link Queue} backed by fixed size array.
 * It's an easy implementation nothing special,
 * so just have a great day :)
 */
public class CircularArrayQueue implements Queue {

    String[] queue;
    private final int capacity;
    private int first = 0;
    private int last = 0;
    private int size = 0;

    /**
     * Creates the queue with capacity set to the value of 5.
     */
    public CircularArrayQueue() {
        this.capacity = 5;
        queue = new String[capacity];
    }

    /**
     * Creates the queue with given {@code capacity}. The capacity represents maximal number of elements that the
     * queue is able to store.
     * @param capacity of the queue
     */
    public CircularArrayQueue(int capacity) {
        this.capacity = capacity;
        queue = new String[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public boolean enqueue(String obj) {
        if (isFull()){
            return false;
        }
        queue[first] = obj;
        first++;
        size++;
        if (first == capacity){
            first = 0;
        }
        return true;
    }

    @Override
    public String dequeue() {
        if (isEmpty()){
            return null;
        }
        String obj = queue[last];
        last++;
        size--;
        if (last == capacity){
            last = 0;
        }
        return obj;
    }

    @Override
    public void printAllElements() {
        for (int i = 0; i < size; i ++){
            System.out.printf("%s ", queue[i %(capacity - 1)]);
        }
    }
}


