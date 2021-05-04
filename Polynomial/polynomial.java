package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage
	 * format of the polynomial is:
	 * 
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * 
	 * with the guarantee that degrees will be in descending order. For example:
	 * 
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * 
	 * which represents the polynomial:
	 * 
	 * <pre>
	 * 4 * x ^ 5 - 2 * x ^ 3 + 2 * x + 3
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients
	 *         and degrees read from scanner
	 */
	public static Node read(Scanner sc) throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}

	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	private static Node addToFront(Node front, float coeff, int degree) {
		return new Node(coeff, degree, front);
	}

	private static Node reverse(Node node) {
		Node prev = null;
		Node current = node;
		Node next = null;
		while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		node = prev;
		return node;
	}

	public static Node add(Node poly1, Node poly2) {
		float coeff = 0;
		int degree = 0;
		Node polyAdd = null;
		while (poly1 != null && poly2 != null) {
			if ((int) poly1.term.degree == (int) poly2.term.degree) {
				if ((float) poly1.term.coeff + (float) poly2.term.coeff == 0) {
					poly1 = poly1.next;
					poly2 = poly2.next;
				} else {
					coeff = (float) poly1.term.coeff + (float) poly2.term.coeff;
					degree = (int) poly1.term.degree;
					poly1 = poly1.next;
					poly2 = poly2.next;
				}
			} else if ((int) poly1.term.degree < (int) poly2.term.degree) {
				coeff = (float) poly1.term.coeff;
				degree = (int) poly1.term.degree;
				poly1 = poly1.next;
			} else if ((int) poly1.term.degree > (int) poly2.term.degree) {
				coeff = (float) poly2.term.coeff;
				degree = (int) poly2.term.degree;
				poly2 = poly2.next;
			}
			polyAdd = addToFront(polyAdd, coeff, degree);
		}
		while (poly1 != null) {
			coeff = (float) poly1.term.coeff;
			degree = (int) poly1.term.degree;
			poly1 = poly1.next;
			polyAdd = addToFront(polyAdd, coeff, degree);
		}
		while (poly2 != null) {
			coeff = (float) poly2.term.coeff;
			degree = (int) poly2.term.degree;
			poly2 = poly2.next;
			polyAdd = addToFront(polyAdd, coeff, degree);
		}

		polyAdd = reverse(polyAdd);

		return polyAdd;

	}

	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node product = null;
		Node ptr1, ptr2;
		ptr1 = poly1;
		ptr2 = poly2;
		while (ptr1 != null) {
			while (ptr2 != null) {
				float coeff = ptr1.term.coeff * ptr2.term.coeff;
				int degree = ptr1.term.degree + ptr2.term.degree;
				Node temporary = new Node(coeff, degree, null);
				product = add(product, temporary);
				ptr2 = ptr2.next;
			}
			ptr2 = poly2;
			ptr1 = ptr1.next;
		}
		return product;
	}

	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x    Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		float value = 0;
		while (poly != null) {
			float currentValue = (float) Math.pow(x, poly.term.degree);
			currentValue *= poly.term.coeff;
			value += currentValue;
			poly = poly.next;
		}
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return value;
	}

	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		}

		String retval = poly.term.toString();
		for (Node current = poly.next; current != null; current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
}
