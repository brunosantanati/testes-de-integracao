package configuration.basic.config;

public class EnvironmentConfig {

	private String somePhrase;

	public EnvironmentConfig(String somePhrase) {
		this.somePhrase = somePhrase;
	}

	public void someMethod() {
		System.out.println(somePhrase);
	}

}
