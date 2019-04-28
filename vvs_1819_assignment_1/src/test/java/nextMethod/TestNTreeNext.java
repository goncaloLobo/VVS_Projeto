package nextMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeNext {

	@Test
	public void testEmptyStack() {
		LinkedList<ArrayNTree<Integer>> stack = new LinkedList<ArrayNTree<Integer>>();
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);
		stack.clear();
		Iterator<Integer> it1 = tree.iterator();
		
		while(it1.hasNext()) {
			it1.next();
		}
		
		assertThrows(NoSuchElementException.class, () -> {
			it1.next();
	    });
	}
}
