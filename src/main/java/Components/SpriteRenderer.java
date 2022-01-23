package Components;

import Engine.Component;

public class SpriteRenderer extends Component {

    private boolean firstTime = true;
    @Override
    public void start(){
        System.out.println("im starting");
    }

    @Override
    public void update(float dt) {
        if(firstTime) {
            System.out.println("im updating 1st time");
            firstTime = false;
        }else {
            System.out.println("im updating");
        }
    }
}
