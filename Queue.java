package game;

public class Queue {

    public Queue() {
        init();
    }

    //
    // Public
    //
    public void enqueue(int item) throws Exception {
        // Check for overflow.
        if (backPtr < CAPACITY-1) {
            backPtr = backPtr + 1;
            arr[backPtr] = item;
        } else {
            Exception overflow = new Exception("Queue Overflow");
            throw overflow;
        }
    }

    public int dequeue() throws Exception {
        int retVal = 0;
        // Check for underflow.

        if (! this.isEmpty()) {
            retVal = arr[frontPtr];
            // Shift every element towards the front.
            for(int i = 0; i < backPtr; i++) {
                arr[i] = arr[i+1];
            }
            // Reinitialize the last element
            arr[backPtr] = 0;
            // shift the back pointer towards the front.
            backPtr--;
        } else {
            // In case of underflow, throw an underflow exception.
            Exception underflow = new Exception("Queue Underflow");
            throw underflow;
        }
        return retVal;
    }

    public boolean isEmpty() {
        boolean retVal = false;
        if (backPtr == -1) {
            retVal = true;
        }
        return retVal;
    }

    public int getCapacity() {
        return CAPACITY;
    }

    //
    // Private
    //
    private void init() {
        for (int i = 0; i < CAPACITY; i++) {
            arr[i] = 0;
        }
    }

    private final int CAPACITY = 100;
    private int[] arr = new int[CAPACITY];
    private int frontPtr = 0;
    private int backPtr  = -1;
}