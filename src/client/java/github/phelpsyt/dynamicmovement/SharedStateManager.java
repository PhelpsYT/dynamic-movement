package github.phelpsyt.dynamicmovement;

public class SharedStateManager {
    private static boolean animationFinished = false;

    public static boolean isAnimationFinished() {
        return animationFinished;
    }

    public static void setAnimationFinished(boolean finished) {
        animationFinished = finished;
    }
}