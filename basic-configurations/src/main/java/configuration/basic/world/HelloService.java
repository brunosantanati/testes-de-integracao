package configuration.basic.world;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

	private WorldService worldService;

	public HelloService(WorldService worldService) {
		this.worldService = worldService;
	}

	public String getHelloWorld() {
		return "Hello "+ worldService.getWorld();
	}

}
