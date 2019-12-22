package pt.feup.ads.util;

// The objective here is to maintain a flag Control to manage the situation when the registered rules are in a loop condition.
// A simple example: 
// WHEN time = Morning then turn off Lamp
// WHEN time = Morning then turn on Lamp.
public class LoopConditionCheck {

	public static int value = 0;
}
