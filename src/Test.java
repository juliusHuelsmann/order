import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Generate order relation strings, print and count them.
 * 
 * @author Julius
 * @version %I%, %U%
 */
public class Test {

	public static Logger log = Logger.getLogger("logger");
	
	private Ordnung[] ord;
	
	
	/**
	 * Test 
	 */
	protected void test3() {


		char[] alphabet = {'a', 'b', 'c'};
		test(alphabet);
		draw(alphabet);
		print(alphabet);
		
	}
	
	
	private void test(final char[] _alpha) {

		ord = new Ordnung[(int) Math.pow(_alpha.length, 2 * _alpha.length)];
		final int maxLength = (int) Math.pow(_alpha.length, 2);
		for (int i = _alpha.length; i < maxLength; i++) {

			System.out.println(i);
			
			// for being quicker start the initialization with reflexive elements
			String[] relation = new String[i + 1];
			for (int s = 0; s < _alpha.length; s++) {

				relation[s] = _alpha[s] + "" + _alpha[s];

			}
			
			if (i == _alpha.length) {

				//
				// the reflexive one
				//
				final String[] relCopy = new String[_alpha.length];
				for (int j = 0; j < _alpha.length; j++) {
					relCopy[j] = relation[j];
				}
				// add the reflexive 
				ord[index] = new Ordnung(_alpha, relCopy);
				if (ord[index].isOrdnung()) {
					index++;
				} else 
					ord[index] = null;
			}
			
			getRelation(i, _alpha.length, relation, _alpha, 0, 0);
		}
		
		
		
		
	}
	
	
	protected void test4() {


		char[] alphabet = {'a', 'b', 'c', 'd'};
		test(alphabet);
		draw(alphabet);
		print(alphabet);
	}
	public Test() {

		test3();
		System.out.println("anz. ordn" + index);

		
		int index = 0;
		for (int i = 0; i < ord.length; i++) {
			
			Ordnung ordn = ord[i];
			if (ordn != null) {
				System.out.print("index" + index + "\t");
				ordn.print();
				System.out.println();
				index ++;
			}
		}
	
		
	}//aa", "bb", "cc", "ac", "cb", "ab"
	
	

	public void draw(final char[] _alphabe) {
//		aa, bb, cc, ab, ac, bc, 
		BufferedImage bi = new BufferedImage(100, index * 100, BufferedImage.TYPE_INT_ARGB);

		JFrame jf = new JFrame("Reflexivität nicht eigezeichnet. n = " + _alphabe.length + ", anz = " + (index));
		jf.setSize(500, 500);
		jf.setBackground(Color.pink);
		jf.setLayout(null);
		jf.setLocationRelativeTo(null);
		jf.setLocation(jf.getLocation().x - 250, jf.getLocation().y);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel jlbl = new JLabel();
		JScrollPane js = new JScrollPane(jlbl);
		js.setSize(jf.getWidth(), jf.getHeight());
		for (int i = 0; i < index; i++) {
			Ordnung o = ord[i];
			o.printBI();
			int[] rgbArray = null;
			rgbArray = o.getBi().getRGB(0,  0, 100, 100, rgbArray, 0, 100);
			bi.setRGB(0, i * 100, 100, 100, rgbArray, 0, 100);
			
		}
		jlbl.setIcon(new ImageIcon(bi));
		jlbl.setSize(bi.getWidth(), bi.getHeight());
		jf.add(js);
		jf.setVisible(true);
		jf.setResizable(true);
		
	}

	public void print(final char[] _alphabe) {
//		aa, bb, cc, ab, ac, bc, 
		BufferedImage bi = new BufferedImage(100, index * 100, BufferedImage.TYPE_INT_ARGB);

		JFrame jf = new JFrame("Reflexivität nicht eigezeichnet. n = " + _alphabe.length + ", anz = " + (index));
		jf.setSize(500, 500);
		jf.setBackground(Color.pink);
		jf.setLocationRelativeTo(null);
		jf.setLayout(null);
		jf.setLocation(jf.getLocation().x + 250, jf.getLocation().y);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextArea jlbl = new JTextArea();
		JScrollPane js = new JScrollPane(jlbl);
		js.setSize(jf.getWidth(), jf.getHeight());
		

		System.out.println("anz. ordn" + index);


		String strg = "";
		for (int i = 0; i < index; i++) {
			strg += i + "\t" + ord[i].printRet() + "\n";
		}
		jlbl.setText(strg);
		jlbl.setSize(jlbl.getPreferredSize());
		jf.add(js);
		jf.setVisible(true);
		jf.setResizable(true);
		
	}
	// ab ac cb
	
	private static boolean debug = false;
	
	
	private static int index = 0;
	private int getRelation (final int _length, final int _position, String _rel[], 
			final char[] _alphabet, final int _startC1, final int _startC2) {

		if (_position > _length) {

			
			ord[index] = new Ordnung(_alphabet, _rel);
			if (ord[index].isOrdnung()) {
//				System.out.println("\nprinting" + _length + ".." + _position);
//				ord[index].print();
//				System.out.println();
				index++;
				return 1;
			}
			ord[index] = null;
			return 0;
		} else {

			int ordnungen = 0;
			for (int c_1 = _startC1; c_1 < _alphabet.length; c_1++) {
				for (int c_2 = 0; c_2 < _alphabet.length; c_2++) {
					if (c_1 == _startC1 && c_2 == 0)
						c_2 = _startC2;
					
					_rel[_position] = _alphabet[c_1] + "" + _alphabet[c_2];
//
					final String[] relCopy = new String[_rel.length];
					if (_length == 5 && debug) {
						System.out.println();
					}
					for (int j = 0; j < _rel.length; j++) {
						relCopy[j] = _rel[j];
						if (_length == 5 && debug) {
							System.out.print(relCopy[j] + ",");
						}
					}
					ordnungen += getRelation(
							_length, 
							_position + 1, relCopy, _alphabet,
							c_1, c_2);
				}
			}
			return ordnungen;
		}
	}
	//   a b c
	// a x
	// b   x
	// c     x
	public static void main(String[] _args) {
		new Test();
	}
}
