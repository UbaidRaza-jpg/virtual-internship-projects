# Power of Two Max Heap

This project implements a Max Heap data structure where each parent node has exactly `2^childExponent` children.

## Indexing Math
We use an array to store the heap elements in level order. The index calculation is highly optimized using bitwise shifts instead of multiplication and division:
- `childCount = 1 << childExponent`
- `parent(i) = (i - 1) >> childExponent`
- `firstChild(i) = (i << childExponent) + 1`

## Time Complexity
- **Insert:** $O(\log_d n)$ - Sifts up the new element to its correct position.
- **PopMax:** $O(d \cdot \log_d n)$ - Sifts down the element, where $d = 2^{childExponent}$. At each level, it scans up to $d$ children to find the maximum.

## How to Run Tests
The project uses Maven and JUnit 5 for testing.
To run the tests:
```bash
mvn test
```
