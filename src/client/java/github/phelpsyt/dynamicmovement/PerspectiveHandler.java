package github.phelpsyt.dynamicmovement;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class PerspectiveHandler {

    private static boolean isThirdPerson = false; // Track the current perspective
    private static boolean isInitialized = false;  // Track if initialization is done
    private static final Camera camera = new Camera();  // Use the in-game camera instance

    public static void init() {

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (!isInitialized) {
                mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                isInitialized = true; // Set initialization flag to true
                isThirdPerson = true;
            }

            if (mc.options.togglePerspectiveKey.wasPressed()) {
                if (isThirdPerson) {
                    // Switch to first-person perspective
                    mc.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
                    isThirdPerson = false;
                } else {
                    // Switch to third-person perspective
                    mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                    isThirdPerson = true;
                }
            }
        });
    }
}
