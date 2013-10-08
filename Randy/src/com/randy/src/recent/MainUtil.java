package com.randy.src.recent;

import java.util.List;

/**
 * @author randy
 */
public class MainUtil {

	public static void main(String[] args) {

		List<List<Rate>> result = Util.getInstance(DataConstructUtil.getGamesList()).getAllGameCombination();
		printResult(result);

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
