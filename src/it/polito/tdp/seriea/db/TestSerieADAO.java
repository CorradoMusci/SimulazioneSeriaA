package it.polito.tdp.seriea.db;

import java.util.List;

import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;


public class TestSerieADAO {

	public static void main(String[] args) {
		SerieADAO dao = new SerieADAO() ;
		
		List<Season> seasons = dao.listSeasons() ;
		System.out.println(seasons);
		

		
		//System.out.println(teams);
		
		for(Match m : dao.listMathes(2006)){
			System.out.format("%s-%s-%d-%d\n",m.getHomeTeam(),m.getAwayTeam(),m.getFtag(),m.getFthg());
		}

		
	}

}
