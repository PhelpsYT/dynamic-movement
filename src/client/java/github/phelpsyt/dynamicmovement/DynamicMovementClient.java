package github.phelpsyt.dynamicmovement;

import net.fabricmc.api.ClientModInitializer;

public class DynamicMovementClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		// YO
		PerspectiveHandler.init();
	}
}