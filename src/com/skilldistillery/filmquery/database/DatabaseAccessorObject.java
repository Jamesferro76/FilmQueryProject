package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";

	@Override
	public Film findFilmById(int filmId) throws SQLException {

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT f.*, l.* FROM film f JOIN language l ON f.language_id=l.id WHERE f.id=?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		Film film = null;
		if (rs.next()) {
			// This do while loop is probably useless because there will only be one result
			do {
//				System.out.println(rs.getString("title") + " " + rs.getString("release_year") + " "
//						+ rs.getString("rating") + " " + rs.getString("description") + " " + rs.getString("name"));
				int filmID = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				short releaseYear = rs.getShort("release_year");
				int langId = rs.getInt("language_id");
				int rentDur = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double repCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				String features = rs.getString("special_features");
				String language= rs.getString("name");
				film = new Film(filmID, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features, findActorsByFilmId(filmId), language, findFilmCategory(filmId));

			} while (rs.next());
		}
		stmt.close();
		conn.close();
		rs.close();
		return film;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM actor a WHERE a.id=?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet rs = stmt.executeQuery();
		Actor actor = null;
		if (rs.next()) {
			// This do while loop is probably useless because there will only be one result
			do {
				System.out.println(
						rs.getString("id") + " " + rs.getString("first_name") + " " + rs.getString("last_name"));
				int actorID = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				actor = new Actor(actorID, firstName, lastName);

			} while (rs.next());
		} else {
			System.out.println("That id does not go to one of our actors");
		}
		stmt.close();
		conn.close();
		rs.close();
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actorList = new ArrayList<>();
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT f.*, a.* FROM film f JOIN film_actor fa ON f.id=fa.film_id JOIN actor a ON fa.actor_id=a.id WHERE f.id=?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		Actor actor = null;
		if (rs.next()) {
			// This do while loop is probably useless because there will only be one result
			do {
				//doesn't show what language the film is in
//				System.out.println(rs.getString("title") + " " + rs.getString("release_year") + " "
//						+ rs.getString("rating") + " " + rs.getString("description"));
//				System.out.println(
//						rs.getString("a.id") + " " + rs.getString("first_name") + " " + rs.getString("last_name"));
				int actorID = rs.getInt("a.id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				actor = new Actor(actorID, firstName, lastName);
				actorList.add(actor);
			} while (rs.next());
		}
		stmt.close();
		conn.close();
		rs.close();
		return actorList;
	}
	
	public List<Film> findFilmByKeyword(String keyword) throws SQLException {
		List<Film> filmList= new ArrayList<>();
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, keyword);
		stmt.setString(2, keyword);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			int id=rs.getInt("id");
			filmList.add(findFilmById(id));
		}
		stmt.close();
		conn.close();
		rs.close();
		return filmList;
		
	}
	
	public List<String> findFilmCategory(int id) throws SQLException{
		List<String> category= new ArrayList<>();
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT f.*, c.* FROM film f JOIN film_category fc ON f.id=fc.film_id "
			      + "JOIN category c ON fc.category_id=c.id WHERE f.id=?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
ResultSet rs = stmt.executeQuery();
		
if (rs.next()) {
	// This do while loop is probably useless because there will only be one result
	do {
		//doesn't show what language the film is in
//		System.out.println(rs.getString("title") + " " + rs.getString("release_year") + " "
//				+ rs.getString("rating") + " " + rs.getString("description"));
//		System.out.println(
//				rs.getString("a.id") + " " + rs.getString("first_name") + " " + rs.getString("last_name"));
		category.add(rs.getString("c.name"));

	} while (rs.next());
}
		stmt.close();
		conn.close();
		rs.close();
		return category;
		
		
	}

}
