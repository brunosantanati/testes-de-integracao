package configuration.basic.world;

import org.springframework.stereotype.Service;

@Service
public class WorldServiceImpl implements WorldService {

	@Override
	public String getWorld() {
		return "World";

	}

}
