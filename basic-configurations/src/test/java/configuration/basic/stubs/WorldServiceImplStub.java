package configuration.basic.stubs;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Primary;

import configuration.basic.world.WorldService;

@TestConfiguration
@Primary
public class WorldServiceImplStub implements WorldService {

	@Override
	public String getWorld() {
		return "Stub";
	}

}
