package org.akj.quiz;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class KidsMathQuiz {

	private final static String[] OPERANDS = new String[]{"+", "-"};
	private final static Integer MAX = 15;
	private final static Random random = new Random();

	private static ScriptEngine scriptEngine = null;

	static {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
	}

	@Test
	public void simpleMathExpression() {
		Integer count = 40;
		simplestMath(count);

		simplerMath(40, 3);

		simplerMath(40, 4);
	}

	private static void simplestMath(Integer count) {
		IntStream.range(0, count).mapToObj(i -> {
			String algorithm = null;
			while (true) {
				int operator1 = random.nextInt(MAX);
				String operand = OPERANDS[random.nextInt(2)];
				int operator2 = random.nextInt(MAX);

				if ((operator1 == 0 && operator2 == 0) || operator1 < operator2 || (operator1 + operator2) > MAX) {
					continue;
				} else {
					algorithm = String.format("%2d %s %2d ", operator1, operand, operator2);
					break;
				}
			}
			return algorithm;
		}).forEach(s -> System.out.println(s + "= "));
	}

	private static void simplerMath(Integer count, Integer operatorCount) {
		IntStream.range(0, count).mapToObj(i -> {
			String algorithm = null;
			while (true) {
				List<Integer> operatorList = IntStream.range(0, operatorCount).mapToObj(j -> random.nextInt(MAX)).collect(Collectors.toList());
				List<String> operandList = IntStream.range(0, operatorCount).mapToObj(k -> OPERANDS[random.nextInt(2)]).collect(Collectors.toList());

				StringBuilder builder = new StringBuilder();
				boolean isLegal = true;
				for (int j = 0; j < operatorList.size(); j++) {
					builder.append(String.format("%2d %s ", operatorList.get(j), operandList.get(j)));
					isLegal &= validate(builder);

					if (!isLegal) {
						break;
					}
				}

				if (builder.length() > 0) {
					isLegal &= validate(builder);
					if (isLegal) {
						algorithm = builder.substring(0, builder.length() - 2);
						break;
					}
				}
			}
			return algorithm;
		}).forEach(s -> System.out.println(s + "= "));
	}

	private static boolean validate(StringBuilder builder) {
		String algorithm = builder.toString();
		algorithm = algorithm.substring(0, algorithm.length() - 2);
		try {
			Integer result = (Integer) scriptEngine.eval(algorithm);
			if (result < 0 || result > MAX) {
				return false;
			}
		} catch (ScriptException e) {
			log.error(e.getMessage());
		}

		return true;
	}
}
