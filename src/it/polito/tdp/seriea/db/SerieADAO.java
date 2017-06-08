package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons";

		List<Season> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Match> listMathes(int season) {
		String sql = "SELECT * FROM matches where matches.Season = ?";

		List<Match> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, season);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Season s = new Season(res.getInt("Season"));
				Team homeTeam = new Team(res.getString("homeTeam"));
				Team awayTeam = new Team(res.getString("awayTeam"));

				Match m = new Match(res.getInt("match_id"), s, res.getString("div"), res.getDate("date").toLocalDate(),
						homeTeam, awayTeam, res.getInt("fthg"), res.getInt("ftag"), res.getString("ftr"));
				result.add(m);
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeambyAnno(int season) {
		String sql = "SELECT DISTINCT teams.team " + "FROM matches,teams " + "where (matches.HomeTeam = teams.team or "
				+ " matches.AwayTeam = teams.team) and Season = ?";

		List<Team> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, season);
			ResultSet res = st.executeQuery();

			while (res.next()) {

				Team t = new Team(res.getString("team"));

				result.add(t);
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
