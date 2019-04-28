package deleteMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeDelete {
	@Test
	public void testDeleteEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		tree.delete(1);
		int size = tree.size();
		assertEquals(1, size, "delete empty");
	}

	@Test
	public void testDeleteRoot() {
		List<Integer> list = Arrays.asList(1, 2);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		tree.delete(1);
		int size = tree.size();
		assertEquals(1, size, "delete root");
	}

	@Test
	public void testDeleteTreeWithTwoElements() {
		List<Integer> list = Arrays.asList(1, 2);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		tree.delete(1);
		int size = tree.size();
		assertEquals(1, size, "delete root");
	}

	@Test
	public void testDeleteSmallerThanRoot() {
		List<Integer> list = Arrays.asList(2, 3, 4);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(1);
		int size = tree.size();
		assertEquals(3, size, "delete root");
	}

	@Test
	public void testBiggerThanTree() {
		List<Integer> list = Arrays.asList(1, 2, 3);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(4);
		int size = tree.size();
		assertEquals(3, size, "insert");
	}

	@Test
	public void testSmallerThanChildren() {
		List<Integer> list = Arrays.asList(1, 3, 4);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(2);
		int size = tree.size();
		assertEquals(3, size, "insert");
	}

	@Test
	public void testBorla() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 4);

		tree.delete(6);
		int size = tree.size();
		assertEquals(3, size, "insert");
	}
}
