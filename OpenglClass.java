import javax.swing.JFrame;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.awt.event.WindowAdapter;
import java.awt.geom.Point2D;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;

public class OpenglClass implements GLEventListener, KeyListener, MouseInputListener  {  

private static GLAutoDrawable glDraw;
static float antsize=5;

static Ant[] antarray;
static SpacePosition[] foodarray;
static Space space;
private static JCheckBox polygonal;
private static JCheckBox bezier;
private static JCheckBox spline;
private static JCheckBox catmull;

double screenwidth, screenheight;
static double windowwidth, windowheight;		


public Point screenToGl(double x, double y)
	{
	double screenaspectratio=screenwidth/screenheight;
	double windowaspectratio=windowwidth/windowheight;
	x=((x/screenwidth))*windowwidth;	
	y=((y/screenheight))*windowheight;
	if (screenaspectratio>windowaspectratio)
	    return new Point(x*screenaspectratio,y);
	else
	   	return new Point(x,y/screenaspectratio);
    } 
public Point glToScreen(double x, double y)
{
	double screenaspectratio=screenwidth/screenheight;
	double windowaspectratio=windowwidth/windowheight;
	if (screenaspectratio>windowaspectratio)
	    x/=screenaspectratio;
	else
	   	y*=screenaspectratio;
	x=((x/windowwidth))*screenwidth;	
	
	y=((y*-1/windowheight)+0.5)*screenheight;
	return new Point(x,y);
}
public Point screenToGl(Point p)
{return screenToGl(p.x, p.y);}
public Point glToScreen(Point p)
{return glToScreen(p.x, p.y);}

public void init(GLAutoDrawable gLDrawable) 
	{
    glDraw = gLDrawable;
    GL gl = gLDrawable.getGL();
    gl = gLDrawable.getGL();
    gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    glDraw.addKeyListener(this);
    glDraw.addMouseListener(this);
    glDraw.addMouseMotionListener(this);
    }
    

public void display(GLAutoDrawable gLDrawable) 
	{
    GL gl = gLDrawable.getGL();
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();
    

    gl.glPointSize(3);
    gl.glBegin(GL.GL_POINTS);
    	for (int i = 0; i< windowwidth; i++)
    		for (int j = 0; j< windowheight; j++)
    			{	
    			gl.glColor3d(1-space.position(i, j).getPheromone()/space.maxpheromone.getPheromone(), space.position(i, j).getPheromone()/space.maxpheromone.getPheromone(), 0.0);
    			gl.glVertex2d(i,j);
    			
    			}
	gl.glEnd();
    
    gl.glColor3d(0.0, 0.0, 0.0);
    gl.glPointSize(antsize);
    gl.glBegin(GL.GL_POINTS);
    	for (int i = 0; i< antarray.length; i++)
    		gl.glVertex2d(OpenglClass.antarray[i].position.x,OpenglClass.antarray[i].position.y);
	gl.glEnd();
	
	 gl.glColor3d(1.0, 0.0, 0.0);
    gl.glPointSize(antsize);
    gl.glBegin(GL.GL_POINTS);
	for (int i = 0; i< foodarray.length; i++)
		gl.glVertex2d(OpenglClass.foodarray[i].x,OpenglClass.foodarray[i].y);
gl.glEnd();
	
    gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2d(0+1,0+1);
		gl.glVertex2d(windowwidth-1,0+1);
		gl.glVertex2d(windowwidth-1,windowheight-1);
		gl.glVertex2d(0+1,windowheight-1);
		gl.glVertex2d(0+1,0+1);
	gl.glEnd();
;
    
	
	}
    

public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) 
    {
    GL gl = gLDrawable.getGL();
    GLU glu = new GLU();
    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    screenwidth=width;
    screenheight=height;
    Point glpoint= screenToGl(width, height);
    glu.gluOrtho2D(0, glpoint.x, 0, glpoint.y);
    } 
    
public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {}



public static JPanel criarCheckBoxes(){

	class CBListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			glDraw.display();
		}
	}
	
	CBListener listener = new CBListener();

	polygonal = new JCheckBox("polygonal");
	polygonal.addActionListener(listener);
	
	bezier = new JCheckBox("bezier");
	bezier.addActionListener(listener);
	
	spline = new JCheckBox("BSpline");
	spline.addActionListener(listener);
	
	catmull = new JCheckBox("Catmull-Rom");
	catmull.addActionListener(listener);
	
	JPanel painel = new JPanel();
	painel.setLayout(new BoxLayout(painel,
            BoxLayout.LINE_AXIS));

	painel.add(polygonal);
	painel.add(bezier);
	painel.add(spline);
	painel.add(catmull);
	painel.setBorder(new TitledBorder(new EtchedBorder(), "Draw"));
	return painel;
}

public static JPanel criarPainelControlo() {
	JPanel painelCor = criarCheckBoxes();
	//JPanel painelPrimitivas = criarRadioButtons();
	
	JPanel painel = new JPanel();
	painel.setLayout(new GridLayout(2, 1));
	painel.add(painelCor);
	//painel.add(painelPrimitivas);
	return painel;
}

	

public static void start(double width, double height, Ant[] antarray, SpacePosition[] foodarray, Space space)
    	{
		OpenglClass.antarray=antarray;
		OpenglClass.foodarray=foodarray;
		OpenglClass.space=space;
		OpenglClass.windowwidth=width;
		OpenglClass.windowheight=height;
		
    	JFrame frame = new JFrame("Ant optimization - goncalopp, 2008");
    	GLCanvas canvas = new GLCanvas();
    	canvas.addGLEventListener(new OpenglClass());
    	canvas.setSize(291, 291);
 		frame.add(canvas, BorderLayout.CENTER);
//		JPanel painelControlo = criarPainelControlo();
//		frame.add(painelControlo, BorderLayout.WEST);
    	frame.pack();
    	frame.addWindowListener(
    			new WindowAdapter() 
    			{
    				public void windowClosing(WindowEvent e) 
    					{
    					System.exit(0);
    					}
    			});
     	canvas.requestFocusInWindow();
    	frame.setVisible(true);
    }

public static void draw(){
	glDraw.display();	
}
 
public void keyTyped(KeyEvent e)
	{
    if (e.getKeyChar() == '\u001B') //escape
    	System.exit(0);
	}
public void keyPressed(KeyEvent e) {}

public void keyReleased(KeyEvent e) {}
public void mouseClicked(MouseEvent e) {}
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {}
public void mousePressed(MouseEvent e)
	{
	}
public void mouseReleased(MouseEvent e) {}
public void mouseDragged(MouseEvent e) {}
public void mouseMoved(MouseEvent e) 
	{
;
	}

}
