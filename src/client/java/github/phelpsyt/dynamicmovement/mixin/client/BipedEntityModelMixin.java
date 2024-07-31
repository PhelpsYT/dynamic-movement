package github.phelpsyt.dynamicmovement.mixin.client;

import github.phelpsyt.dynamicmovement.SharedStateManager;
import github.phelpsyt.dynamicmovement.mixin.client.BipedEntityModelMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

import static github.phelpsyt.dynamicmovement.mixin.client.CameraMixin.*;


@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {

    @Shadow
    private ModelPart head;
    @Shadow
    private ModelPart hat;
    @Shadow
    private ModelPart body;
    @Shadow
    private ModelPart rightArm;
    @Shadow
    private ModelPart leftArm;
    @Shadow
    private ModelPart rightLeg;
    @Shadow
    private ModelPart leftLeg;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void setVisible(boolean visible) {
        if (!SharedStateManager.isAnimationFinished()) {
            // Adjust visibility for third-person back view
            this.head.visible = visible;
            this.hat.visible = visible;
            this.body.visible = visible;
            this.rightArm.visible = visible;
            this.leftArm.visible = visible;
            this.rightLeg.visible = visible;
            this.leftLeg.visible = visible;
        } else{
            // Adjust visibility for third-person front view
            this.head.visible = false;
            this.body.visible = visible;
            this.rightArm.visible = visible; // Example: Hide arms in front view
            this.leftArm.visible = visible;  // Example: Hide arms in front view
            this.rightLeg.visible = visible;
            this.leftLeg.visible = visible;
            this.hat.visible = visible;
        }
    }
}
