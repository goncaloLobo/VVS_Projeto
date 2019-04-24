package isLeafMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sut.ArrayNTree;

public class TestNTreeIsLeaf {
	
	@Test
	public void testEmptyTree() {
		List<Integer> list = Arrays.asList();
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 0);

		boolean isLeaf = tree.isLeaf();
		assertEquals(false, isLeaf, "is leaf");
	}
	
	@Test
	public void testTreeWithOneElement() {
		List<Integer> list = Arrays.asList(39);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 1);

		boolean isLeaf = tree.isLeaf();
		assertEquals(true, isLeaf, "is leaf");
	}

	@Test
	public void testTreeWithTwoElements() {
		List<Integer> list = Arrays.asList(39, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		boolean isLeaf = tree.isLeaf();
		assertEquals(false, isLeaf, "is leaf");
	}
}
