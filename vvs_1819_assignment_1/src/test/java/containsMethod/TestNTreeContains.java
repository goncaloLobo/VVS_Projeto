package containsMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sut.ArrayNTree;

public class TestNTreeContains {

	@Test
	public void testEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		boolean contains = tree.contains(1);
		assertEquals(false, contains, "contains element");
	}

	@Test
	public void testElementAtRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		boolean contains = tree.contains(1);
		assertEquals(true, contains, "contains element");
	}

	@Test
	public void testTreeDoesNotContainSmaller() {
		List<Integer> list = Arrays.asList(1, 2, 3);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		boolean contains = tree.contains(0);
		assertEquals(false, contains, "contains element");
	}

	/**
	 * Caso de teste para o método contain cujo valor exista
	 */
	@Test
	public void testContains() {
		List<Integer> list = Arrays.asList(10, 39, 50);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		
		boolean contains = tree.contains(50);
		assertEquals(true, contains, "contains element");
	}
}
