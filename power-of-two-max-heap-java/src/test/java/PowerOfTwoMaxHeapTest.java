import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PowerOfTwoMaxHeapTest {

    @Test
    public void testRandomOperations() {
        int[] exponents = {0, 1, 2, 3, 5, 10, 20, 30};
        int numOperations = 100000;
        long seed = 42;

        for (int exp : exponents) {
            PowerOfTwoMaxHeap myHeap = new PowerOfTwoMaxHeap(exp);
            PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
            Random random = new Random(seed);

            for (int i = 0; i < numOperations; i++) {
                if (pq.isEmpty() || random.nextDouble() < 0.6) { // 60% insert, 40% pop
                    int val = random.nextInt();
                    myHeap.insert(val);
                    pq.add(val);
                } else {
                    assertEquals(pq.poll(), myHeap.popMax());
                }
                assertEquals(pq.size(), myHeap.size());
                assertEquals(pq.isEmpty(), myHeap.isEmpty());
            }

            // Pop remaining elements
            while (!pq.isEmpty()) {
                assertEquals(pq.poll(), myHeap.popMax());
            }
            assertTrue(myHeap.isEmpty());
        }
    }

    @Test
    public void testEdgeCases() {
        PowerOfTwoMaxHeap heap = new PowerOfTwoMaxHeap(2);
        
        // Single element
        heap.insert(42);
        assertEquals(42, heap.popMax());
        assertTrue(heap.isEmpty());

        // Negative numbers and boundaries
        heap.insert(-10);
        heap.insert(Integer.MIN_VALUE);
        heap.insert(Integer.MAX_VALUE);
        heap.insert(0);
        heap.insert(-10); // Duplicate
        
        assertEquals(Integer.MAX_VALUE, heap.popMax());
        assertEquals(0, heap.popMax());
        assertEquals(-10, heap.popMax());
        assertEquals(-10, heap.popMax());
        assertEquals(Integer.MIN_VALUE, heap.popMax());
        assertTrue(heap.isEmpty());

        // Inserting after popping everything
        heap.insert(99);
        assertEquals(99, heap.popMax());

        // Force repeated resizing
        for (int i = 0; i < 10000; i++) {
            heap.insert(i);
        }
        assertEquals(10000, heap.size());
        for (int i = 9999; i >= 0; i--) {
            assertEquals(i, heap.popMax());
        }
    }

    @Test
    public void testExceptions() {
        assertThrows(IllegalArgumentException.class, () -> new PowerOfTwoMaxHeap(-1));
        assertThrows(IllegalArgumentException.class, () -> new PowerOfTwoMaxHeap(31));

        PowerOfTwoMaxHeap heap = new PowerOfTwoMaxHeap(1);
        assertThrows(NoSuchElementException.class, heap::popMax);
    }
}
