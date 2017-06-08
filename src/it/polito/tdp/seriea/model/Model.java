package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {

	private SerieADAO serieDAO;
	private List<Match> matches;
	private DirectedWeightedMultigraph<Team, DefaultWeightedEdge> grafo;

	public Model() {

		serieDAO = new SerieADAO();
	}

	public Set<Team> creaGrafo(int season) {

	 	grafo = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);
		
		matches = serieDAO.listMathes(season);
       
		List<Team> teams = serieDAO.listTeambyAnno(season);
		
//		Graphs.addAllVertices(grafo,teams);
	   
		for (Match m : matches) {
             Team source = null;
             Team target = null;
		 
           grafo.addVertex(m.getHomeTeam());
	       grafo.addVertex(m.getAwayTeam());
			
	       int weight = 0;

			if (m.getFthg() > m.getFtag())
				weight = 1;
			else if (m.getFthg() < m.getFtag())
				    
				weight = -1;
            
//			for (Team t : grafo.vertexSet()) {
//				if (t.getTeam().compareTo(m.getHomeTeam().getTeam()) == 0)
//					source = t;
//				if (t.getTeam().compareTo(m.getAwayTeam().getTeam()) == 0)
//					target = t;
//			}
		
			// grafo.addEdge(m.getHomeTeam(),m.getAwayTeam());
			// grafo.setEdgeWeight(grafo.getEdge(m.getHomeTeam(),m.getAwayTeam()),
			// weight);
			Graphs.addEdge(grafo, m.getHomeTeam(), m.getAwayTeam(), weight); 

		}
		return grafo.vertexSet();
	}

	public List<Team> getClassifica() {

		List<Team> classifica = new ArrayList<>();

		Team t1;
		Team t2;
		double weight;

		for (DefaultWeightedEdge e : grafo.edgeSet()) {

			t1 = grafo.getEdgeSource(e);
			t2 = grafo.getEdgeTarget(e);
			weight = grafo.getEdgeWeight(e);

			if (weight > 0) {
				t1.plusPunteggio(3);
			} else if (weight < 0) {
				t2.plusPunteggio(3);
			} else {
				t1.plusPunteggio(1);
				t2.plusPunteggio(1);
			}
		}
  
		classifica = new ArrayList<>(grafo.vertexSet());
		Collections.sort(classifica);

		return classifica;
	}

}
