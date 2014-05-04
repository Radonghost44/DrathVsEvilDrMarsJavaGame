package game;

public class Stack {


    //
    // Public
    //
    public Stack() {
        init();
    }

    public void push(int item) {
        // Check for stack overflow.
        if (topPtr > 0) {
            topPtr = topPtr - 1;
            arr[topPtr] = item;
        } else {
            System.out.println("OVERFLOW");
        }
    }

    public int pop() {
        int retVal = 0;
        // Check for stack underflow.
        if (topPtr < CAPACITY) {
            retVal = arr[topPtr];
            topPtr = topPtr + 1;
            
        } else {
        	System.out.println("UNDERFLOW");
            retVal = -1;
        }
        return retVal;
    }

    public boolean isEmpty() {
        boolean retVal = false;
        if (topPtr == CAPACITY) {
            retVal = true;
        }
        return retVal;
    }

    public boolean isEmptyMo() {
        return (topPtr == CAPACITY);
    }


    //
    // Private
    //
    private final int CAPACITY = 100;
    private int[] arr = new int[CAPACITY];
    private int topPtr = 0;

    private void init() {
       for (int i = 0; i < CAPACITY; i++) {
           arr[i] = 0;
       }
       topPtr = CAPACITY;
    }


}