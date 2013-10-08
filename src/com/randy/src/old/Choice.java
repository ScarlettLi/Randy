package com.randy.src.old;

/**
 * 封装选择的对象。比如说选择组合的集合里有：
 * 
 * 123 456 789 abc def,
 * 
 * 那么选择组合就为123. 其中123之中的一个选择就是1.这个1就代表了一个选择，使用Choice来代替。为了考虑通用性，这里选择了String类型来存储这个选择。理论上根据情况，使用byte short int来存储，效率会更高。
 * 
 * ，
 * 
 * @author shirdrn
 */
public class Choice {

	private String source = null;

	public Choice(String source) {
		this.setSource(source);
	}

	public Choice() {

	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return source;
	}

}
