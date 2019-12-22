package pt.feup.ads.environment.state;


import pt.feup.ads.environment.rule.Rule;

/*
 * StatePattern
 * */
public interface EnvironmentState {
	
	public void doAction(Rule rule);
	
	public String getName();

}
