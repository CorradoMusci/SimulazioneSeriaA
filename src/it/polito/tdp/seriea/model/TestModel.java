package it.polito.tdp.seriea.model;



public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Model model = new Model();
		
	    model.creaGrafo(2006);
		
		for( Team t : model.getClassifica()){
           System.out.println(t+" "+t.getPunteggio()+"\n");        
		
		}
    
	}

}
