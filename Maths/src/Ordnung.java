import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Ordnung {

	/**
	 * The relations.
	 */
	private final String[] relation;
	
	/**
	 * The alphabet (used for the 
	 */
	private final char[] alphabet;
	
	/**
	 * Paint BufferedImage
	 */
	private BufferedImage bi;
	
	
	/**
	 * Contructor: save values and error checking.
	 * @param _alphabet	the alphabet, {@link #alphabet}
	 * @param _relation	the relations {@link #relation}
	 */
	public Ordnung(final char[] _alphabet, final String[] _relation) {
		
		// save relation and alphabet
		this.relation = _relation;
		this.alphabet = _alphabet;
		
		// error checking.
		if (_relation == null)
			System.exit(1);
		for (String rel : _relation) {
			if (rel == null) {
				Test.log.severe("Error relation eq 2 null" + _relation +"len" +  _relation.length);
				for (int i = 0; i < _relation.length; i++) {
					System.out.println(_relation[i]);
				}
				System.exit(1);
			}
			if (rel.length()!= 2) {
				Test.log.severe("Error relation array eq 2 null");
				System.exit(1);
			}
		}
	}
	
	
	public void printBI() {
		//init bi
		final int size = 100;
		setBi(new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB));
		Graphics g = getBi().getGraphics();

		for (int i = 0; i < alphabet.length; i++) {

			g.drawString("" + alphabet[i], (1 + i % 2) * size / 3, ((1 + (int) (i/2))) * size / 3);	
		}
		
		for (int i = 0; i < relation.length; i++) {
			g.setColor(Color.green);
			char fC = relation[i].charAt(0);
			char lC = relation[i].charAt(1);

			int indexF = index(alphabet, fC);
			int indexL = index(alphabet, lC);
			
			Point locF = new Point((1 + indexF % 2) * size / 3, ((1 + (int) (indexF/2))) * size / 3);
			Point locL = new Point((1 + indexL % 2) * size / 3, ((1 + (int) (indexL/2))) * size / 3);
			
			final Point pnt_vector = new Point(locL.x - locF.x, locL.y - locF.y);
			pnt_vector.x = pnt_vector.x * 2/3;
			pnt_vector.y = pnt_vector.y * 2/3;
			final Point pnt_dest = new Point(locF.x + pnt_vector.x, locF.y + pnt_vector.y);
			g.drawLine(locF.x, locF.y, pnt_dest.x, pnt_dest.y);
			final int size1 = 10;
			g.drawRect(locF.x - size1/2, locF.y - size1/2, size1, size1);
			
		}
		for (int i = 0; i < alphabet.length; i++) {
			g.setColor(Color.black);
			g.drawString("" + alphabet[i], (1 + i % 2) * size / 3, ((1 + (int) (i/2))) * size / 3);	
		}
	}
	
	private int index(final char[] _ch, final char _c) {
		for (int i = 0; i < _ch.length; i++) {
			if (_ch[i] == _c)
				return i;
		}
		return -1;
	}
	
	public void print() {
		for (int i = 0; i < relation.length; i++) {
			System.out.print(relation[i].charAt(0) + "" 
					+ leq + "" + relation[i].charAt(1) + ", ");
		}
	}
	
	/**
	 * Check whether the current instance of Ordnung is actually 
	 * reflexive, antisymmetric and transitive. Otherwise, it has to be removed
	 * from list.
	 * 
	 * @return
	 */
	public boolean isOrdnung() {
		
		final boolean minimal = isMinimal();
		if (!minimal) {
			return false;
		}
		final boolean reflexive = isReflexive();
		if (!reflexive) {
			return false;
		}
		final boolean antisymm = isAntiSymm();
		if (!antisymm) {
			return false;
		}
		final boolean transitive = isTransitive();
		if (!transitive) {
			return false;
		}
		final boolean isOrdnung = reflexive && antisymm && transitive;
		
		return isOrdnung;
	}
	
	private boolean isMinimal() {
		for (int i = 0; i < relation.length; i++) {
			for (int j = 0; j < relation.length; j++) {
				
				if (i != j && relation[i].equals(relation[j])) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	private boolean isReflexive() {

		for (char alpha : alphabet) {
			String relation = alpha + "" + alpha;
			if (!contains(relation)) {
				return false;
			}
		}
		return true;
	}
	
	
	private boolean isAntiSymm() {
		
		for (String rel : relation) {
			char small = rel.charAt(0);
			char great = rel.charAt(1);

			if (great != small && contains("" + great + "" +  small)) {

					return false;
			}
		}
		return true;
	}
	
	
	private boolean isTransitive() {
		
		for (String rel : relation) {
			char small = rel.charAt(0);
			char great = rel.charAt(1);
			
			// abbreviation for reflexive relation tuple.
			if (small == great) {
				continue;
			}

			final String[] sw = startingWith(great);
			
			if (sw == null)
				continue;
			for (String rel2 : sw) {
				char greatest = rel2.charAt(1);
				if (!contains("" + small + "" + greatest)) {
					return false;
				}
			}
		}
		return true;
	}

	private String[] startingWith(final char _start) {
		
		String [] ret_strg = null;
		
		for (String rel : relation) {
		
			// found one string starting with specified character
			if (rel.charAt(0) == _start) {
				
				if (ret_strg == null) {

					ret_strg = new String[1];
					ret_strg[0] = rel;
				} else {

					String[] newStrg = new String[ret_strg.length + 1];
					newStrg[ret_strg.length] = rel;
					for (int i = 0; i < ret_strg.length; i++) {
						newStrg[i] = ret_strg[i];
					}
					ret_strg = newStrg;
				}
			}
		}
		return ret_strg;
	}
	

	private String[] endingWith(final char _start) {
		
		String [] ret_strg = null;
		
		for (String rel : relation) {
		
			// found one string starting with specified character
			if (rel.charAt(1) == _start) {
				
				if (ret_strg == null) {

					ret_strg = new String[1];
					ret_strg[0] = rel;
				} else {

					String[] newStrg = new String[ret_strg.length + 1];
					newStrg[ret_strg.length] = rel;
					for (int i = 0; i < ret_strg.length; i++) {
						newStrg[i] = ret_strg[i];
					}
					ret_strg = newStrg;
				}
			}
		}
		return ret_strg;
	}
	
	
	/**
	 * Checks whether the current relation array contains a specified relation.
	 * @param _strg		the relation
	 * @return			whether the relation is contained.
	 */
	private boolean contains(final String _strg) {

		for (String rel : relation) {
		
			// found one string starting with specified character
			if (rel.equals(_strg)) {
				return true;
			}
		}
		return false;
	}
	

	private final char leq = '\u2264';
	public static void main(String[] _argss) {
//		aa, bb, cc, ab, ac, bc, 
		Ordnung o = new Ordnung(new char[]{'a', 'b', 'c'}, new String[]{
				"aa", "bb", "cc", "ac", "cb", "ab"});
		o.print();
		
		o.printBI();
		JFrame jf = new JFrame();
		jf.setSize(500, 500);
		jf.setBackground(Color.pink);
		jf.setLayout(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel jlbl = new JLabel();
		jlbl.setIcon(new ImageIcon(o.getBi()));
		jlbl.setSize(o.getBi().getWidth(), o.getBi().getHeight());
		jf.add(jlbl);
		jf.setVisible(true);
		jf.setResizable(false);
		System.out.println(o.isOrdnung());
	}


	/**
	 * @return the bi
	 */
	public BufferedImage getBi() {
		return bi;
	}


	/**
	 * @param bi the bi to set
	 */
	public void setBi(BufferedImage bi) {
		this.bi = bi;
	}
}
