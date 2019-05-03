

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class AllCouplingsUsePaths {
	
	// caminho: (1,2)
	@Test
	public void testDeleteEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		tree.delete(1);
		int size = tree.size();
		assertEquals(1, size, "delete empty");
	}
	 // caminho: (1,2)
	@Test
	public void testDeleteSmallerThanRoot() {
		List<Integer> list = Arrays.asList(2, 3, 4);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(1);
		int size = tree.size();
		assertEquals(3, size, "delete root");
	}
	
	// caminho: (1,3,5,6)
	@Test
	public void testDeleteRoot() {
		List<Integer> list = Arrays.asList(1, 2);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		tree.delete(1);
		int size = tree.size();
		assertEquals(1, size, "delete root");
	}

	// caminho: 1,3,5,7,8,12
	@Test
	public void testDeleteSmaller() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(3);
		int size = tree.size();
		assertEquals(4, size, "delete");
	}

	// caminho: 1,3,5,7,9,10,11,12
	@Test
	public void testDeleteBigger() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(20);
		int size = tree.size();
		assertEquals(4, size, "delete");
	}

	// caminho: 1,3,5,7,9,11,12
	// caminho (no compact): [1,2,8,1,2,3,4,5,3,6,7]
	@Test
	public void testDeleteMiddleElem() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(10);
		int size = tree.size();
		assertEquals(3, size, "delete");
	}

	// caminho: 1,3,5,7,9,11,12
	// caminho: 1,2,4,9 (dentro do proposePosition)
	@Test
	public void testDeleteCap() {
		List<Integer> list = Arrays.asList(1, 5, 10);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(5);
		int size = tree.size();
		assertEquals(2, size, "delete");
	}

	// caminho: 1,3,5,7,9,11,12
	// caminho: 1,2,6,7,2,5,3 (dentro do proposePosition)
	@Test
	public void testDeleteFive() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(6);
		int size = tree.size();
		assertEquals(4, size, "delete");
	}
	
	// caminho: 1,3,5,7,9,11,12
	// caminho: 1,2,6,7,2,6,7,2,5,3
	@Test
	public void testDeleteIndexTwice() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(11);
		int size = tree.size();
		assertEquals(4, size, "delete");
	}
}
