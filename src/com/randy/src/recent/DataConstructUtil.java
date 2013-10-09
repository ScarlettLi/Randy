package com.randy.src.recent;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个仅仅是构造一些基本数据而已，包括game数据，赔率数据，是否设置为“胆”。
 * 
 * @author randy
 */
public class DataConstructUtil {

	public static final int HOW_MANY_GAMES_YOU_WANT_TO_SET_UP = 3;

	/**
	 * 构建了5个比赛，其中有两组比赛是可以让球的，另外一個比賽
	 * 
	 * @return
	 */
	public static List<Game> getGamesList() {

		List<Game> gamesList = new ArrayList<Game>();
		boolean isLetPointEnabled = false;
		for (int i = 1; i <= HOW_MANY_GAMES_YOU_WANT_TO_SET_UP; i++) {
			String gameIdString = "game" + i;

			// 设置这场比赛的赔率
			List<Rate> ratesList = new ArrayList<Rate>();
			for (int j = 1; j <= (isLetPointEnabled ? 6 : 3); j++) {
				Rate rate = new Rate(gameIdString, isLetPointEnabled ? "让球" : "不让球", j % 3, j);
				ratesList.add(rate);
			}

			Game game = new Game(gameIdString, "主队" + i, "客队" + i, isLetPointEnabled);
			game.setRateList(ratesList);

			gamesList.add(game);

			isLetPointEnabled = !isLetPointEnabled;

		}
		return gamesList;
	}
}
