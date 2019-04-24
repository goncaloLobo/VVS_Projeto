package isEmptyMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sut.ArrayNTree;

public class TestNTreeIsEmpty {
	@Test
	public void testEmpty() {
		List<Integer> list = Arrays.asList();
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 0);

		boolean isEmpty = tree.isEmpty();
		assertEquals(true, isEmpty, "count of elements");
	}

}
