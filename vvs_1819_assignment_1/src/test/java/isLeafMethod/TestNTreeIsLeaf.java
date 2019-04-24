package isLeafMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sut.ArrayNTree;

public class TestNTreeIsLeaf {

	// se a árvore é vazia então não tem nós, fará sentido testar?
	@Test
	public void testEmptyTree() {
		List<Integer> list = Arrays.asList();
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 0);

		boolean isLeaf = tree.isLeaf();
		assertEquals(false, isLeaf, "is leaf");
	}

	/**
	 * Caso de teste para o método isLeaf para uma árvore com 1 folha
	 */
	@Test
	public void testTreeWithOneElement() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		boolean isLeaf = tree.isLeaf();
		assertEquals(true, isLeaf, "is leaf");
	}

	/**
	 * Caso de teste para o método isLeaf para uma árvore com vários elementos
	 */
	@Test
	public void testTreeWithMoreElements() {
		List<Integer> list = Arrays.asList(39, 17, 55);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		boolean isLeaf = tree.isLeaf();
		assertEquals(false, isLeaf, "is leaf");
	}
}
