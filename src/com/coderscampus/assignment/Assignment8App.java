package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.*;

public class Assignment8App {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Assignment8 assignment = new Assignment8();
		List<Future<List<Integer>>> futures = new ArrayList<>();

		// Use an ExecutorService to manage the threads
		ExecutorService executor = Executors.newFixedThreadPool(50);

		// Call the getNumbers() method 1000 times asynchronously
		for (int i = 0; i < 1000; i++) {
			futures.add(executor.submit(assignment::getNumbers));
		}

		// Create a concurrent map to store the occurrence of numbers
		ConcurrentMap<Integer, AtomicInteger> occurrences = new ConcurrentHashMap<>();

		// Gather all the data from futures and process them
		for (Future<List<Integer>> future : futures) {
			List<Integer> numbersList = future.get();
			numbersList.forEach(num -> {
				occurrences.compute(num, (key, value) -> {
					if (value == null) {
						return new AtomicInteger(1);
					} else {
						value.incrementAndGet();
						return value;
					}
				});
			});
		}

		// Shut down the executor service
		executor.shutdown();

		// Print the occurrences
		List<Integer> uniqueNumbersSorted = occurrences.keySet().stream().sorted().collect(Collectors.toList());
		for (Integer num : uniqueNumbersSorted) {
			System.out.println(num + "=" + occurrences.get(num).get());
		}

		// If there are numbers that haven't appeared in the dataset, print them with 0
		// occurrence.
		int maxNum = uniqueNumbersSorted.get(uniqueNumbersSorted.size() - 1);
		for (int i = 1; i <= maxNum; i++) {
			if (!occurrences.containsKey(i)) {
				System.out.println(i + "=0");
			}
		}
	}
}
