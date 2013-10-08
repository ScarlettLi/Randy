package com.randy.src.old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 递归求解组合数，并输出组合数序列。看不懂的话， 先去看两个简单的Demo。
 * 
 * @author randy
 */
public class CombinationAgorithm {

	// "123456", "abcdef", "uvwxyz", "ABCDEF", "UVWXYZ" 
	/**
	 * 提供选择和选择组合的原始数据，这里每一个String就代表着一场比赛，每一个String里的字符内容就代表这个比赛结果的可能性。
	 */
	public static final String[] choiceCombination = new String[] { "123", "abcABC"};

	/**
	 * 把上面的 "123456", "abcdef", "uvwxyz", "ABCDEF", "UVWXYZ"转换成一个Map映射，123456的Key则是abcdef则是1，同理类推。
	 */
	public static Map<Integer, List<Choice>> choiceCombinationMap = new HashMap<Integer, List<Choice>>();

	/**
	 * 在把选择组合转换成Map映射以后，我们需要做的就是统计用户到底可以选择哪些组合。
	 */
	public static List<Integer[]> indexSetList = new ArrayList<Integer[]>();

	/**
	 * 每个选择组合里的选择。
	 **/
	public static Integer[] indexsArray = null;

	/**
	 * 统计结果
	 */
	public static List<String> resultList = new ArrayList<String>();

	/**
	 * 仅仅便于你看而已，在统计的时候，就把统计信息打印的更好看。
	 */
	private static final boolean printResultWhenComputing = true;

	public static void main(String[] args) {

		initChoiceCombinationAndChoices();
		computeChoiceCombinationIndexSet();
		// printIndex();
		computeChoiceCombinationBasedOnIndexSet();

		if (printResultWhenComputing) {
			printResult();
		}
	}

	/**
	 * 基于选择组合，再去统计每个选择组合里每个选项的可能性。
	 */
	public static void computeChoiceCombinationBasedOnIndexSet() {
		// TODO Auto-generated method stub

		int count = 1;
		for (int i = 0; i < indexSetList.size(); i++) {
			if (indexSetList.get(i) != null) {
				generateCombos(indexSetList.get(i));
			} else {
				System.out.println("\n开始统计" + count + "选1的组合结果:\n");
				count++;
			}
		}

	}

	private static void generateCombos(Integer[] indexArray) {
		// Call the helper method with an empty prefix and the entire
		// phone number as the remaining part.
		generateCombosHelper("", indexArray, indexArray.length - 1);
	}

	public static void generateCombosHelper(String prefix, Integer[] remaining, int remaintIndex) {

		// The current digit we are working with
		int digit = remaining[remaintIndex];

		if (remaintIndex == 0) {
			// We have reached the last digit in the phone number, so add
			// all possible prefix-digit combinations to the list
			for (int i = 0; i < choiceCombinationMap.get(digit).size(); i++) {
				String result = prefix + choiceCombinationMap.get(digit).get(i) + ",";
				resultList.add(result);
				if (printResultWhenComputing) {
					System.out.println(result);
				}
			}
		} else {
			// Recursively call this method with each possible new
			// prefix and the remaining part of the phone number.
			for (int i = 0; i < choiceCombinationMap.get(digit).size(); i++) {
				generateCombosHelper(prefix + choiceCombinationMap.get(digit).get(i) + ",", remaining, remaintIndex - 1);
			}
		}

	}

	public static void printResult() {
		if (resultList == null || resultList.size() <= 0) {
			return;
		}
		System.out.println("一共有：" + resultList.size() + "组合");
//
//		for (String result : resultList) {
//			System.out.println(result);
//		}

	}

	public static void printIndex() {
		if (indexSetList == null || indexSetList.size() <= 0) {
			return;
		}

		for (Integer[] array : indexSetList) {
			if (array != null && array.length > 0) {
				StringBuffer sbBuffer = new StringBuffer();
				for (Integer itemInteger : array) {
					sbBuffer.append(itemInteger + " ");
				}
				System.out.println(sbBuffer);
			}
		}

	}

	/**
	 * 统计出用户可以从哪些选择组合中选择。
	 */
	private static void computeChoiceCombinationIndexSet() {
		if (indexsArray == null) {
			return;// FIXME size <=1 should not be included.
		}

		for (int i = 1; i <= indexsArray.length; i++) {
			addAnEmtpyChoiceForIndication(); // XXX
			combine(indexsArray, indexsArray.length, new Integer[i], i, i);

		}
	}

	/**
	 * 这个方法仅仅是用来在统计结果的时候，打印一个提示语句的。
	 */
	private static void addAnEmtpyChoiceForIndication() {
		// TODO Auto-generated method stub
		indexSetList.add(null);
	}

	public static void combine(Integer[] a, Integer n, Integer[] b, Integer m, final Integer mFinal) {
		for (int i = n; i >= m; i--) {
			b[m - 1] = i - 1;
			if (m > 1) { // 递归求解
				combine(a, i - 1, b, m - 1, mFinal);
			} else { // 递归出口，当m == 1时, 输出一个组合序列

				Integer[] combinations = new Integer[mFinal];
				for (int j = 0; j <= mFinal - 1; j++) {
					combinations[j] = a[b[j]];
				}
				indexSetList.add(combinations);
			}
		}
	}

	/**
	 * 初始化数据：遍历每个选择组合，把每个选择组合之中的选择加入到list里，把每个list和对应选择组合的索引已经绑定起来，并且加入到选择组合的Map里。 这样子就把原始的选择组合初始化成了一个像手机键盘一样的Map组合。
	 */
	private static void initChoiceCombinationAndChoices() {
		if (choiceCombination == null || choiceCombination.length == 0) {
			return;
		}
		indexsArray = new Integer[choiceCombination.length];

		for (int i = 0; i < choiceCombination.length; i++) {
			indexsArray[i] = i;
			String itemString = choiceCombination[i];
			if (itemString == null || itemString.length() == 0) {
				continue;// FIXME should throw exception to make sure index is consistent with ChoiceCombination.
			}
			char[] chars = itemString.toCharArray();
			List<Choice> choiceList = new ArrayList<Choice>();
			for (int j = 0; j < chars.length; j++) {
				Choice choice = new Choice(String.valueOf(chars[j]));
				choiceList.add(choice);
			}

			choiceCombinationMap.put(i, choiceList);

		}

	}

}
