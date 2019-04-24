package countLeavesMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sut.ArrayNTree;

public class TestNTreeCountLeaves {
	
	/**
	 * Caso de teste para o método count leaves
	 * com uma árvore apenas com raiz
	 */
	@Test
	public void testTreeWithOneElement() {
		List<Integer> list = Arrays.asList(17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 1);

		int leaves = tree.countLeaves();
		assertEquals(1, leaves, "number of leaves");
	}
	
	/**
	 * Caso de teste para o método count leaves
	 * com árvore com vários elementos
	 */
	@Test
	public void testTreeMoreElements() {
		List<Integer> list = Arrays.asList(39, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		int leaves = tree.countLeaves();
		assertEquals(1, leaves, "number of leaves");
	}
}
