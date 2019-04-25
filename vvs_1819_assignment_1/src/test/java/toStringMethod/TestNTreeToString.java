package toStringMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeToString {
	/**
	 * Caso de teste para o método toString para uma representação de uma empty tree
	 */
	@Test
	public void testEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		String display = tree.toString();
		boolean equals = display.equals("[]");
		assertEquals(true, equals, "toString");
	}

	@Test
	public void testLeaf() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		String display = tree.toString();
		boolean equals = display.equals("[1]");
		assertEquals(true, equals, "toString");
	}

	@Test
	public void testMultipleElemsTree() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45, 37);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 5);

		String display = tree.toString();
		boolean equals = display.equals("[17:[37:[39]][41][45][59][85]]");
		assertEquals(true, equals, "toString");
	}

}
