import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class CanvasListener implements KeyListener, MouseListener, MouseMotionListener{
	
	
	public boolean wDown = false;
	public boolean aDown = false;
	public boolean sDown = false;
	public boolean dDown = false;
	
	public boolean qDown = false;
	public boolean eDown = false;
	
	
	public boolean leftcontrolDown = false;
	public boolean spaceDown = false;
	
	public boolean uparrowDown = false;
	public boolean downarrowDown = false;
	
	public boolean leftshiftDown = false;
	
	public boolean middlemouseDown = false;
	
	public float[] PreviousMousePosition = {0,0};
	public float MouseXDelta = 0;
	public float MouseYDelta = 0;

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP:		uparrowDown = true; //System.out.println("up arrow down");
				break;
			case KeyEvent.VK_DOWN:		downarrowDown = true; //System.out.println("down arrow down");
				break;
			case KeyEvent.VK_SPACE: 	spaceDown = true; //System.out.println("space down");
				break;
			case KeyEvent.VK_CONTROL: 	leftcontrolDown = true; //System.out.println("left control down");
				break;
			case KeyEvent.VK_SHIFT: 	leftshiftDown = true; //System.out.println("left shift down");
				break;
			case KeyEvent.VK_W:			wDown = true; //System.out.println("w down");
				break;
			case KeyEvent.VK_A:			aDown = true; //System.out.println("a down");
				break;
			case KeyEvent.VK_S:			sDown = true; //System.out.println("s down");
				break;
			case KeyEvent.VK_D:			dDown = true; //System.out.println("d down");
				break;
			case KeyEvent.VK_Q:			qDown = true; //System.out.println("q down");
				break;
			case KeyEvent.VK_E:			eDown = true; //System.out.println("e down");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:		uparrowDown = false; //System.out.println("up arrow up");
			break;
		case KeyEvent.VK_DOWN:		downarrowDown = false; //System.out.println("down arrow up");
			break;
		case KeyEvent.VK_SPACE: 	spaceDown = false; //System.out.println("space up");
			break;
		case KeyEvent.VK_CONTROL: 	leftcontrolDown = false; //System.out.println("left control up");
			break;
		case KeyEvent.VK_SHIFT: 	leftshiftDown = false; //System.out.println("left shift up");
			break;
		case KeyEvent.VK_W:			wDown = false; //System.out.println("w up");
			break;
		case KeyEvent.VK_A:			aDown = false; //System.out.println("a up");
			break;
		case KeyEvent.VK_S:			sDown = false; //System.out.println("s up");
			break;
		case KeyEvent.VK_D:			dDown = false; //System.out.println("d up");
			break;
		case KeyEvent.VK_Q:			qDown = false; //System.out.println("q up");
			break;
		case KeyEvent.VK_E:			eDown = false; //System.out.println("e up");
	}
		
	}

	//ignoring this one
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == 2){
			if(!middlemouseDown){
				middlemouseDown = true;
				System.out.println("MMB Down");
				PreviousMousePosition[0] = arg0.getX();
				PreviousMousePosition[1] = arg0.getY();
			}
			
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton() == 2){
			middlemouseDown = false;
			System.out.println("MMB Up");
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if(middlemouseDown == true){
			
			float CurrentX = me.getX();
			float CurrentY = me.getY();
			
			MouseXDelta = PreviousMousePosition[0] - CurrentX;
			MouseYDelta = PreviousMousePosition[1] - CurrentY;
			//System.out.println(MouseXDelta);
			//System.out.println(MouseYDelta);
			
			PreviousMousePosition[0] = CurrentX;
			PreviousMousePosition[1] = CurrentY;
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		
	}

}
