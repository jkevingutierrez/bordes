package edge;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Image {

	private BufferedImage img;

	public BufferedImage readImage(String url) {
		this.img = null;

	    try {
			this.img = ImageIO.read(new File(url));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.img;
	}

	public BufferedImage getGrayScale(BufferedImage img) {
		this.img = img;

    	WritableRaster raster = img.getRaster();

    	int height = img.getHeight();
    	int width = img.getWidth();

		for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            int[] pixel = raster.getPixel(x, y, new int[3]);

	            int grayLevel = (int) (0.21*pixel[0] + 0.72*pixel[1] + 0.07*pixel[2]);
	            pixel[0] = grayLevel;
	            pixel[1] = grayLevel;
	            pixel[2] = grayLevel;
	            raster.setPixel(x, y, pixel);
	        }
	    }

		return this.img;
	}

	public BufferedImage getEdges(BufferedImage img) {
		this.img = img;

    	WritableRaster raster = img.getRaster();

    	int height = img.getHeight();
    	int width = img.getWidth();

		int[][] horizontalMatrix = new int[height][width];
		int[][] verticalMatrix = new int[height][width];
		int[][] finalMatrix = new int[height][width];

		for (int y = 0; y < height; y++) {
	        for (int x = 0; x < img.getWidth(); x++) {
	            int[] pixel = raster.getPixel(x, y, new int[3]);
	            int[] nextPixelHorizontal = {0};
	            int[] nextPixelVertical = {0};
				int[] resultantPixel = new int[3];

	            if (x + 1 < width) {
	            	nextPixelHorizontal = raster.getPixel(x + 1, y, new int[3]);
	            }

	            if (y + 1 < height) {
		            nextPixelVertical = raster.getPixel(x, y + 1, new int[3]);
	            }

	            horizontalMatrix[y][x] = pixel[0] - nextPixelHorizontal[0];
	            verticalMatrix[y][x] = pixel[0] - nextPixelVertical[0];
				finalMatrix[y][x] = (int) Math.sqrt((Math.pow(horizontalMatrix[y][x], 2) + Math.pow(verticalMatrix[y][x], 2)));

				if (finalMatrix[y][x] >= 150) {
					resultantPixel[0] = 0;
					resultantPixel[1] = 0;
					resultantPixel[2] = 0;
				} else {
					resultantPixel[0] = 255;
					resultantPixel[1] = 255;
					resultantPixel[2] = 255;
				}

//	            resultantPixel[0] = finalMatrix[y][x];
//	            resultantPixel[1] = finalMatrix[y][x];
//	            resultantPixel[2] = finalMatrix[y][x];

//				resultantPixel[0] = Math.min(horizontalMatrix[y][x], verticalMatrix[y][x]);
//	            resultantPixel[1] = Math.min(horizontalMatrix[y][x], verticalMatrix[y][x]);
//	            resultantPixel[2] = Math.min(horizontalMatrix[y][x], verticalMatrix[y][x]);

	            raster.setPixel(x, y, resultantPixel);
	        }
	    }

		return this.img;
	}

	public void drawImage(BufferedImage img) {
		this.img = img;

		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(this.img)));
		frame.pack();
		frame.setVisible(true);
	    frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
