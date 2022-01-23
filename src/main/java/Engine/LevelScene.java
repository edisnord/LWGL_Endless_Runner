package Engine;

public class LevelScene extends Scene{

    @Override
    public void init() {

    }

    @Override
    public void update(float dt) {
        System.out.println("inside le");
        Window.get().r = 1;
        Window.get().g = 1;
        Window.get().b = 1;
    }
}
