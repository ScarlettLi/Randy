package com.randy.src.recent;

import java.util.List;

/**
 * @author randy
 */
public class MainUtil {

	public static void main(String[] args) {

		List<List<Rate>> resultAll = Util.getInstance(DataConstructUtil.getGamesList())
				.getAllGameCombination();
		System.out.println("**********所有的组合***********");
		printResult(resultAll);
		System.out.println("**********胆的组合***********");
		List<List<Rate>> resultSure= Util.getInstance(DataConstructUtil.getGamesList())
				.getAllGameCombinationWithSure();		
		printResult(resultSure);
		System.out.println("************2串1的组合*********");
		List<List<Rate>> result2 = Util.getInstance(DataConstructUtil.getGamesList()).getGameCombinationByN(2);
		printResult(result2);
		System.out.println("***********3串1的组合**********");
		List<List<Rate>> result3 = Util.getInstance(DataConstructUtil.getGamesList()).getGameCombinationByN(3);
		printResult(result3);

	}

	public static void printResult(List<List<Rate>> finalResultList) {
		if (finalResultList == null || finalResultList.size() <= 0) {
			return;
		}

		System.out.println("一共有：" + finalResultList.size() + "组合");

		for (List<Rate> result : finalResultList) {
			StringBuffer sbBuffer = new StringBuffer("{");

			for (Rate rate : result) {
				sbBuffer.append("[" + rate.getLotteryId() + "," + rate.getGameId() + rate.getResultDescription() + "],");
			}
			sbBuffer.setLength(sbBuffer.length() - 1);
			sbBuffer.append("}");
			System.out.println(sbBuffer);
		}

	}
}
