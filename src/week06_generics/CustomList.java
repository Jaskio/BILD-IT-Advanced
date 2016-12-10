package week06_generics;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;


public class CustomList<T> extends  AbstractList<T> implements List<T> {
	private T[] array;
	private final int DEFAULT_CAPACITY = 10;
	private int size = 0;
	
	CustomList() {
		array = (T[]) new Object[DEFAULT_CAPACITY];
	}
	
	CustomList(int capacity) {
		array = (T[]) new Object[capacity];
	}
	
	
	@Override
	public boolean add(T t) {
		if (size == array.length) {
			ensureCapacity();
		}
		array[size++] = t;
		return true;
	}

	@Override
	public void add(int index, T t) {
		if (size == array.length) {
			ensureCapacity();
		}
		System.arraycopy(array, index, array, index + 1, size - index);
		size++;
		array[index] = t;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		if (c.size() + size >= array.length) {
			ensureCapacity();
		}
		System.arraycopy(c.toArray(), 0, array, size + 1, c.size());
		size += c.size();
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		if (c.size() + size >= array.length) {
			ensureCapacity();
		}
		T[] tempArray = Arrays.copyOfRange(array, index, size);
		System.arraycopy(c.toArray(), 0, array, index, c.size());
		System.arraycopy(tempArray, 0, array, c.size() + index, tempArray.length);
		size += c.size();
		return true;
	}
	
	void ensureCapacity() {
		array = Arrays.copyOf(array, array.length * 2);
	}

	@Override
	public void clear() {
		array = (T[]) new Object[DEFAULT_CAPACITY];
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < size; i++) {
			if (array[i].equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		T[] collection = (T[]) c.toArray();
		int eqCount = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < c.size(); j++) {
				if (array[i].equals(collection[j])) {
					eqCount++;
				}
			}
		}
		return (eqCount == c.size()) ? true : false;
	}

	@Override
	public T get(int index) {
		return array[index];
	}

	@Override
	public int indexOf(Object o) {
		for (int i = 0; i < size; i++) {
			if (array[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return (size == 0) ? true : false;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterate();
	}

	@Override
	public int lastIndexOf(Object o) {
		for (int i = size - 1; i >= 0; i--) {
			if (array[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		return new ListIterate(0);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new ListIterate(index);
	}

	
    private class Iterate implements Iterator<T> {
        int cursor;       
        int lastRet = -1; 
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = array;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
            	CustomList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            final int size = CustomList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = CustomList.this.array;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((T) elementData[i++]);
            }
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class ListIterate extends Iterate implements ListIterator<T> {
    	ListIterate(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public T previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = CustomList.this.array;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (T) elementData[lastRet = i];
        }

        public void set(T t) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
            	CustomList.this.set(lastRet, t);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(T t) {
            checkForComodification();

            try {
                int i = cursor;
                CustomList.this.add(i, t);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
	

	@Override
	public boolean remove(Object o) {
		if (size == 0) {
			throw new IndexOutOfBoundsException("Index: 0, Size: 0");
		}
		for (int i = 0; i < size; i++) {
			if (array[i].equals(o)) {
				System.arraycopy(array, i + 1, array, i, array.length - i - 1);
				size--;
				return true;
			}
		}
		return false;
	}

	@Override
	public T remove(int index) {
		if (size == 0) {
			throw new IndexOutOfBoundsException("Index: 0, Size: 0");
		}
		T returnIndex = array[index];
		System.arraycopy(array, index + 1, array, index, array.length - index - 1);
		size--;
		return returnIndex;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object o : c) {
			if (contains(o)) {
				remove(o);
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		T[] tempArray = Arrays.copyOfRange(array, 0, size);
		int index = 0;
		for (int i = 0; i < size; i++) {
			if (c.contains(tempArray[i])) {
				set(index++, tempArray[i]);
				modified = true;
			}
		}
		size = index;
		return modified;
	}

	@Override
	public T set(int index, T t) {
		T oldElement = array[index];
		array[index] = t;
		return oldElement;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<T> subList(int from, int to) {
		if (from < 0)
			throw new IndexOutOfBoundsException("fromIndex = " + from);
	    if (to > size)
	        throw new IndexOutOfBoundsException("toIndex = " + to);
	    if (from > to)
	        throw new IllegalArgumentException("fromIndex(" + from + ") > toIndex(" + to + ")");
	    
		List<T> list = new CustomList<>();
		for (T s : Arrays.copyOfRange(array, from, to)) {
			list.add(s);
		}
		return list;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(array, size);
	}

	@Override
	public <T> T[] toArray(T[] t) {
		if (t.length < size) {
			return (T[]) Arrays.copyOf(array, size, t.getClass());
		}
	    System.arraycopy(array, 0, t, 0, size);
	    if (t.length > size) {
	    	t[size] = null;
	    }
		return t;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				s += array[i];
				continue;
			}
			s += array[i] + "\n";
		}
		return s;
	}
}