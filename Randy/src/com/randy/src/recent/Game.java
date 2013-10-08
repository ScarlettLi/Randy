package com.randy.src.recent;

import java.io.Serializable;
import java.util.List;

/**
 * 封装选择的对象。比如说选择组合的集合里有：
 * 
 * 123 456 789 abc def,
 * 
 * 那么选择组合就为123. 其中123之中的一个选择就是1.这个1就代表了一个选择，使用Choice来代替。为了考虑通用性，这里选择了String类型来存储这个选择。理论上根据情况，使用byte short int来存储，效率会更高。
 * 
 * ，
 * 
 * <id>1_20131006_7_062</id> <gameType>1</gameType> <day>20131006</day> <weekId>7</weekId> <roundId>062</roundId>
 * <leagueId>1_2</leagueId> <league>阿甲</league> <hostTeamId>1_283</hostTeamId> <hostTeamName>拉普拉塔</hostTeamName>
 * <guestTeamId>1_276</guestTeamId> <guestTeamName>萨斯菲尔德</guestTeamName> <time>20131008063000</time>
 * <endTime>20131007234500</endTime> <hot>0</hot> <unsupport>FT001</unsupport>
 * 
 * @author shirdrn
 */
public class Game implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4342457366955965573L;

	private String id = null;
	private String day = null;
	private String roundId = null;
	private String leagueId = null;
	private String league = null;
	private String hostTeamId = null;
	private String hostTeamName = null;
	private String guestTeamId = null;
	private String guestTeamName = null;
	private String unsupport = null;
	private String time = null;
	private String endTime = null;

	private int gameType;
	private int weekId;
	private int hot;

	private boolean isSure = false;// 是否设置这场比赛为胆。
	private boolean isLetPointEnabled = false;// 是否设置这场比赛让球。

	private List<Rate> rateList;

	public Game() {

	}

	public Game(String id, String hostTeamName, String guestTeamName, boolean isLetPointEnabled) {
		
		this.id = id;
		this.hostTeamName = hostTeamName;
		this.guestTeamName = guestTeamName;
		this.isLetPointEnabled = isLetPointEnabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getRoundId() {
		return roundId;
	}

	public void setRoundId(String roundId) {
		this.roundId = roundId;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getHostTeamId() {
		return hostTeamId;
	}

	public void setHostTeamId(String hostTeamId) {
		this.hostTeamId = hostTeamId;
	}

	public String getHostTeamName() {
		return hostTeamName;
	}

	public void setHostTeamName(String hostTeamName) {
		this.hostTeamName = hostTeamName;
	}

	public String getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(String guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public String getGuestTeamName() {
		return guestTeamName;
	}

	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}

	public String getUnsupport() {
		return unsupport;
	}

	public void setUnsupport(String unsupport) {
		this.unsupport = unsupport;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public int getWeekId() {
		return weekId;
	}

	public void setWeekId(int weekId) {
		this.weekId = weekId;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public List<Rate> getRateList() {
		return rateList;
	}

	public void setRateList(List<Rate> rateList) {
		this.rateList = rateList;
	}

	public boolean isSure() {
		return isSure;
	}

	public void setSure(boolean isSure) {
		this.isSure = isSure;
	}

	public boolean isLetPointEnabled() {
		return isLetPointEnabled;
	}

	public void setLetPointEnabled(boolean isLetPointEnabled) {
		this.isLetPointEnabled = isLetPointEnabled;
	}

}
