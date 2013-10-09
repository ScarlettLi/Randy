package com.randy.src.old;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个仅仅是一个用于打印从N个数里面选择M的数出来的组合有多少个。这个Demo是用于参考你从不同的选择集合里，选择若干个选择集合。
 * 
 * @author randy
 */
public class DemoCombination {

	public static final Integer[] source_indexes = new Integer[] { 1, 2, 3, 4, 5, 6 };
	public static final int howManyYouWantToChoose = 3;
	public static List<String> resultList = new ArrayList<String>();

	public static void main(String[] args) {

		Integer[] b = new Integer[howManyYouWantToChoose];
		combine(source_indexes, source_indexes.length, b, howManyYouWantToChoose, howManyYouWantToChoose);

		if (resultList != null && resultList.size() > 0) {
			System.out.println(source_indexes.length + "选" + howManyYouWantToChoose + "的可能性有" + resultList.size()
					+ "种:");
			for (String item : resultList) {
				System.out.println(item);
			}
		}

	}

	/**
	 * 从N个数里面选择出M个数的组合
	 * @param sourceData N个源数据，作为数据传入进入
	 * @param theLeftLengthOfSourceData 源数据剩余的数据个数
	 * @param choosenData 用于存储已经选择了的数据
	 * @param howManyLeftToBeChoosen 还有多少个需要选择
	 * @param howManyYouWantToChoose 一共要选择多少个
	 */
	public static void combine(Integer[] sourceData, Integer theLeftLengthOfSourceData, Integer[] choosenData,
			Integer howManyLeftToBeChoosen, final Integer howManyYouWantToChoose) {
		for (int i = theLeftLengthOfSourceData; i >= howManyLeftToBeChoosen; i--) {
			choosenData[howManyLeftToBeChoosen - 1] = i - 1;
			if (howManyLeftToBeChoosen > 1) { // 递归求解
				combine(sourceData, i - 1, choosenData, howManyLeftToBeChoosen - 1, howManyYouWantToChoose);
			} else { // 递归出口，当m == 1时, 输出一个组合序列
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j <= howManyYouWantToChoose - 1; j++) {
					sb.append(sourceData[choosenData[j]]);
					sb.append(" ");
				}
				resultList.add(sb.toString());
			}
		}
	}

}
