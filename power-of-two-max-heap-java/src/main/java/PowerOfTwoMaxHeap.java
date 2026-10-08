import java.util.Arrays;
import java.util.NoSuchElementException;

public class PowerOfTwoMaxHeap {
    private int[] data;
    private int size;
    private final int childExponent;

    public PowerOfTwoMaxHeap(int childExponent) {
        if (childExponent < 0 || childExponent > 30) {
            throw new IllegalArgumentException("childExponent must be between 0 and 30");
        }
        this.childExponent = childExponent;
        this.data = new int[10];
        this.size = 0;
    }

    public void insert(int value) {
        if (size == data.length) {
            grow();
        }
        int index = size;
        // Sift-up: O(log_d n) time complexity, where d = 2^childExponent.
        // We use the "hole" technique to avoid swapping at each step.
        while (index > 0) {
            int parentIndex = (index - 1) >> childExponent;
            if (data[parentIndex] >= value) {
                break;
            }
            data[index] = data[parentIndex];
            index = parentIndex;
        }
        data[index] = value;
        size++;
    }

    public int popMax() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        int max = data[0];
        size--;
        if (size == 0) {
            return max;
        }
        int last = data[size];
        int index = 0;
        long childCount = 1L << childExponent;

        // Sift-down: O(d * log_d n) time complexity.
        while (true) {
            long firstChildLong = ((long) index << childExponent) + 1;
            if (firstChildLong >= size) {
                break;
            }
            int firstChild = (int) firstChildLong;
            long limitLong = Math.min((long) size, firstChildLong + childCount);
            int limit = (int) limitLong;

            int maxChildIndex = firstChild;
            int maxChildVal = data[firstChild];
            for (int i = firstChild + 1; i < limit; i++) {
                if (data[i] > maxChildVal) {
                    maxChildVal = data[i];
                    maxChildIndex = i;
                }
            }

            if (last >= maxChildVal) {
                break;
            }
            data[index] = maxChildVal;
            index = maxChildIndex;
        }
        data[index] = last;
        return max;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void grow() {
        int oldCapacity = data.length;
        if (oldCapacity == Integer.MAX_VALUE - 8) {
            throw new OutOfMemoryError("Heap capacity exceeded");
        }
        long newCapacity = (long) oldCapacity * 2;
        if (newCapacity > Integer.MAX_VALUE - 8) {
            newCapacity = Integer.MAX_VALUE - 8;
        }
        data = Arrays.copyOf(data, (int) newCapacity);
    }
}
