package cloneMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeClone {

	/**
	 * Caso de teste para o método clone usado o método equals
	 */
	@Test
	public void testCloneTree() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);

		ArrayNTree<Integer> tree2 = tree.clone();
		boolean equals = tree.equals(tree2);
		assertEquals(true, equals, "clone");
	}
}
