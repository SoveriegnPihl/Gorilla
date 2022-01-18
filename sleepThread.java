import java.util.concurrent.locks.ReentrantLock;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;

public class sleepThread extends Thread{
	PathTransition path;
	RotateTransition rotate;
	
	public sleepThread(PathTransition pat, RotateTransition rot) {
		this.path = pat;
		this.rotate = rot;
	}
	
	//@Override
	public void run() {
        path.play();
        rotate.play();
		
		
	}

}
