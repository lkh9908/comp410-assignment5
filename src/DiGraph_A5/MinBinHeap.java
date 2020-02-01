package DiGraph_A5;

public class MinBinHeap implements Heap_Interface {
	private EntryPair[] array; //load this array
	private int size;
	private static final int arraySize = 10000; //Everything in the array will initially 
	//be null. This is ok! Just build out 
	//from array[1]

	public MinBinHeap() {
		this.array = new EntryPair[arraySize];
		size = 0;
		array[0] = new EntryPair(null, -100000); //0th will be unused for simplicity 
		//of child/parent computations...
		//the book/animation page both do this.
	}

	//Please do not remove or modify this method! Used to test your entire Heap.
	@Override
	public EntryPair[] getHeap() { 
		return this.array;
	}

	@Override
	public void insert(EntryPair entry) {
		// insert
		//	  in: an EntryPair object, containing the priority and string, 
		//    assume no duplicate priorities will be inserted 
		// 	  return: void

		if (entry.priority < 0) {
			System.out.println("Input contains negative priority: (" + entry.value + ", " + entry.priority +
					").");
		} else {
			size++;
			int ing = size; 
			while (entry.getPriority() < array[parent(ing)].getPriority()) {
				array[ing] = array[parent(ing)];
				array[parent(ing)] = null;
				ing = parent(ing);
				if (ing == 1) break;
			} 
			array[ing] = entry;
		}
	}

	@Override
	public void delMin() {
		//	delMin
		//	  in: nothing
		//	  return: void
		if (size == 0) return;
		if (size == 1) {
			array[1] = null;
			size--;
			return;
		}
		EntryPair temp = array[size];
		array[size] = null;
		array[1] = null;
		int ing = 1;
		while (array[leftChild(ing)] != null || array[rightChild(ing)] != null) {
			// while it is not at leaf
			if (array[rightChild(ing)] == null) {
				// right leave is null
				// avoid null pointer
				if (array[leftChild(ing)].getPriority() < temp.getPriority()) {
					array[ing] = array[leftChild(ing)];
					array[leftChild(ing)] = null;
					ing = leftChild(ing);
					break;
				}
				break;
			} else if (temp.getPriority() < array[leftChild(ing)].getPriority() &&
					temp.getPriority() < array[rightChild(ing)].getPriority()) {
				// right place
				break;
			} else {
				if (array[leftChild(ing)].getPriority() < 
						array[rightChild(ing)].getPriority()) {
					array[ing] = array[leftChild(ing)];
					array[leftChild(ing)] = null;
					ing = leftChild(ing);
				} else {
					array[ing] = array[rightChild(ing)];
					array[rightChild(ing)] = null;
					ing = rightChild(ing);
				}
			}
		}
		array[ing] = temp;
		size--;
	}


	@Override
	public EntryPair getMin() {
		//		getMin
		//		  in: nothing
		//		  return: an element (an EntryPair object)
		if (size == 0) return array[0];
		else return array[1];
	}

	@Override
	public int size() {
		//		size
		//		  in: nothing
		//		  return: integer 0 or greater
		return size;
	}

	@Override
	public void build(EntryPair[] entries) {
		//		build
		//		  in: array of elements that need to be in the heap
		//		  return: void
		//		  (assume for a build that the bheap will start empty)
		int length = entries.length;

		for (int i = 0; i < length; i++) {
			// check for negative priority
			if (entries[i].priority < 0) System.out.println("Input contains negative priority: (" + 
					entries[i].value + 
					", " + entries[i].priority +
					").");
			else array[i + 1] = entries[i];
		}
		int last_parent = Math.floorDiv(length, 2);
		int ing = last_parent;
		while (ing > 0) {
			int curr = ing;
			while ((array[leftChild(curr)] != null || array[rightChild(curr)] != null)) {
				if ((array[leftChild(curr)] != null && array[rightChild(curr)] != null)) {
					if (array[curr].getPriority() > array[leftChild(curr)].priority || 
							array[curr].getPriority() > array[rightChild(curr)].getPriority()) {
						if (array[leftChild(curr)].priority > array[rightChild(curr)].priority) {
							swap(curr, rightChild(curr));
							curr = rightChild(curr);
						} else {
							swap(curr, leftChild(curr));
							curr = leftChild(curr);
						}
					} else {
						break;
					}
				} else if (array[leftChild(curr)] != null) {
					if (array[curr].getPriority() > array[leftChild(curr)].getPriority()) {
						swap(curr, leftChild(curr));
						curr = leftChild(curr);
					} else {
						break;
					}
				} else {
					if (array[curr].getPriority() > array[rightChild(curr)].getPriority()) {
						swap(curr, rightChild(curr));
						curr = rightChild(curr);
					} else {
						break;
					}
				}
			}
			ing--;
		}
		size += length;
	}

	// healper methods
	private int parent(int i) 
	{ 
		return i / 2; 
	} 

	private int leftChild(int i) 
	{ 
		return (2 * i); 
	} 

	private int rightChild(int i) 
	{ 
		return (2 * i) + 1; 
	} 

	private void swap(int x, int y) {
		EntryPair temp = array[x];
		array[x] = array[y];
		array[y] = temp;
	}
}