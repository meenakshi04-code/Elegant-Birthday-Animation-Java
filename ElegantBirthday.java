import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ElegantBirthday extends JPanel implements ActionListener {

    private Timer timer;
    private int textX = -400;
    private float glow = 0f;
    private boolean increase = true;

    private int[] balloonX = new int[7];
    private int[] balloonY = new int[7];
    private Color[] colors = {
        new Color(255, 99, 132),
        new Color(54, 162, 235),
        new Color(255, 206, 86),
        new Color(75, 192, 192),
        new Color(153, 102, 255),
        new Color(255, 159, 64),
        new Color(200, 200, 200)
    };

    Random rand = new Random();

    public ElegantBirthday() {
        timer = new Timer(40, this);
        timer.start();

        for (int i = 0; i < balloonX.length; i++) {
            balloonX[i] = rand.nextInt(600);
            balloonY[i] = rand.nextInt(400) + 200;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Smooth rendering
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gradient background
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(20, 20, 40),
                0, getHeight(), new Color(80, 0, 120)
        );
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Glowing text effect
        g2.setFont(new Font("Serif", Font.BOLD, 42));
        Color glowColor = new Color(255, (int)(100 + glow), (int)(150 + glow));
        g2.setColor(glowColor);
        g2.drawString("HAPPY BIRTHDAY", textX, 150);

        // Balloons
        for (int i = 0; i < balloonX.length; i++) {
            g2.setColor(colors[i]);
            g2.fillOval(balloonX[i], balloonY[i], 40, 55);

            g2.setColor(Color.WHITE);
            g2.drawLine(balloonX[i] + 20, balloonY[i] + 55,
                        balloonX[i] + 20, balloonY[i] + 85);
        }

        // Cake base
        g2.setColor(new Color(240, 240, 240));
        g2.fillRoundRect(200, 260, 200, 90, 20, 20);

        // Cake layer
        g2.setColor(new Color(255, 182, 193));
        g2.fillRoundRect(200, 230, 200, 30, 20, 20);

        // Candles
        g2.setColor(new Color(255, 255, 180));
        for (int i = 0; i < 4; i++) {
            int x = 220 + i * 40;
            g2.fillRect(x, 200, 8, 30);

            // Flame
            g2.setColor(new Color(255, 140, 0));
            g2.fillOval(x, 190, 8, 10);
            g2.setColor(new Color(255, 255, 180));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Move text
        textX += 3;
        if (textX > getWidth()) {
            textX = -400;
        }

        // Glow animation
        if (increase) glow += 3;
        else glow -= 3;

        if (glow > 100) increase = false;
        if (glow < 0) increase = true;

        // Balloon animation
        for (int i = 0; i < balloonY.length; i++) {
            balloonY[i] -= 2;
            if (balloonY[i] < -60) {
                balloonY[i] = getHeight();
                balloonX[i] = rand.nextInt(getWidth());
            }
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Birthday Animation");
        ElegantBirthday panel = new ElegantBirthday();

        frame.add(panel);
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}