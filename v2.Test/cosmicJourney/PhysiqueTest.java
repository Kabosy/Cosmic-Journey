package cosmicJourney;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.ATR;

import org.junit.Before;
import org.junit.Test;

import v2.src.modele.CorpsCeleste;
import v2.src.modele.CorpsSimule;
import v2.src.modele.Physique;
import v2.src.modele.Vecteur;

public class PhysiqueTest {
	
	CorpsCeleste a =new CorpsCeleste("soleil", 30, 0, 0, 0, 0);
	CorpsCeleste b =new CorpsCeleste("planete", 2, -100, 0, 0, 1);
	CorpsCeleste c =new CorpsCeleste("planete", 30, -50, 10, 0, 0);
	CorpsCeleste d =new CorpsCeleste("planete", 2, -100, 0, 0, 1);
	
	List <CorpsCeleste> univers=new ArrayList<>();
	
	
	
	@Test
	public void distanceEntreCorpsTest() {
	double res=Math.sqrt(10000);
		double res2=Math.sqrt(2600);
		assertEquals(res,Physique.distanceEntreCorps(a, b),0.001);
		assertEquals(res2,Physique.distanceEntreCorps(c, d),0.001);
	}

	@Test
	public void forceAttractionTest() {
		double d= Physique.distanceEntreCorps(a, b);
		assertEquals(0.3/Math.pow(d, 2),Physique.forceAttraction(30, 1, Physique.distanceEntreCorps(a, b)),0.001);
	}
	

	@Test
	public void vecteurAttractionTest() {
		Vecteur attractionV= Physique.vecteurAttraction(a, b);
		Vecteur v=new Vecteur(-4,-8);
		double attraction = Physique.forceAttraction(a.getMasse(), b.getMasse(), Physique.distanceEntreCorps(a, b));
		Vecteur res=new Vecteur(-4/Math.sqrt(80) * attraction, -8/Math.sqrt(80) *attraction);
		assertEquals(res.getX(),attractionV.getX(),0.01);
		assertEquals(res.getY(),attractionV.getY(),0.01);
	}
	
	@Test
	public void vecteurAccelerationTest() {
		univers.add(a);
		univers.add(b);
		Vecteur attractionAB = Physique.vecteurAttraction(a, b);
		Vecteur res=new Vecteur();
		res.setX(attractionAB.getX()/a.getMasse());
		res.setY(attractionAB.getY()/a.getMasse());
		assertEquals(res.getX(),Physique.vecteurAcceleration(univers,a).getX(),0.001);
		assertEquals(res.getY(),Physique.vecteurAcceleration(univers,a).getY(),0.001);
		univers.add(c);
		univers.add(d);
		Vecteur attractionAC = Physique.vecteurAttraction(a, c);
		Vecteur attractionAD = Physique.vecteurAttraction(a, d);
		res.setX( (attractionAB.getX()+attractionAC.getX()+attractionAD.getX()  )  /a.getMasse());
		res.setY( (attractionAB.getY()+attractionAC.getY()+attractionAD.getY()  ) /a.getMasse());
		assertEquals(res.getX(),Physique.vecteurAcceleration(univers,a).getX(),0.001);
		assertEquals(res.getY(),Physique.vecteurAcceleration(univers,a).getY(),0.001);
		
	} 
	 
	@Test
	public void eulerExpliciteTest() {
		Vecteur vecAcc= new Vecteur(2, 0);
		Vecteur vecVit=new Vecteur(-4,1);
		vecVit= Physique.eulerExplicite(vecVit, vecAcc, 1);
		assertEquals(-2,vecVit.getX(),0.001);
		assertEquals(1,vecVit.getY(),0.001);
		
		Vecteur position= new Vecteur(6,-1);
		position= Physique.eulerExplicite(position, vecVit, 1);
		assertEquals(4,position.getX(),0.001);
		assertEquals(0,position.getY(),0.001);
		
	}
	
	//etant donne que PositioEuler ne fait que faire un set avec la méthode tester ci-dessus
	// et que celle ci fonctionne, il n'est pas necessaire de faire les test.
}
