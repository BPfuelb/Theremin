import processing.core.PApplet;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;

class Sensor {
	Theremin parent;
	Controller controller;

	public Sensor(PApplet parent) {
		this.parent = (Theremin) parent;

		controller = new Controller();
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.config().setFloat("Gesture.Circle.MinRadius", 20.0f);
		controller.config().setFloat("Gesture.Circle.MinArc", .15f);
		controller.config().save();
		
	}

	public void getValues() {
		Frame frame = controller.frame();

		if (frame.hands().count() == 2) {

			Hand lHand = frame.hands().leftmost();
			Hand rHand = frame.hands().rightmost();

			float fingerZahl = rHand.fingers().get(2).tipPosition().getY();

			float leftHand = lHand.palmPosition().getY();
			float rightHand = rHand.palmPosition().getY();

			float diff = fingerZahl - rightHand;
			rightHand = rightHand - diff;

			parent.handMoved(leftHand, rightHand);
		} else
			parent.handMoved(0, 0);

	}

	public void getGestures() {

		for (Gesture gestureObj : controller.frame().gestures()) {
			switch (gestureObj.state()) {
			case STATE_START:
				break;
			case STATE_UPDATE:
				break;
			case STATE_STOP:
				parent.nextWave();
				break;
			default:
				System.out.println("Handle unrecognized states");
				break;
			}
		}

	}

}