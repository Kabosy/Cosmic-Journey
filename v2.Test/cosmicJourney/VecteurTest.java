package cosmicJourney;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import v2.src.modele.Vecteur;

class VecteurTest {

	@Test
	void normeTest() {
		Vecteur v= new Vecteur(-5, 2);
		double res= Math.sqrt(v.getX()*v.getX() + v.getY()+v.getY());
		assertEquals(res,v.norme());
		
		Vecteur v2= new Vecteur(5, -2);
		double res2= Math.sqrt(v2.getX()*v2.getX() + v2.getY()*v2.getY());
		assertEquals(res2,v2.norme());
		
		Vecteur v3= new Vecteur(5, 2);
		double res3= Math.sqrt(v3.getX()*v3.getX() + v3.getY()*v3.getY());
		assertEquals(res3,v3.norme());

		Vecteur v4= new Vecteur(0, 0);
		double res4= Math.sqrt(v4.getX()*v4.getX() + v4.getY()*v4.getY());
		assertEquals(res4,v4.norme());
	
		Vecteur v5= new Vecteur(-10, -10);
		double res5= Math.sqrt(v5.getX()*v5.getX() + v5.getY()*v5.getY());
		assertEquals(res5,v5.norme());
		
	}

}
