package maxMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeMax {

	/**
	 * Caso de teste para o método max para uma árvore apenas com uma folha
	 */
	@Test
	public void testTreeWithOneElement() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(17, 1);
		int max = tree.max();
		assertEquals(17, max, "max value");
	}

	/**
	 * Caso de teste para o método max para uma árvore com vários elementos
	 */
	@Test
	public void testTreeMoreElements() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 6);
		int max = tree.max();
		assertEquals(85, max, "max value");
	}
}
