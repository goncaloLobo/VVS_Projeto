package equalsMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeEquals {

	@Test
	public void testComparingItself() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);

		boolean equals = tree.equals(tree);
		assertEquals(true, equals, "equals");
	}

	@Test
	public void testEqualsTrees() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list1, 3);

		boolean equals = tree.equals(tree2);
		assertEquals(true, equals, "equals");
	}
}