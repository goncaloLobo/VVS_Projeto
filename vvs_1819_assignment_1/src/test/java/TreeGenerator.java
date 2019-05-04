import java.util.Arrays;
import java.util.List;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.ArrayNTree;

public class TreeGenerator extends Generator<ArrayNTree<Integer>> {

	private int min = 5;
	private int max = 20;
	public static final int MAX_SIZE = 100;
	public static final int MAX_INT = 100;

	// generator respects `@InRange` annotation, defining the size of string
	public void configure(InRange range) {
		min = range.minInt();
		max = range.maxInt();
	}

	public TreeGenerator(Class<ArrayNTree<Integer>> type) {
		super(type);
	}

	@Override
	public ArrayNTree<Integer> generate(SourceOfRandomness arg0, GenerationStatus arg1) {

		int size = arg0.nextInt(1, max); // tamanho aleatório da árvore

		Integer[] array = new Integer[size]; // array
		for (int i = 0; i < array.length; i++) {
			int randomValue = arg0.nextInt(max); // valores aleatórios de valores
			array[i] = randomValue;
		}
		List<Integer> list = Arrays.asList(array); // passar de array para lista

		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		return tree;
	}

}
