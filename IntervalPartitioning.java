package lab04;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;




public class IntervalPartitioning {
		
	static class Interval {

		// * start time of interval
		final int start;
		// * end time of interval
		final int finish;
		// * convenient name for the interval.
		final String name;
	
		Interval(String name, int start, int finish) {
			this.start = start;
			this.finish = finish;
			this.name = name;
		}

		/**
		 * convenience method to print an Interval
		 */

		@Override
		public String toString() {
			return "Interval [start=" + start + ", finish=" + finish + ", name=" + name + "]";
		}
	}


	
/*
 * Checks whether a room containing a list of Intervals is compatible with a new interval
 * @param room  the list of intervals already scheduled in the room
 * @param i   the new interval to be checked - is there space in room for this interval
 * @return   boolean indicating whether i can be added to the room without overlapping any intervals
 */
	
	static boolean isCompatible(List<Interval> room, Interval i) {
		boolean clear = true;
		for (Interval j : room) {
			if (overlaps(j, i)) {
					clear = false;
					break;
				}
			}
		return clear;		
	}

/*
 *  returns true if the two intervals overlap, and returns false otherwise	
 */
	
	static boolean overlaps(Interval i, Interval j) {
		if (i.finish > j.start && i.start <= j.start) {
			return true;
		} else if (j.finish > i.start && j.start <= i.start) {
			return true;
		}
		return false;
	}
	
	
	static List<List<Interval>> partition(Interval[] intervals) {

		// sort intervals in order of ascending start time.
		Arrays.sort(intervals, new Comparator<Interval>() {
			@Override
			public int compare(Interval first, Interval second) {
				return Integer.compare(first.start, second.start);
			}
		});

		// list of class rooms and the intervals that are scheduled in them.
		List<List<Interval>> classrooms = new LinkedList<List<Interval>>();

		
		for (Interval i : intervals) {
			boolean found = false;
			for (List<Interval> r : classrooms) {

				if (isCompatible(r, i)) {
					r.add(i);
					found = true;
					break;
				}
			}
			if (found == false) {
				List<Interval> newRoom = new LinkedList<Interval>();
				newRoom.add(i);
				classrooms.add(newRoom);
			}
		}

		return classrooms;

	}

//*********************************************************	
	

/**
 * test the partitioning algorithm on a set of intervals
 * 
 * @param name
 *            name of the test
 * @param test
 *            the set of intervals to test
 * @param expected
 *            the expected number of rooms required for the schedule
 */
		
	public static void testPartition(String name, Interval[] test, int expected){
		List<List<Interval>> schedule = IntervalPartitioning.partition(test);
		int actual = schedule.size();
	    if (actual == expected){
	    	System.out.println("  Pass:   "+name);
	    }
	    else {
	    	System.out.println("*********************");	    	
	    	System.out.println("  Fail:   "+name);
	    	System.out.println("  Optimal Result: "+ expected+" partitions");
	    	System.out.println("  Your Result:   "+actual+" partitions");
			System.out.print(
					"You have scheduled the " + test.length + " intervals as follows in this number of classrooms: " + schedule.size() + "\n\n");

			int count = 0;
			for (int room = 0; room < schedule.size(); room ++) {
				System.out.println ("Room: " + room);
				for (Interval i : schedule.get(room)) {
					System.out.println(i);
					count ++;
				}
				System.out.print("\n");
			}
	    	System.out.println("*********************\n");

	    }

	}
	
/*
 * Main will test the scheduling algorithm on various inputs	
 */
	
	public static void main(String[] args) {

		Interval a = new Interval("a",10, 30);
		Interval b = new Interval("b",15, 35);
		Interval c = new Interval("c",20, 50);
		Interval d = new Interval("d",25, 45);
		Interval e = new Interval("e",25, 70);
		Interval f = new Interval("f",40, 90);
		Interval g = new Interval("g",40, 60);
		Interval h = new Interval("h",55, 95);
		Interval j = new Interval("j",75, 95);
		Interval k = new Interval("k",80, 95);
		Interval l = new Interval("l",20, 30);
		Interval m = new Interval("m", 5, 40);
		Interval n = new Interval("n",40, 50);
		Interval p = new Interval("p",30, 60);
		Interval q = new Interval("q",35, 65);
		Interval r = new Interval("r",50, 85);
		Interval s = new Interval("s",30, 60);
		Interval t = new Interval("t",30, 60);
		
		
		Interval[] intervals1 = {a,b,c,d,e,f,g,h,j,k};
		testPartition("test1",intervals1, 5);
		Interval[] intervals2 = {a,b,c,d,e,f,g};
		testPartition("test2",intervals2, 5);
		Interval[] intervals3 = {b,g,j};
		testPartition("test3",intervals3, 1);
		Interval[] intervals4 = {a,b,f,g,k};
		testPartition("test4",intervals4, 2);
		Interval[] intervals5 = {h,j,k};
		testPartition("test5",intervals5, 3);
		Interval[] intervals6 = {};
		testPartition("test6",intervals6, 0);
		Interval[] intervals7 = {l,m,n,p};
		testPartition("test7",intervals7, 2);
		Interval[] intervals8 = {d,g,m,q,r};
		testPartition("test8",intervals8, 3);
		

	}
}
