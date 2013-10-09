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

	private static void clear() {
		indexsArray = null;
		choiceCombinationMap = new HashMap<Integer, List<Rate>>();
		indexSetList = new ArrayList<List<Integer[]>>();
	}

	private static class UtilSingletonHolder {
		public static final Util instance = new Util();
	}

	public static Util getInstance(List<Game> gameList) {
		if (gameList != sourceGameList) {
			sourceGameList = gameList;
			clear();
			initChoiceCombinationAndChoices();
			computeChoiceCombinationIndexSet();
		}
		return UtilSingletonHolder.instance;
	}

	/**
	 * 
	 * @param n
	 *            从源数据里选择n个串起来。传入2，就是2选1，也就是从10场比赛里选择出2场比赛的。
	 * @return 返回所有比赛组合的组合。如果是一共十场比赛，然后是2选1的话，那么会返回45条记录。其中子List<Game>代表就是这个组合由哪几场比赛组成的。
	 */
	public List<List<Rate>> getGameCombinationByN(int n) {

		List<List<Rate>> finalResultList = new ArrayList<List<Rate>>();
		computeChoiceCombinationBasedOnIndexSet(finalResultList, n);
		return finalResultList;
	}

	/**
	 * 这个方法不考虑胆
	 * 
	 * @return 返回所有比赛的所有的可能性组合的组合。如果是一共十场比赛，那么包括从1穿1，2穿1，3穿1，。。。，到10穿1。
	 */
	public List<List<Rate>> getAllGameCombination() {

		List<List<Rate>> finalResultList = new ArrayList<List<Rate>>();
		computeChoiceCombinationBasedOnIndexSet(finalResultList);
		return finalResultList;
	}

	/**
	 * 这个方法考虑胆的比赛。解决这个问题的思路如下：首先过滤到没有设置未胆的比赛。然后把这些比赛的所有组合都得到（包括1串1,因为加上那个胆以后就是2串1了），
	 * 
	 * @return 返回所有比赛的所有的可能性组合的组合。
	 */
	public List<List<Rate>> getAllGameCombinationWithSure() {

		if (sourceGameList == null || sourceGameList.size() == 0) {
			return null;
		}

		Game gameSureGame = null;
		for (Game game : sourceGameList) {

			if (game.isSure()) {
				gameSureGame = game;
				System.out.println("breaking");
				break;
			}
			System.out.println("next");
		}

		sourceGameList.remove(gameSureGame);
		clear();
		initChoiceCombinationAndChoices();
		computeChoiceCombinationIndexSet();

		List<List<Rate>> tempResult = getAllGameCombination();

		if (tempResult == null) {
			return tempResult;
		}

		// 加上胆的赔率数据
		for (List<Rate> rateList : tempResult) {
			for (Rate rate : gameSureGame.getRateList()) {
				rateList.add(rate);
			}
		}

		return tempResult;
	}

	/**
	 * 
	 * @return 返回所有比赛的所有的可能性组合的组合。如果是一共十场比赛，那么包括从2穿1，3穿1，。。。，到10穿1。
	 */
	public List<List<Rate>> getAllGameCombinationWithoutOne() {

		List<List<Rate>> finalResultList = new ArrayList<List<Rate>>();
		computeChoiceCombinationBasedOnIndexSetWithoutN(finalResultList, 1);
		return finalResultList;
	}

	/**
	 * 基于选择组合，再去统计每个选择组合里每个选项的可能性。
	 */
	private static void computeChoiceCombinationBasedOnIndexSet(List<List<Rate>> finalResultList) {
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

	/**
	 * 基于选择组合，再去统计每个选择组合里每个选项的可能性。
	 * 
	 * @param finalResultList
	 * @param n
	 *            n代表N串1，比如n是5的话，就表示5串1.
	 */
	private static void computeChoiceCombinationBasedOnIndexSet(List<List<Rate>> finalResultList, int n) {
		// TODO Auto-generated method stub

		for (int i = 0; i < indexSetList.size(); i++) {
			List<Integer[]> list = indexSetList.get(i);
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) != null && list.get(j).length == n) {
					generateCombos(list.get(j), finalResultList);
				}
			}

		}

	}

	/**
	 * 基于选择组合，再去统计每个选择组合里每个选项的可能性。
	 * 
	 * @param finalResultList
	 * @param n
	 *            n代表N串1，比如n是5的话，就表示5串1.
	 */
	private static void computeChoiceCombinationBasedOnIndexSetWithoutN(List<List<Rate>> finalResultList, int n) {
		// TODO Auto-generated method stub

		for (int i = 0; i < indexSetList.size(); i++) {
			List<Integer[]> list = indexSetList.get(i);
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) != null && list.get(j).length != n) {
					generateCombos(list.get(j), finalResultList);
				}
			}

		}

	}

	private static void generateCombos(Integer[] indexArray, List<List<Rate>> finalResultList) {
		// Call the helper method with an empty prefix and the entire
		// phone number as the remaining part.
		List<Rate> rateList = new ArrayList<Rate>();
		generateCombinationHelper(rateList, indexArray, indexArray.length - 1, finalResultList);
	}

	private static void generateCombinationHelper(List<Rate> rateList, Integer[] remaining, int remaintIndex,
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
				generateCombinationHelper(newRatesList, remaining, remaintIndex - 1, finalResultList);
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
			List<Integer[]> list = new ArrayList<Integer[]>();
			combine(indexsArray, indexsArray.length, new Integer[i], i, i, list);
			indexSetList.add(list);

		}
	}

	/**
	 * 从N个数里面选择出M个数的组合
	 * 
	 * @param sourceData
	 *            N个源数据，作为数据传入进入
	 * @param theLeftLengthOfSourceData
	 *            源数据剩余的数据个数
	 * @param choosenData
	 *            用于存储已经选择了的数据
	 * @param howManyLeftToBeChoosen
	 *            还有多少个需要选择
	 * @param howManyYouWantToChoose
	 *            一共要选择多少个
	 * @param resultList
	 *            统计返回的结果
	 */
	private static void combine(Integer[] sourceData, Integer theLeftLengthOfSourceData, Integer[] choosenData,
			Integer howManyLeftToBeChoosen, final Integer howManyYouWantToChoose, List<Integer[]> resultList) {
		for (int i = theLeftLengthOfSourceData; i >= howManyLeftToBeChoosen; i--) {
			choosenData[howManyLeftToBeChoosen - 1] = i - 1;
			if (howManyLeftToBeChoosen > 1) { // 递归求解
				combine(sourceData, i - 1, choosenData, howManyLeftToBeChoosen - 1, howManyYouWantToChoose, resultList);
			} else { // 递归出口，当m == 1时, 输出一个组合序列

				Integer[] combinations = new Integer[howManyYouWantToChoose];
				for (int j = 0; j <= howManyYouWantToChoose - 1; j++) {
					combinations[j] = sourceData[choosenData[j]];
				}
				resultList.add(combinations);
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
