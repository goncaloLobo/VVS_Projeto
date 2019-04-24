package maxMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeMaxMethod {
	@Test
	public void testTreeWithOneElement() {
		List<Integer> list = Arrays.asList(17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 1);
		int min = tree.min();
		assertEquals(17, min, "min");
	}
	
	@Test
	public void testTreeMoreElements() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 6);
		int min = tree.min();
		assertEquals(17, min, "min");
	}
}
