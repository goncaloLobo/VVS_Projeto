package toListMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeToList {

	@Test
	public void testCompare() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);
		List<Integer> list = new LinkedList<>();
		for (Integer elem : tree)
			list.add(elem);
		
		List<Integer> ordered = tree.toList();
		assertEquals(list, ordered, "equal List");
	}
}
