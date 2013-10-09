package com.randy.src.old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个算法统计是从一系列的字符串里，选择若干个字符串，然后再从这若干个字符串里选择一个字符出来的合集的组合。
 * 
 * @author ztang
 * 
 */
public class DemoMapCombination {
	public static final String[] choiceCombination = new String[] { "123456", "abcdef", "uvwxyz", "ABCDEF", "UVWXYZ" };

	/**
	 * 用户存储最后的结果
	 */
	public static List<String> resultList = new ArrayList<String>();

	/**
	 * 指定从哪几个选择组合选择。0,2,4则代表着分别从123456, uvwxyz和UVWXYZ里分别选择一个字母的组合
	 */
	public static final Integer[] CHOICEINDEX_INTEGERS = new Integer[] { 0, 2, 3 };
	public static Map<Integer, List<Choice>> choiceCombinationMap = new HashMap<Integer, List<Choice>>();

	public static void main(String[] args) {
		generateMap();
		generateCombos(CHOICEINDEX_INTEGERS);
		printResult();
	}

	public static void printResult() {
		if (resultList == null || resultList.size() <= 0) {
			return;
		}

		System.out.println("从" + getInfo() + "中分别选择一个字母出来的可能性有如下" + resultList.size() + "种:\n");
		for (String result : resultList) {
			System.out.println(result);
		}

	}

	private static StringBuffer getInfo() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < CHOICEINDEX_INTEGERS.length; i++) {

			sb.append(choiceCombination[CHOICEINDEX_INTEGERS[i]] + ",");

		}

		sb.setLength(sb.length() - 1);
		return sb;
	}

	private static void generateCombos(Integer[] indexArray) {
		// Call the helper method with an empty prefix and the entire
		// phone number as the remaining part.
		generateCombosHelper("", indexArray, indexArray.length - 1);
	}

	public static void generateCombosHelper(String currentResult, Integer[] remaining, int remaintIndex) {

		// The current digit we are working with
		int digit = remaining[remaintIndex];

		if (remaintIndex == 0) {
			// We have reached the last digit in the phone number, so add
			// all possible prefix-digit combinations to the list
			for (int i = 0; i < choiceCombinationMap.get(digit).size(); i++) {
				String result = currentResult + choiceCombinationMap.get(digit).get(i) + ",";
				resultList.add(result);
			}
		} else {
			// Recursively call this method with each possible new
			// prefix and the remaining part of the phone number.
			for (int i = 0; i < choiceCombinationMap.get(digit).size(); i++) {
				generateCombosHelper(currentResult + choiceCombinationMap.get(digit).get(i) + ",", remaining, remaintIndex - 1);
			}
		}

	}

	private static void generateMap() {
		// TODO Auto-generated method stub
		if (choiceCombination == null || choiceCombination.length == 0) {
			return;
		}

		for (int i = 0; i < choiceCombination.length; i++) {
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