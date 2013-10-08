package com.randy.src.recent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 递归求解组合数，并输出组合数序列。看不懂的话， 先去看两个简单的Demo。
 * 
 * @author randy
 */
public class Util {

	// // "123456", "abcdef", "uvwxyz", "ABCDEF", "UVWXYZ"
	// /**
	// * 提供选择和选择组合的原始数据，这里每一个String就代表着一场比赛，每一个String里的字符内容就代表这个比赛结果的可能性。
	// */
	// public static final String[] choiceCombination = new String[] { "123456", "abcdef", "uvwxyz" };
	//
	/**
	 * 把上面的 "123456", "abcdef", "uvwxyz", "ABCDEF", "UVWXYZ"转换成一个Map映射，123456的Key则是abcdef则是1，同理类推。
	 */
	public static Map<Integer, List<Rate>> choiceCombinationMap = new HashMap<Integer, List<Rate>>();

	/**
	 * 在把选择组合转换成Map映射以后，我们需要做的就是统计用户到底可以选择哪些组合。
	 */
	public static List<List<Integer[]>> indexSetList = new ArrayList<List<Integer[]>>();

	/**
	 * 每个选择组合里的选择。
	 **/
	public static Integer[] indexsArray = null;
	//
	// /**
	// * 这里将会收集所有的混选组合，如果有5场比赛，那么讲包括2串1，3串1，4串1，5串1的组合的可能。每一个串法都会放到一个list里去，然后把这些list都再次加入到最终的结果里。
	// */
	// public static List<String> resultList = new ArrayList<String>();
	//

	private static List<Game> sourceGameList = null;

	private Util() {
	}

	private static class UtilSingletonHolder {
		public static final Util instance = new Util();
	}

	public static Util getInstance(List<Game> gameList) {
		if (sourceGameList == null) {
			sourceGameList = gameList;
			initChoiceCombinationAndChoices();
			computeChoiceCombinationIndexSet();
		}
		return UtilSingletonHolder.instance;
	}

	/**
	 * 仅仅便于你看而已，在统计的时候，就把统计信息打印的更好看。
	 */
	private static final boolean printResultWhenComputing = false;

	public static void main(String[] args) {

	}

	/**
	 * 
	 * @param n
	 *            从源数据里选择n个串起来。传入2，就是2选1，也就是从10场比赛里选择出2场比赛的。
	 * @return 返回所有比赛组合的组合。如果是一共十场比赛，然后是2选1的话，那么会返回45条记录。其中子List<Game>代表就是这个组合由哪几场比赛组成的。
	 */
	public static List<List<Game>> getGameCombinationByN(int n) {

		initChoiceCombinationAndChoices();
		computeChoiceCombinationIndexSet();
		// printIndex();
		// computeChoiceCombinationBasedOnIndexSet();

		return null;
	}

	/**
	 * 
	 * @return 返回所有比赛的所有的可能性组合的组合。如果是一共十场比赛，那么包括从2选1，3选1，。。。，到10选1。
	 */
	public List<List<Rate>> getAllGameCombination() {

		List<List<Rate>> finalResultList = new ArrayList<List<Rate>>();
		computeChoiceCombinationBasedOnIndexSet(finalResultList);
		return finalResultList;
	}

	/**
	 * 基于选择组合，再去统计每个选择组合里每个选项的可能性。
	 */
	public static void computeChoiceCombinationBasedOnIndexSet(List<List<Rate>> finalResultList) {
		// TODO Auto-generated method stub

		for (int i = 0; i < indexSetList.size(); i++) {
			List<Integer[]> list = indexSetList.get(i);
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) != null) {
					generateCombos(list.get(j), finalResultList);
				}
			}

		}

	}

	private static void generateCombos(Integer[] indexArray, List<List<Rate>> finalResultList) {
		// Call the helper method with an empty prefix and the entire
		// phone number as the remaining part.
		List<Rate> rateList = new ArrayList<Rate>();
		generateCombosHelper(rateList, indexArray, indexArray.length - 1, finalResultList);
	}

	public static void generateCombosHelper(List<Rate> rateList, Integer[] remaining, int remaintIndex,
			List<List<Rate>> finalResultList) {

		// The current digit we are working with
		int digit = remaining[remaintIndex];

		if (remaintIndex == 0) {
			// We have reached the last digit in the phone number, so add
			// all possible prefix-digit combinations to the list
			for (int i = 0; i < choiceCombinationMap.get(digit).size(); i++) {
				List<Rate> newRatesList = new ArrayList<Rate>();
				newRatesList.addAll(rateList);
				newRatesList.add(choiceCombinationMap.get(digit).get(i));
				finalResultList.add(newRatesList);
			}
		} else {
			// Recursively call this method with each possible new
			// prefix and the remaining part of the phone number.
			for (int i = 0; i < choiceCombinationMap.get(digit).size(); i++) {
				List<Rate> newRatesList = new ArrayList<Rate>();
				newRatesList.addAll(rateList);
				newRatesList.add(choiceCombinationMap.get(digit).get(i));
				generateCombosHelper(newRatesList, remaining, remaintIndex - 1, finalResultList);
			}
		}

	}

	public static void printResult(List<List<Rate>> finalResultList) {
		if (finalResultList == null || finalResultList.size() <= 0) {
			return;
		}

		for (List<Rate> result : finalResultList) {
			StringBuffer sbBuffer = new StringBuffer();

			for (Rate rate : result) {
				System.out.println(sbBuffer.append(rate.getGameId() + rate.getResultDescription() + ","));
			}

			System.out.println(sbBuffer);
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
			List<Integer[]> list = new ArrayList<Integer[]>();
			combine(indexsArray, indexsArray.length, new Integer[i], i, i, list);
			indexSetList.add(list);

		}
	}

	public static void combine(Integer[] a, Integer n, Integer[] b, Integer m, final Integer mFinal,
			List<Integer[]> list) {
		for (int i = n; i >= m; i--) {
			b[m - 1] = i - 1;
			if (m > 1) { // 递归求解
				combine(a, i - 1, b, m - 1, mFinal, list);
			} else { // 递归出口，当m == 1时, 输出一个组合序列

				Integer[] combinations = new Integer[mFinal];
				for (int j = 0; j <= mFinal - 1; j++) {
					combinations[j] = a[b[j]];
				}
				list.add(combinations);
			}
		}
	}

	/**
	 * 初始化数据：遍历每个选择组合，把每个选择组合之中的选择加入到list里，把每个list和对应选择组合的索引已经绑定起来，并且加入到选择组合的Map里。 这样子就把原始的选择组合初始化成了一个像手机键盘一样的Map组合。
	 */
	private static void initChoiceCombinationAndChoices() {
		if (sourceGameList == null || sourceGameList.size() == 0) {
			return;
		}
		indexsArray = new Integer[sourceGameList.size()];

		for (int i = 0; i < sourceGameList.size(); i++) {
			indexsArray[i] = i;
			List<Rate> rateListsForCurrentGame = sourceGameList.get(i).getRateList();

			choiceCombinationMap.put(i, rateListsForCurrentGame);

		}

	}

}
