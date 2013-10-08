package com.randy.src.recent;

import java.io.Serializable;

public class Rate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3241837619222422117L;

	private String gameId = null;
	private String lotteryId = null;
	private int name;// 0 代表主队负 1代表平 2代表主队胜
	private int value;
	private int number = 1;// 让球数量，默认为1。目前不考虑

	public Rate() {

	}

	public Rate(String gameId, String lotteryId, int name, int value) {

		this.gameId = gameId;
		this.lotteryId = lotteryId;
		this.name = name;
		this.value = value;

	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getResultDescription() {

		return name == 0 ? "主队负" : (name == 1 ? "平" : "主队胜");

	}

}
