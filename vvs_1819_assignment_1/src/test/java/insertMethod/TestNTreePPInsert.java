package insertMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreePPInsert {

	// caminho: 1,6,12,14,1,5
	// satisfaz 1,6,12,14,1 (64)
	@Test
	public void testMenorQMax() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15, 20);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(19);
		int size = tree.size();
		assertEquals(5, size, "insert test seven");
	}

	// caminho: 1,6,12,13,1,6,7,1,6,8,9
	// satisfaz 1,6,12,13,19 (62); 6,12,13,1,6 (53); 12,13,1,6,7 (86); 7,1,6,8,9 (59)
	@Test
	public void testSeven() {
		List<Integer> list = Arrays.asList(2, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		System.out.println("antes: " + tree.info());
		tree.insert(1);
		System.out.println("dpsss: " + tree.info());
		// tree.insert(19);
		int size = tree.size();
		assertEquals(5, size, "insert test seven");
	}
}
