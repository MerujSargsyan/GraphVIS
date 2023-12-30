import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

//TO-DO: implement MouseListener
public class DrawPanel extends JPanel implements MouseListener, KeyListener {
    private ArrayList<Shape> shapes;
    private ArrayList<Vertex> vertecies;
    private final int X_SIZE = 25;
    private final int Y_SIZE = 25;
    private final int ARC_SIZE = 25;
    private final int CENTER_ADJUSTMENT = 12;
    private Grid grid = new Grid();

    private int DELETE_VALUE = 8;


    public DrawPanel() {
        shapes = new ArrayList<>();
        vertecies = new ArrayList<>();
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        for(Shape s : shapes) {
            if(s instanceof Vertex) {
                //Draw vertex
                paintVertex((Vertex)s, g);
            } else if(s instanceof Line) {
                //Draw line
                paintLine((Line)s, g);
            }
        }
    }

    public void addVertex(Vertex v) {
        shapes.add(v);
        vertecies.add(v);
        if(vertecies.size() >= 2) {
            addLine();
        }        
        repaint();
    }

    public void addLine() {
        Line newLine = new Line(vertecies.get(vertecies.size() - 2), 
            vertecies.get(vertecies.size() - 1));
        shapes.add(newLine);
    }

    public void paintVertex(Vertex v, Graphics g) {
        g.fillRoundRect(v.x - CENTER_ADJUSTMENT, v.y - CENTER_ADJUSTMENT, X_SIZE, 
            Y_SIZE, ARC_SIZE, ARC_SIZE);
    }

    public void paintLine(Line l, Graphics g) {
        g.drawLine(l.startingPoint.x, l.startingPoint.y, l.endingPoint.x, 
            l.endingPoint.y);
    }

    private void deleteRecentButton() {
        int i = shapes.size() - 1;
        //removes all lines connected to the most recent vertex
        while(!(shapes.get(i) instanceof Vertex) && i > 0) {
            shapes.remove(shapes.get(i));
            i--;
        }
        //deletes the vertex itself
        Vertex v = (Vertex)shapes.get(shapes.size() - 1);
        grid.deletePoint(v);
        vertecies.remove(v);
        shapes.remove(v);
        repaint();
    }

    //Overrides all MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {}; //nothing happens

    @Override
    public void mouseReleased(MouseEvent e) {}; //nothing happens

    @Override
    public void mouseEntered(MouseEvent e) {}; //nothing happens

    @Override
    public void mouseExited(MouseEvent e) {}; //nothing happens

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = getMousePosition(false);
        Vertex v = grid.usePoint(p);
        if(v != null) {
            addVertex(v);
        }   
    } 

    //Overrides all KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {
        
    }; //does nothing

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == DELETE_VALUE) {
            deleteRecentButton();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}; //does nothing
}

//TODO: catch too many deletes error