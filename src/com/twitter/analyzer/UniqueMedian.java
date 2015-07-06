package com.twitter.analyzer;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 
 * @author Arun Velagapalli This class contains two priority queues for integer
 *         types, dividing the numbers seen so far into two halves 1. left
 *         priority queue maintains the maximum number at head of the numbers
 *         lesser than current median, -using the comparator specified in
 *         instantiation (MAX Heap) 2. right priority queue maintains the
 *         minimum number at head of the numbers greater than current median,
 *         -using the comparator specified in instantiation (MIN Heap) 3. When a
 *         new unique word count number arrives, it is compared against HEAD
 *         elements of left,right priority queues to determine where the new
 *         number fits in. 4. If size of left == size of right queues then
 *         median = (left head + right head)/2. else median= head of largest
 *         queue.
 */
public class UniqueMedian {
	private PriorityQueue<Integer> leftQueue; // priority queue that maintains
	private PriorityQueue<Integer> rightQueue;

	public UniqueMedian() {
		leftQueue = new PriorityQueue<Integer>(2, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1); // reverse natural comparison for max
											// heap

			}
		});
		rightQueue = new PriorityQueue<Integer>(); // natural comparison for
													// natural ordering
		leftQueue.add(Integer.MIN_VALUE); // initialize max heap (left queue to
											// min int value)
		rightQueue.add(Integer.MAX_VALUE); // initialize min heap (right queue
											// to max int value)
	}

	private void balanceQueues() {
		// if left has 2 elements more than right queue, remove left head and
		// add to right queue
		// apply same logic vice versa.
		if (leftQueue.size() - rightQueue.size() == 2) {
			// if left has 2 elements more than right queue, remove left head
			// and add to right queue
			rightQueue.add(leftQueue.poll());
		} else if (rightQueue.size() - leftQueue.size() == 2) {
			leftQueue.add(rightQueue.poll());
		}
	}

	/**
	 * 1.reads the current input number and determines where it goes by
	 * comparing it with heads of left,right queues. 2. Queues are Balanced such
	 * that their size differs by maximum of 1 element. 3. call helper function
	 * to determine current median.
	 * 
	 * @param uniqueWordCount
	 * @return
	 */
	public double computeMedian(Integer uniqueWordCount) {
		if (uniqueWordCount >= rightQueue.peek()) {
			rightQueue.add(uniqueWordCount);
		} else
			leftQueue.add(uniqueWordCount);
		balanceQueues();
		return getMedian();
	}

	/**
	 * helper function to compute median average of heads when leftQueue size==
	 * rightQueue size else the head of largest Queue.
	 * 
	 * @return
	 */
	private double getMedian() {
		if (leftQueue.size() == rightQueue.size()) {
			return (double) (leftQueue.peek() + rightQueue.peek()) / 2;
		} else
			return leftQueue.size() > rightQueue.size() ? leftQueue.peek() : rightQueue.peek();
	}

	public double getMedian(Integer uniquewords) throws Exception {
		return computeMedian(uniquewords);
	}

}
