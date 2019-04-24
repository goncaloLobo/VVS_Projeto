package minMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeMin {
	
	/**
	 * Caso de teste para o método min para uma árvore apenas com raiz
	 */
	@Test
	public void testTreeWithOneElement() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(17, 1);
		int min = tree.min();
		assertEquals(17, min, "min value");
	}

	/**
	 * Caso de teste para o método min para uma árvore com vários elementos
	 */
	@Test
	public void testTreeMoreElements() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 6);
		int min = tree.min();
		assertEquals(17, min, "min value");
	}
}
