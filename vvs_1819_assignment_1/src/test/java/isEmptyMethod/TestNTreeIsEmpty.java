package isEmptyMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import sut.ArrayNTree;

public class TestNTreeIsEmpty {

	/**
	 * Caso de teste para o método isEmpty para uma árvore vazia
	 */
	@Test
	public void testEmpty() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		boolean isEmpty = tree.isEmpty();
		assertEquals(true, isEmpty, "empty tree");
	}

}
